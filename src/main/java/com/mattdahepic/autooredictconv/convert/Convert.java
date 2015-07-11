package com.mattdahepic.autooredictconv.convert;

import com.mattdahepic.autooredictconv.OreDictConv;
import com.mattdahepic.autooredictconv.config.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class Convert {
    public static void convert () {
        List<EntityPlayer> players = OreDictConv.mcServer.getConfigurationManager().playerEntityList;
        for (EntityPlayer player : players) { //for all players on server
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack playerStack = player.inventory.getStackInSlot(i);
                if (playerStack != null) {
                    int[] oreIDs = OreDictionary.getOreIDs(playerStack);
                    List<String> oreNames = new ArrayList<String>();
                    for (int id : oreIDs) oreNames.add(OreDictionary.getOreName(id));
                    for (String name : oreNames) {
                        if (Config.conversions.containsKey(name)) {
                            ItemStack templateStack = Config.conversions.get(name);
                            int stackSize = playerStack.stackSize;
                            player.inventory.decrStackSize(i,stackSize);
                            player.inventory.addItemStackToInventory(new ItemStack(templateStack.getItem(),stackSize,templateStack.getMetadata()));
                            player.inventory.markDirty();
                        }
                    }
                }
            }
        }
    }
}