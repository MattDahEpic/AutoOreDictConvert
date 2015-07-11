package com.mattdahepic.autooredictconv.command;

import com.mattdahepic.autooredictconv.config.Config;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class Add {
    public static void add (ICommandSender sender, ItemStack item) {
        int[] oreIDs = OreDictionary.getOreIDs(item);
        List<String> oreNames = new ArrayList<String>();
        for (int id : oreIDs) {
            oreNames.add(OreDictionary.getOreName(id));
        }
        for (String name : oreNames) {
            Config.add(name,item);
            Config.write(name,item);
            sender.addChatMessage(new ChatComponentText("Added " + EnumChatFormatting.AQUA + item.getDisplayName() + EnumChatFormatting.RESET + " as the default conversion entry for " + EnumChatFormatting.AQUA + name + EnumChatFormatting.RESET + "."));
        }
    }
}
