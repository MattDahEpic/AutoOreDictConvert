package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.autooredictconv.convert.Conversions;
import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class ReloadLogic implements ICommandLogic {
    public static ReloadLogic instance = new ReloadLogic();

    public String getCommandName () {
        return "reload";
    }
    public int getPermissionLevel () {
        return 2;
    }
    public String getCommandSyntax () {
        return "/odc reload";
    }
    public void handleCommand (MinecraftServer server, ICommandSender sender, String[] args) {
        Conversions.Config.reloadFromDisk();
        sender.sendMessage(new TextComponentString(TextFormatting.GREEN+"Conversions successfully reloaded from disk."));
    }
    public List<String> getTabCompletionList (MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }
}
