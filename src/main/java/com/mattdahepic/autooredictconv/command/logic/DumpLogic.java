package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class DumpLogic implements ICommandLogic {
    public static DumpLogic instance = new DumpLogic();

    public String getCommandName () {
        return "dump";
    }
    public int getPermissionLevel () {
        return -1;
    }
    public String getCommandSyntax () {
        return "/odc dump";
    }
    public void handleCommand (MinecraftServer server, ICommandSender sender, String[] args) {
        String[] oreDictNames = OreDictionary.getOreNames();
        sender.addChatMessage(new TextComponentString("Dumping all Ore Dictionary entries..."));
        for (int i = 0; i < oreDictNames.length; i++) {
            sender.addChatMessage(new TextComponentString(oreDictNames[i]));
        }
    }
    public List<String> getTabCompletionOptions (MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {return null;}
}
