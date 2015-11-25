package com.mattdahepic.autooredictconv.command;

import com.mattdahepic.autooredictconv.config.Config;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class Remove {
    public static void remove (ICommandSender sender, ItemStack item) {
        int[] oreIDs = OreDictionary.getOreIDs(item);
        List<String> oreDictNames = new ArrayList<String>();
        for (int id : oreIDs) oreDictNames.add(OreDictionary.getOreName(id));
        for (String name : oreDictNames) {
            if (Config.conversions.containsKey(name)) {
                if (Config.remove(name)) {
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Successfully removed conversion for "+EnumChatFormatting.AQUA+name+EnumChatFormatting.GREEN+"!"));
                    continue;
                }
            }
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Did not remove conversion for "+EnumChatFormatting.AQUA+name+EnumChatFormatting.RED+"!"));
        }
    }
}
