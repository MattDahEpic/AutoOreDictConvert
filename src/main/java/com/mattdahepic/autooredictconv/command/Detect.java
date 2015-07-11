package com.mattdahepic.autooredictconv.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

public class Detect {
    private Detect () {}
    public static void detect (ICommandSender commandSender, ItemStack item) {
        int[] oreDictEntryNumbers = OreDictionary.getOreIDs(item);
        String[] oreDictEntryNames = new String[oreDictEntryNumbers.length];
        for (int i = 0; i < oreDictEntryNumbers.length; i++) { //add names to array
            oreDictEntryNames[i] = OreDictionary.getOreName(oreDictEntryNumbers[i]);
        }
        commandSender.addChatMessage(new ChatComponentText("Ore dictionary names for item \"" + EnumChatFormatting.AQUA+ Item.itemRegistry.getNameForObject(item.getItem()) + "@" + item.getItemDamage() + EnumChatFormatting.RESET+"\" are:"));
        for (int i = 0; i < oreDictEntryNames.length; i++) { //print names
            commandSender.addChatMessage(new ChatComponentText(oreDictEntryNames[i]));
        }
    }
}
