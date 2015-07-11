package com.mattdahepic.autooredictconv.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class Find {
    public static void find (ICommandSender commandSender, String oreDictName) {
        List<ItemStack> itemsUnderOreDict = OreDictionary.getOres(oreDictName);
        if (!itemsUnderOreDict.isEmpty()) {
            commandSender.addChatMessage(new ChatComponentText("Ore names under entry " + EnumChatFormatting.AQUA+ oreDictName +EnumChatFormatting.RESET+ " are:"));
            for (int i = 0; i < itemsUnderOreDict.size(); i++) {
                commandSender.addChatMessage(new ChatComponentText(Item.itemRegistry.getNameForObject(itemsUnderOreDict.get(i).getItem()) + "@" + itemsUnderOreDict.get(i).getItemDamage()));
            }
            return;
        } else {
            commandSender.addChatMessage(new ChatComponentText("There are no items under this name or this name is not registered!"));
            return;
        }
    }
}
