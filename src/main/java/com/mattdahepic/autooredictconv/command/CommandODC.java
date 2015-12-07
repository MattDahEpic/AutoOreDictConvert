package com.mattdahepic.autooredictconv.command;

import com.mattdahepic.autooredictconv.command.logic.*;
import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.*;

public class CommandODC extends CommandBase {
    public static CommandODC instance = new CommandODC();
    private static Map<String, ICommandLogic> commands = new HashMap<String,ICommandLogic>();

    static {
        registerCommandLogic(HelpLogic.instance);
        registerCommandLogic(DetectLogic.instance);
        registerCommandLogic(DumpLogic.instance);
        registerCommandLogic(FindLogic.instance);
        registerCommandLogic(ListLogic.instance);
        registerCommandLogic(AddLogic.instance);
        registerCommandLogic(ReloadLogic.instance);
        registerCommandLogic(RemoveLogic.instance);
    }
    public static void init(FMLServerStartingEvent e) {
        e.registerServerCommand(instance);
    }
    public static String getCommandSyntax (String name) {
        if (getCommandExists(name)) {
            return commands.get(name).getCommandSyntax();
        }
        return null;
    }
    public static boolean registerCommandLogic (ICommandLogic commandLogic) {
        if (!commands.containsKey(commandLogic.getCommandName())) {
            commands.put(commandLogic.getCommandName(), commandLogic);
            return true;
        }
        return false;
    }
    public static Set<String> getCommandList() {
        return commands.keySet();
    }
    public static int getCommandPermission (String command) {
        return getCommandExists(command) ? commands.get(command).getPermissionLevel() : Integer.MAX_VALUE;
    }
    public static boolean getCommandExists (String command) {
        return commands.containsKey(command);
    }
    public static boolean canUseCommand(ICommandSender sender, int requiredPermission, String name) {
        if (getCommandExists(name)) {
            return sender.canCommandSenderUseCommand(requiredPermission, "odc " + name) || sender instanceof EntityPlayerMP && requiredPermission <= 0;
        }
        return false;
    }

    public int getRequiredPermissionLevel() {
        return -1;
    }
    public String getCommandName() {
        return "odc";
    }
    public List getCommandAliases() {
        return Collections.emptyList();
    }
    public String getCommandUsage(ICommandSender sender) {
        return "/" + this.getCommandName() + " help";
    }
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length < 1) {
            args = new String[]{"help"};
        }

        ICommandLogic command = commands.get(args[0]);
        if(command != null) {
            if(canUseCommand(sender, command.getPermissionLevel(), command.getCommandName())) {
                command.handleCommand(sender, args);
            } else {
                throw new CommandException("commands.generic.permission");
            }
        } else {
            throw new CommandNotFoundException();
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args,commands.keySet());
        } else if (commands.containsKey(args[0])) {
            return commands.get(args[0]).addTabCompletionOptions(sender,args,pos);
        }
        return null;
    }
}
