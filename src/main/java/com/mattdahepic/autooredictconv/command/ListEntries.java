package com.mattdahepic.autooredictconv.command;

import com.mattdahepic.autooredictconv.config.Config;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Set;

public class ListEntries {
    public static void list (ICommandSender sender) {
        Set<String> entries = Config.conversions.keySet();
        sender.addChatMessage(new ChatComponentText("The configured conversions are:"));
        for (String entry : entries) {
            ItemStack item = Config.conversions.get(entry);
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA+entry+EnumChatFormatting.RESET+"="+EnumChatFormatting.GREEN+item.getDisplayName()));
        }
    }
}
