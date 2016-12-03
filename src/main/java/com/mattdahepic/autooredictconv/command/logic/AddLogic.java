package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.autooredictconv.convert.Conversions;
import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class AddLogic implements ICommandLogic {
    public static AddLogic instance = new AddLogic();

    public String getCommandName () {
        return "add";
    }
    public int getPermissionLevel () {
        return 2;
    }
    public String getCommandSyntax () {
        return "/odc add";
    }
    public void handleCommand (MinecraftServer server, ICommandSender sender, String[] args) {
        ItemStack item = ((EntityPlayer) sender).getHeldItem(EnumHand.MAIN_HAND);
        if (item != null) {
            List<String> oreNames = new ArrayList<String>();
            for (int id : OreDictionary.getOreIDs(item)) oreNames.add(OreDictionary.getOreName(id));
            for (String name : oreNames) {
                Conversions.Config.addAndWwrite(name,item);
                sender.sendMessage(new TextComponentString("Added " + TextFormatting.AQUA + item.getDisplayName() + TextFormatting.RESET + " as the default conversion entry for " + TextFormatting.AQUA + name + TextFormatting.RESET + "."));
            }
        } else {
            sender.sendMessage(new TextComponentString(TextFormatting.RED+"You\'re not holding an item!"));
        }
    }
    public List<String> getTabCompletionList (MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {return null;}
}
