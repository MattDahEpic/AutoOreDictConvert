package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.mdecore.command.AbstractCommand;
import com.mattdahepic.mdecore.command.ICommandLogic;
import com.mattdahepic.mdecore.helpers.ItemHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class FindLogic implements ICommandLogic {
    public static FindLogic instance = new FindLogic();

    public String getCommandLogicName () {
        return "find";
    }
    public int getPermissionLevel () {
        return -1;
    }
    public String getCommandSyntax () {
        return "/odc find <ore name>";
    }
    public void handleCommand (MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.addChatMessage(new TextComponentString(TextFormatting.RED+"You didn\'t specify a Ore Dictionary name! Use \"/odc help\" for help."));
            return;
        }
        //args[1] = oreDictName
        List<ItemStack> itemsUnderOreDict = OreDictionary.getOres(args[1]);
        if (!itemsUnderOreDict.isEmpty()) {
            sender.addChatMessage(new TextComponentString("Ore names under entry " + TextFormatting.AQUA + args[1] + TextFormatting.RESET + " are:"));
            for (ItemStack i : itemsUnderOreDict) {
                sender.addChatMessage(new TextComponentString(ItemHelper.getNameFromItemStack(i)));
            }
        } else {
            sender.addChatMessage(new TextComponentString("There are no items under this name or this name is not registered!"));
        }
    }
    public List<String> getTabCompletionList (MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 1 ? AbstractCommand.getListOfStringsMatchingLastWord(args,OreDictionary.getOreNames()) : null;
    }
}
