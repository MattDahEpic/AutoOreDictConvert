package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.autooredictconv.config.ConversionsConfig;
import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class ReloadLogic implements ICommandLogic {
    public static final String USAGE = "/odc list";
    public static ReloadLogic instance = new ReloadLogic();

    public String getCommandName () {
        return "reload";
    }
    public int getPermissionLevel () {
        return 2;
    }
    public String getCommandSyntax () {
        return USAGE;
    }
    public void handleCommand (ICommandSender sender, String[] args) {
        ConversionsConfig.reloadFromDisk();
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Conversions successfully reloaded from disk."));
    }
    public List<String> addTabCompletionOptions (ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }
}
