package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.autooredictconv.convert.Conversions;
import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class ListLogic implements ICommandLogic {
    public static ListLogic instance = new ListLogic();

    public String getCommandName () {
        return "list";
    }
    public int getPermissionLevel () {
        return -1;
    }
    public String getCommandSyntax () {
        return "/odc list";
    }
    public void handleCommand (MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("The configured conversions are:"));
        for (String entry : Conversions.conversionMap.keySet()) {
            ItemStack item = Conversions.conversionMap.get(entry);
            sender.sendMessage(new TextComponentString(TextFormatting.AQUA+entry+TextFormatting.RESET+"="+TextFormatting.GREEN+item.getDisplayName()));
        }
    }
    public List<String> getTabCompletionList (MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }
}
