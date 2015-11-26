package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.autooredictconv.config.ConversionsConfig;
import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class AddLogic implements ICommandLogic {
    public static final String USAGE = "/odc add";
    public static AddLogic instance = new AddLogic();

    public String getCommandName () {
        return "add";
    }
    public int getPermissionLevel () {
        return 2;
    }
    public String getCommandSyntax () {
        return USAGE;
    }
    public void handleCommand (ICommandSender sender, String[] args) {
        ItemStack item = ((EntityPlayer) sender).getHeldItem();
        if (item != null) {
            int[] oreIDs = OreDictionary.getOreIDs(item);
            List<String> oreNames = new ArrayList<String>();
            for (int id : oreIDs) {
                oreNames.add(OreDictionary.getOreName(id));
            }
            for (String name : oreNames) {
                ConversionsConfig.add(name, item);
                ConversionsConfig.write(name, item);
                sender.addChatMessage(new ChatComponentText("Added " + EnumChatFormatting.AQUA + item.getDisplayName() + EnumChatFormatting.RESET + " as the default conversion entry for " + EnumChatFormatting.AQUA + name + EnumChatFormatting.RESET + "."));
            }
        } else {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"You\'re not holding an item!"));
        }
    }
    public List<String> addTabCompletionOptions (ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }
}
