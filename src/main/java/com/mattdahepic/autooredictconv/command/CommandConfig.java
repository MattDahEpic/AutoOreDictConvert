package com.mattdahepic.autooredictconv.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class CommandConfig extends CommandBase {
    private List aliases, tabCompletionOptions;
    public CommandConfig () {
        this.aliases = new ArrayList();
        this.aliases.add("odc");
        this.tabCompletionOptions = new ArrayList();
        this.tabCompletionOptions.add("detect");
        this.tabCompletionOptions.add("dump");
        this.tabCompletionOptions.add("find");
        this.tabCompletionOptions.add("list");
        this.tabCompletionOptions.add("add");
        this.tabCompletionOptions.add("reload");
        this.tabCompletionOptions.add("remove");
        this.tabCompletionOptions.add("help");
    }
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    @Override
    public String getCommandName () {
        return "odc";
    }
    @Override
    public String getCommandUsage (ICommandSender iCommandSender) {
        return EnumChatFormatting.RED + "/odc <command>";
    }
    @Override
    public List getCommandAliases () {
        return this.aliases;
    }
    @Override
    public void processCommand (ICommandSender sender, String[] args) {
        if (args.length == 0) { // no input command
            sender.addChatMessage(new ChatComponentText("Use \"/odc help\" for usage help."));
            return;
        } else { //message contains data
            ItemStack item = null;
            if (sender instanceof EntityPlayer) { //if the command runner is a player, get the item they are holding
                item = ((EntityPlayer) sender).getHeldItem();
            }
            if (args[0].equalsIgnoreCase("detect")) {
                if (item != null) {
                    Detect.detect(sender, item);
                    return;
                } else {
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"You\'re not holding an item!"));
                    return;
                }
            } else if (args[0].equalsIgnoreCase("dump")) {
                Dump.dump(sender);
                return;
            } else if (args[0].equalsIgnoreCase("find")) {
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"You didn\'t specify a Ore Dictionary name! Use \"/odc help\" for help."));
                    return;
                }
                Find.find(sender, args[1]);
                return;
            } else if (args[0].equalsIgnoreCase("add")) {
                if (item != null) {
                    Add.add(sender, item);
                    return;
                } else {
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "You\'re not holding an item!"));
                    return;
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                ListEntries.list(sender);
                return;
            } else if (args[0].equalsIgnoreCase("reload")) {
                Reload.reload(sender);
                return;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (item != null) {
                    Remove.remove(sender, item);
                    return;
                } else {
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"You\'re not holding an item!"));
                    return;
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                sender.addChatMessage(new ChatComponentText("To get the ore dictionary entries of the item currently held, use \"/odc detect\"."));
                sender.addChatMessage(new ChatComponentText("To dump all ore dictionary entries to the chat and log, use \"/odc dump\"."));
                sender.addChatMessage(new ChatComponentText("To find all items listed as the specified Ore Dictionary name, use \"/odc find <oreDictName>\"."));
                sender.addChatMessage(new ChatComponentText("To add the currently held item as the default for it's ore dictionary entries, use \"/odc add\"."));
                sender.addChatMessage(new ChatComponentText("To see all current configured items and their ore dictionary entries, use \"/odc list\"."));
                sender.addChatMessage(new ChatComponentText("To reload any config changes made outside of the game, use \"/odc reload\"."));
                sender.addChatMessage(new ChatComponentText("To remove the currently held item's conversion (to replace it with another for example), use \"/odc remove\"."));
                return;
            } else {
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Not a valid argument! Us \"/odc help\" to see command usage."));
                return;
            }
        }
    }
    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        return this.tabCompletionOptions;
    }
    @Override
    public boolean isUsernameIndex(String[] inputString, int i) {
        return false;
    }
}