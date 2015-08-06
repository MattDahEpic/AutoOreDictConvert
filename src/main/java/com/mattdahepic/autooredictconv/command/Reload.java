package com.mattdahepic.autooredictconv.command;

import com.mattdahepic.autooredictconv.config.Config;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Reload {
    public static void reload (ICommandSender sender) {
        Config.reloadFromDisk();
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Conversions successfully reloaded from disk."));
    }
}
