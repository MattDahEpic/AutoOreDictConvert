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
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandODC {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("odc")
                .requires(s -> s.hasPermissionLevel(4))
                .executes(CommandRegistry::missingArgument)
                .then(Commands.literal("help")
                        .executes(CommandODC::help))
                .then(Commands.literal("detect")
                        .executes(CommandODC::detect))
                .then(Commands.literal("dump")
                        .executes(CommandODC::dump))
                .then(Commands.literal("find")
                        .executes(CommandRegistry::missingArgument)
                        .then(Commands.argument("tag", ResourceLocationArgument.resourceLocation())
                                .suggests((ctx, bld) -> ISuggestionProvider.suggest(ItemTags.getCollection().getRegisteredTags().stream().map(ResourceLocation::toString),bld))
                                .executes(CommandODC::find)))
                .then(Commands.literal("list")
                        .executes(CommandODC::list))
                .then(Commands.literal("add")
                        .executes(CommandODC::add))
                .then(Commands.literal("set")
                        .executes(CommandRegistry::missingArgument)
                        .then(Commands.argument("tag", ResourceLocationArgument.resourceLocation())
                                .suggests((ctx, bld) -> ISuggestionProvider.suggest(ItemTags.getCollection().getRegisteredTags().stream().map(ResourceLocation::toString),bld))
                                .executes(CommandODC::set)))
                .then(Commands.literal("reload")
                        .executes(CommandODC::reload))
                .then(Commands.literal("remove")
                        .executes(CommandODC::removeHand)
                        .then(Commands.argument("tag",ResourceLocationArgument.resourceLocation())
                                .suggests((ctx,bld) -> ISuggestionProvider.suggest(Conversions.tagConversionMap.keySet().stream().map(ResourceLocation::toString),bld))
                                .executes(CommandODC::removeTag)))
                .then(Commands.literal("pause")
                        .requires(s -> s.hasPermissionLevel(0))
                        .executes(CommandODC::pause));
        LiteralCommandNode<CommandSource> command = dispatcher.register(builder);
    }
    public static int help(CommandContext<CommandSource> ctx) {
        for (int i = 0; i < 9; i++) {
            ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.help."+i),false);
        }
        return Command.SINGLE_SUCCESS;
    }
    public static int detect(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ItemStack item = ctx.getSource().asPlayer().getHeldItem(Hand.MAIN_HAND);
        ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.detect",item.getItem().getRegistryName()),true);
        ItemTags.getCollection().getOwningTags(item.getItem()).forEach(r -> {
            ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc._each",r),true);
        });
        return Command.SINGLE_SUCCESS;
    }
    public static int dump(CommandContext<CommandSource> ctx) {
        ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.dump"),true);
        ItemTags.getCollection().getRegisteredTags().forEach(r -> {
            ctx.getSource().sendFeedback(new StringTextComponent(r.toString()),true);
        });
        return Command.SINGLE_SUCCESS;
    }
    public static int find(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ResourceLocation loc = ResourceLocationArgument.getResourceLocation(ctx,"tag");
        ITag<Item> tag = ItemTags.getCollection().get(loc);
        if (tag == null) throw new SimpleCommandExceptionType(new TranslationTextComponent("autooredictconv.command.odc._no_tag",loc)).create();
        ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.find",loc),true);
        tag.getAllElements().forEach(i -> {
            ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc._each",i.getRegistryName()),true);
        });
        return Command.SINGLE_SUCCESS;
    }
    public static int list(CommandContext<CommandSource> ctx) {
        ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.list"),false);
        if (!Conversions.tagConversionMap.isEmpty()) {
            ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.list.tag"),true);
            Conversions.tagConversionMap.forEach((tag, item) -> {
                ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc._each_pair", tag, item.getRegistryName()), true);
            });
        }
        if (!Conversions.itemConversionMap.isEmpty()) {
            ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.list.item"),true);
            Conversions.itemConversionMap.forEach((in, out) -> {
                ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc._each_pair",in.getRegistryName(),out.getRegistryName()),true);
            });
        }
        return Command.SINGLE_SUCCESS;
    }
    public static int add(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ItemStack held = ctx.getSource().asPlayer().getHeldItem(Hand.MAIN_HAND);
        if (held.getItem() == Items.AIR) throw new SimpleCommandExceptionType(new TranslationTextComponent("autooredictconv.command.odc._must_be_holding")).create();
        int addedTo = 0;
        for (ResourceLocation tag : ItemTags.getCollection().getOwningTags(held.getItem())) {
            if (Conversions.tagBlacklist.contains(tag.toString())) continue; //ignore tags that everything has in addition to their actual entries.
            Conversions.tagConversionMap.put(tag,held.getItem());
            addedTo++;
            ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.add",held.getItem().getRegistryName(),tag),true);
        }
        if (addedTo == 0) ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.add.none"),true);
        ConversionsConfig.save();
        return Command.SINGLE_SUCCESS;
    }
    public static int set(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ResourceLocation loc = ResourceLocationArgument.getResourceLocation(ctx,"tag");
        ITag<Item> tag = ItemTags.getCollection().get(loc);
        if (tag == null) throw new SimpleCommandExceptionType(new TranslationTextComponent("autooredictconv.command.odc._no_tag",loc)).create();
        ItemStack held = ctx.getSource().asPlayer().getHeldItem(Hand.MAIN_HAND);
        if (held.getItem() == Items.AIR) throw new SimpleCommandExceptionType(new TranslationTextComponent("autooredictconv.command.odc._must_be_holding")).create();
        Conversions.tagConversionMap.put(loc,held.getItem());
        ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.add",held.getItem().getRegistryName(),loc),true);
        return Command.SINGLE_SUCCESS;
    }
    public static int reload(CommandContext<CommandSource> ctx) {
        ConversionsConfig.reload();
        ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.reload"),false);
        return Command.SINGLE_SUCCESS;
    }
    public static int removeHand(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ItemStack held = ctx.getSource().asPlayer().getHeldItem(Hand.MAIN_HAND);
        if (held.getItem() == Items.AIR) throw new SimpleCommandExceptionType(new TranslationTextComponent("autooredictconv.command.odc._must_be_holding")).create();
        ItemTags.getCollection().getOwningTags(held.getItem()).forEach(tag -> {
            remove(tag,ctx);
        });
        ConversionsConfig.save();
        return Command.SINGLE_SUCCESS;
    }
    public static int removeTag(CommandContext<CommandSource> ctx) {
        ResourceLocation tag = ResourceLocationArgument.getResourceLocation(ctx,"tag");
        remove(tag,ctx);
        ConversionsConfig.save();
        return Command.SINGLE_SUCCESS;
    }
    private static void remove (ResourceLocation tag, CommandContext<CommandSource> ctx) {
        Conversions.tagConversionMap.remove(tag);
        ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.remove",tag),true);
    }
    public static int pause(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        try {
            String name = ctx.getSource().asPlayer().getScoreboardName();
            if (AutoOreDictConv.pausedPlayers.contains(name)) {
                AutoOreDictConv.pausedPlayers.remove(name);
                ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.pause.unpause"),false);
            } else {
                AutoOreDictConv.pausedPlayers.add(name);
                ctx.getSource().sendFeedback(new TranslationTextComponent("autooredictconv.command.odc.pause.pause"),false);
            }
            return Command.SINGLE_SUCCESS;
        } catch (Exception ex) {
            throw new SimpleCommandExceptionType(new TranslationTextComponent("autooredictconv.command.odc.pause.invalid")).create();
        }
    }
}
