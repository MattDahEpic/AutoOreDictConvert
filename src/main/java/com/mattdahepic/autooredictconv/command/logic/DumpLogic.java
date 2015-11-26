package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class DumpLogic implements ICommandLogic {
    public static final String USAGE = "/odc dump";
    public static DumpLogic instance = new DumpLogic();

    public String getCommandName () {
        return "dump";
    }
    public int getPermissionLevel () {
        return -1;
    }
    public String getCommandSyntax () {
        return USAGE;
    }
    public void handleCommand (ICommandSender sender, String[] args) {
        String[] oreDictNames = OreDictionary.getOreNames();
        sender.addChatMessage(new ChatComponentText("Dumping all Ore Dictionary entries..."));
        for (int i = 0; i < oreDictNames.length; i++) {
            sender.addChatMessage(new ChatComponentText(oreDictNames[i]));
        }
    }
    public List<String> addTabCompletionOptions (ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }
}
