package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.autooredictconv.config.ConversionsConfig;
import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.Set;

public class ListLogic implements ICommandLogic {
    public static final String USAGE = "/odc list";
    public static ListLogic instance = new ListLogic();

    public String getCommandName () {
        return "list";
    }
    public int getPermissionLevel () {
        return -1;
    }
    public String getCommandSyntax () {
        return USAGE;
    }
    public void handleCommand (ICommandSender sender, String[] args) {
        Set<String> entries = ConversionsConfig.conversions.keySet();
        sender.addChatMessage(new ChatComponentText("The configured conversions are:"));
        for (String entry : entries) {
            ItemStack item = ConversionsConfig.conversions.get(entry);
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA+entry+EnumChatFormatting.RESET+"="+EnumChatFormatting.GREEN+item.getDisplayName()));
        }
    }
    public List<String> addTabCompletionOptions (ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }
}
