package com.mattdahepic.autooredictconv.convert;

import com.mattdahepic.autooredictconv.AutoOreDictConv;
import com.mattdahepic.autooredictconv.config.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class Convert {
    public static void convertPlayers() {
        List<EntityPlayer> players = AutoOreDictConv.mcServer.getConfigurationManager().playerEntityList;
        for (EntityPlayer player : players) { //for all players on server
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) { //and every item
                ItemStack playerStack = player.inventory.getStackInSlot(i);
                if (playerStack != null) { //not empty slot
                    int[] oreIDs = OreDictionary.getOreIDs(playerStack);
                    List<String> oreNames = new ArrayList<String>();
                    for (int id : oreIDs) oreNames.add(OreDictionary.getOreName(id));
                    for (String name : oreNames) { //for each oredict name this item is
                        if (Config.conversions.containsKey(name)) { //do we have a conversion?
                            ItemStack templateStack = Config.conversions.get(name).copy();
                            templateStack.stackSize = playerStack.stackSize;
                            player.inventory.setInventorySlotContents(i,templateStack);
                            player.inventory.markDirty();
                        }
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
                    if (Config.conversions.containsKey(name)) { //do we have a conversion?
                        ItemStack templateStack = Config.conversions.get(name).copy();
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