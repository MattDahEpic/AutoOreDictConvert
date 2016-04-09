package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.autooredictconv.convert.Conversions;
import com.mattdahepic.mdecore.command.AbstractCommand;
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

public class RemoveLogic implements ICommandLogic {
    public static RemoveLogic instance = new RemoveLogic();

    public String getCommandName () {
        return "remove";
    }
    public int getPermissionLevel () {
        return 2;
    }
    public String getCommandSyntax () {
        return "/odc remove <ore name>";
    }
    public void handleCommand (MinecraftServer server, ICommandSender sender, String[] args) {
        ItemStack item = ((EntityPlayer) sender).getHeldItem(EnumHand.MAIN_HAND);
        if (item != null) {
            List<String> oreDictNames = new ArrayList<String>();
            for (int id : OreDictionary.getOreIDs(item)) oreDictNames.add(OreDictionary.getOreName(id));
            for (String name : oreDictNames) {
                if (Conversions.conversionMap.containsKey(name)) {
                    if (Conversions.Config.remove(name)) {
                        sender.addChatMessage(new TextComponentString(TextFormatting.GREEN+"Successfully removed conversion for "+ TextFormatting.AQUA+name+TextFormatting.GREEN+"!"));
                        continue;
                    }
                }
                sender.addChatMessage(new TextComponentString(TextFormatting.RED+"Did not remove conversion for "+TextFormatting.AQUA+name+TextFormatting.RED+"!"));
            }
        } else if (args.length == 2) {
            String name = args[1];
            if (Conversions.conversionMap.containsKey(name)) {
                if (Conversions.Config.remove(name)) {
                    sender.addChatMessage(new TextComponentString(TextFormatting.GREEN+"Successfully removed conversion for "+TextFormatting.AQUA+name+TextFormatting.GREEN+"!"));
                }
            }
            sender.addChatMessage(new TextComponentString(TextFormatting.RED+"Did not remove conversion for "+TextFormatting.AQUA+name+TextFormatting.RED+"!"));
        } else {
            sender.addChatMessage(new TextComponentString(TextFormatting.RED+"You\'re not holding an item and didn't specify a name to remove!"));
        }
    }
    public List<String> getTabCompletionOptions (MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 2 ? AbstractCommand.getListOfStringsMatchingLastWord(args,Conversions.conversionMap.keySet()) : null;
    }
}
