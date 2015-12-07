package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class HelpLogic implements ICommandLogic {
    public static final String USAGE = "/odc help";
    public static HelpLogic instance = new HelpLogic();

    public String getCommandName () {
        return "help";
    }
    public int getPermissionLevel () {
        return -1;
    }
    public String getCommandSyntax () {
        return USAGE;
    }
    public void handleCommand (ICommandSender sender, String[] args) {
        sender.addChatMessage(new ChatComponentText("To get the ore dictionary entries of the item currently held, use \"/odc detect\"."));
        sender.addChatMessage(new ChatComponentText("To dump all ore dictionary entries to the chat and log, use \"/odc dump\"."));
        sender.addChatMessage(new ChatComponentText("To find all items listed as the specified Ore Dictionary name, use \"/odc find <oreDictName>\"."));
        sender.addChatMessage(new ChatComponentText("To add the currently held item as the default for it's ore dictionary entries, use \"/odc add\"."));
        sender.addChatMessage(new ChatComponentText("To see all current configured items and their ore dictionary entries, use \"/odc list\"."));
        sender.addChatMessage(new ChatComponentText("To reload any changes made outside of the game, use \"/odc reload\"."));
        sender.addChatMessage(new ChatComponentText("To remove the currently held item or specified name's conversion, use \"/odc remove\"."));
    }
    public List<String> addTabCompletionOptions (ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }
}
