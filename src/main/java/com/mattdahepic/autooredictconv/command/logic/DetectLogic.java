package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class DetectLogic implements ICommandLogic {
    public static final String USAGE = "/odc detect";
    public static DetectLogic instance = new DetectLogic();

    public String getCommandName () {
        return "detect";
    }
    public int getPermissionLevel () {
        return -1;
    }
    public String getCommandSyntax () {
        return USAGE;
    }
    public void handleCommand (ICommandSender sender, String[] args) {
        ItemStack item = ((EntityPlayer) sender).getHeldItem();
        if (item != null) {
            int[] oreDictEntryNumbers = OreDictionary.getOreIDs(item);
            String[] oreDictEntryNames = new String[oreDictEntryNumbers.length];
            for (int i = 0; i < oreDictEntryNumbers.length; i++) { //add names to array
                oreDictEntryNames[i] = OreDictionary.getOreName(oreDictEntryNumbers[i]);
            }
            sender.addChatMessage(new ChatComponentText("Ore dictionary names for item \"" + EnumChatFormatting.AQUA+ Item.itemRegistry.getNameForObject(item.getItem()) + "@" + item.getItemDamage() + EnumChatFormatting.RESET+"\" are:"));
            for (int i = 0; i < oreDictEntryNames.length; i++) { //print names
                sender.addChatMessage(new ChatComponentText(oreDictEntryNames[i]));
            }
        } else {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"You\'re not holding an item!"));
        }
    }
    public List<String> addTabCompletionOptions (ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }
}
