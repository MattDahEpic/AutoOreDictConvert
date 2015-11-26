package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;

public class FindLogic implements ICommandLogic {
    public static final String USAGE = "/odc find <ore name>";
    public static FindLogic instance = new FindLogic();

    public String getCommandName () {
        return "find";
    }
    public int getPermissionLevel () {
        return -1;
    }
    public String getCommandSyntax () {
        return USAGE;
    }
    public void handleCommand (ICommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"You didn\'t specify a Ore Dictionary name! Use \"/odc help\" for help."));
            return;
        }
        //args[1] = oreDictName
        List<ItemStack> itemsUnderOreDict = OreDictionary.getOres(args[1]);
        if (!itemsUnderOreDict.isEmpty()) {
            sender.addChatMessage(new ChatComponentText("Ore names under entry " + EnumChatFormatting.AQUA + args[1] + EnumChatFormatting.RESET + " are:"));
            for (int i = 0; i < itemsUnderOreDict.size(); i++) {
                sender.addChatMessage(new ChatComponentText(Item.itemRegistry.getNameForObject(itemsUnderOreDict.get(i).getItem()) + "@" + itemsUnderOreDict.get(i).getItemDamage()));
            }
            return;
        } else {
            sender.addChatMessage(new ChatComponentText("There are no items under this name or this name is not registered!"));
            return;
        }
    }
    public List<String> addTabCompletionOptions (ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 1 ? Arrays.asList(OreDictionary.getOreNames()) : null;
    }
}
