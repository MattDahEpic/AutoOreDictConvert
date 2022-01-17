package com.mattdahepic.autooredictconv.common.command;

import com.mattdahepic.autooredictconv.common.AutoOreDictConv;
import com.mattdahepic.autooredictconv.common.config.ConversionsConfig;
import com.mattdahepic.autooredictconv.common.convert.Conversions;
import com.mattdahepic.mdecore.common.registries.CommandRegistry;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class CommandODC {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("odc")
                .requires(s -> s.hasPermission(4))
                .executes(CommandRegistry::missingArgument)
                .then(Commands.literal("help")
                        .executes(CommandODC::help))
                .then(Commands.literal("detect")
                        .executes(CommandODC::detect))
                .then(Commands.literal("dump")
                        .executes(CommandODC::dump))
                .then(Commands.literal("find")
                        .executes(CommandRegistry::missingArgument)
                        .then(Commands.argument("tag", ResourceLocationArgument.id())
                                .suggests((ctx, bld) -> SharedSuggestionProvider.suggest(ItemTags.getAllTags().getAvailableTags().stream().map(ResourceLocation::toString),bld))
                                .executes(CommandODC::find)))
                .then(Commands.literal("list")
                        .executes(CommandODC::list))
                .then(Commands.literal("add")
                        .executes(CommandODC::add))
                .then(Commands.literal("set")
                        .executes(CommandRegistry::missingArgument)
                        .then(Commands.argument("tag", ResourceLocationArgument.id())
                                .suggests((ctx, bld) -> SharedSuggestionProvider.suggest(ItemTags.getAllTags().getAvailableTags().stream().map(ResourceLocation::toString),bld))
                                .executes(CommandODC::set)))
                .then(Commands.literal("reload")
                        .executes(CommandODC::reload))
                .then(Commands.literal("remove")
                        .executes(CommandODC::removeHand)
                        .then(Commands.argument("tag",ResourceLocationArgument.id())
                                .suggests((ctx,bld) -> SharedSuggestionProvider.suggest(Conversions.tagConversionMap.keySet().stream().map(ResourceLocation::toString),bld))
                                .executes(CommandODC::removeTag)))
                .then(Commands.literal("pause")
                        .requires(s -> s.hasPermission(0))
                        .executes(CommandODC::pause));
        LiteralCommandNode<CommandSourceStack> command = dispatcher.register(builder);
    }
    public static int help(CommandContext<CommandSourceStack> ctx) {
        for (int i = 0; i < 9; i++) {
            ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.help."+i),false);
        }
        return Command.SINGLE_SUCCESS;
    }
    public static int detect(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ItemStack item = ctx.getSource().getPlayerOrException().getItemInHand(InteractionHand.MAIN_HAND);
        ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.detect",item.getItem().getRegistryName()),true);
        ItemTags.getAllTags().getMatchingTags(item.getItem()).forEach(r -> {
            ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc._each",r),true);
        });
        return Command.SINGLE_SUCCESS;
    }
    public static int dump(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.dump"),true);
        ItemTags.getAllTags().getAvailableTags().forEach(r -> {
            ctx.getSource().sendSuccess(new TextComponent(r.toString()),true);
        });
        return Command.SINGLE_SUCCESS;
    }
    public static int find(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ResourceLocation loc = ResourceLocationArgument.getId(ctx,"tag");
        Tag<Item> tag = ItemTags.getAllTags().getTag(loc);
        if (tag == null) throw new SimpleCommandExceptionType(new TranslatableComponent("autooredictconv.command.odc._no_tag",loc)).create();
        ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.find",loc),true);
        tag.getValues().forEach(i -> {
            ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc._each",i.getRegistryName()),true);
        });
        return Command.SINGLE_SUCCESS;
    }
    public static int list(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.list"),false);
        if (!Conversions.tagConversionMap.isEmpty()) {
            ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.list.tag"),true);
            Conversions.tagConversionMap.forEach((tag, item) -> {
                ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc._each_pair", tag, item.getRegistryName()), true);
            });
        }
        if (!Conversions.itemConversionMap.isEmpty()) {
            ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.list.item"),true);
            Conversions.itemConversionMap.forEach((in, out) -> {
                ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc._each_pair",in.getRegistryName(),out.getRegistryName()),true);
            });
        }
        return Command.SINGLE_SUCCESS;
    }
    public static int add(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ItemStack held = ctx.getSource().getPlayerOrException().getItemInHand(InteractionHand.MAIN_HAND);
        if (held.getItem() == Items.AIR) throw new SimpleCommandExceptionType(new TranslatableComponent("autooredictconv.command.odc._must_be_holding")).create();
        int addedTo = 0;
        for (ResourceLocation tag : ItemTags.getAllTags().getMatchingTags(held.getItem())) {
            if (Conversions.tagBlacklist.contains(tag.toString())) continue; //ignore tags that everything has in addition to their actual entries.
            Conversions.tagConversionMap.put(tag,held.getItem());
            addedTo++;
            ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.add",held.getItem().getRegistryName(),tag),true);
        }
        if (addedTo == 0) ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.add.none"),true);
        ConversionsConfig.save();
        return Command.SINGLE_SUCCESS;
    }
    public static int set(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ResourceLocation loc = ResourceLocationArgument.getId(ctx,"tag");
        Tag<Item> tag = ItemTags.getAllTags().getTag(loc);
        if (tag == null) throw new SimpleCommandExceptionType(new TranslatableComponent("autooredictconv.command.odc._no_tag",loc)).create();
        ItemStack held = ctx.getSource().getPlayerOrException().getItemInHand(InteractionHand.MAIN_HAND);
        if (held.getItem() == Items.AIR) throw new SimpleCommandExceptionType(new TranslatableComponent("autooredictconv.command.odc._must_be_holding")).create();
        Conversions.tagConversionMap.put(loc,held.getItem());
        ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.add",held.getItem().getRegistryName(),loc),true);
        return Command.SINGLE_SUCCESS;
    }
    public static int reload(CommandContext<CommandSourceStack> ctx) {
        ConversionsConfig.reload();
        ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.reload"),false);
        return Command.SINGLE_SUCCESS;
    }
    public static int removeHand(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ItemStack held = ctx.getSource().getPlayerOrException().getItemInHand(InteractionHand.MAIN_HAND);
        if (held.getItem() == Items.AIR) throw new SimpleCommandExceptionType(new TranslatableComponent("autooredictconv.command.odc._must_be_holding")).create();
        ItemTags.getAllTags().getMatchingTags(held.getItem()).forEach(tag -> {
            remove(tag,ctx);
        });
        ConversionsConfig.save();
        return Command.SINGLE_SUCCESS;
    }
    public static int removeTag(CommandContext<CommandSourceStack> ctx) {
        ResourceLocation tag = ResourceLocationArgument.getId(ctx,"tag");
        remove(tag,ctx);
        ConversionsConfig.save();
        return Command.SINGLE_SUCCESS;
    }
    private static void remove (ResourceLocation tag, CommandContext<CommandSourceStack> ctx) {
        Conversions.tagConversionMap.remove(tag);
        ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.remove",tag),true);
    }
    public static int pause(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        try {
            String name = ctx.getSource().getPlayerOrException().getScoreboardName();
            if (AutoOreDictConv.pausedPlayers.contains(name)) {
                AutoOreDictConv.pausedPlayers.remove(name);
                ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.pause.unpause"),false);
            } else {
                AutoOreDictConv.pausedPlayers.add(name);
                ctx.getSource().sendSuccess(new TranslatableComponent("autooredictconv.command.odc.pause.pause"),false);
            }
            return Command.SINGLE_SUCCESS;
        } catch (Exception ex) {
            throw new SimpleCommandExceptionType(new TranslatableComponent("autooredictconv.command.odc.pause.invalid")).create();
        }
    }
}
