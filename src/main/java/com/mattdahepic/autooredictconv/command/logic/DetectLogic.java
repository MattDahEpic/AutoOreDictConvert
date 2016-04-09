package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class DetectLogic implements ICommandLogic {
    public static DetectLogic instance = new DetectLogic();

    public String getCommandName () {
        return "detect";
    }
    public int getPermissionLevel () {
        return -1;
    }
    public String getCommandSyntax () {
        return "/odc detect";
    }
    public void handleCommand (MinecraftServer server, ICommandSender sender, String[] args) {
        ItemStack item = ((EntityPlayer) sender).getHeldItem(EnumHand.MAIN_HAND);
        if (item != null) {
            sender.addChatMessage(new TextComponentString("Ore dictionary names for item \"" + TextFormatting.AQUA+ Item.itemRegistry.getNameForObject(item.getItem()) + "@" + item.getItemDamage() + TextFormatting.RESET+"\" are:"));

            for (int i : OreDictionary.getOreIDs(item)) { //print names
                sender.addChatMessage(new TextComponentString(OreDictionary.getOreName(i)));
            }
        } else {
            sender.addChatMessage(new TextComponentString(TextFormatting.RED+"You\'re not holding an item!"));
        }
    }
    public List<String> getTabCompletionOptions (MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {return null;}
}
