package com.mattdahepic.autooredictconv.convert;

import com.mattdahepic.autooredictconv.config.ConversionsConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class Convert {
    public static void convertAllPlayers() {
        List<EntityPlayerMP> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        for (EntityPlayerMP player : players) { //for all players on server
            convertPlayer(player);
        }
    }
    public static void convertPlayer (EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) { //and every item
            ItemStack playerStack = player.inventory.getStackInSlot(i);
            if (playerStack != null) { //not empty slot
                int[] oreIDs = OreDictionary.getOreIDs(playerStack);
                List<String> oreNames = new ArrayList<String>();
                for (int id : oreIDs) oreNames.add(OreDictionary.getOreName(id));
                for (String name : oreNames) { //for each oredict name this item is
                    if (ConversionsConfig.conversions.containsKey(name)) { //do we have a conversion?
                        ItemStack templateStack = ConversionsConfig.conversions.get(name).copy();
                        templateStack.stackSize = playerStack.stackSize;
                        player.inventory.setInventorySlotContents(i,templateStack);
                        player.inventory.markDirty();
                    }
                }
            }
        }
    }
    public static boolean convertInventory(ItemStack[] inventory) {
        boolean success = false;
        for (int i = 0; i < inventory.length; i++) { //for every item
            ItemStack invStack = inventory[i];
            if (invStack != null) { //not empty slot
                int[] oreIDs = OreDictionary.getOreIDs(invStack);
                List<String> oreNames = new ArrayList<String>();
                for (int id : oreIDs) oreNames.add(OreDictionary.getOreName(id));
                for (String name : oreNames) { //for each oredict name this item is
                    if (ConversionsConfig.conversions.containsKey(name)) { //do we have a conversion?
                        ItemStack templateStack = ConversionsConfig.conversions.get(name).copy();
                        templateStack.stackSize = invStack.stackSize;
                        inventory[i] = templateStack;
                        success=true;
                    }
                }
            }
        }
        return success;
    }
}