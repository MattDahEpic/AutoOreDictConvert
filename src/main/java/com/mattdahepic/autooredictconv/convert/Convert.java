package com.mattdahepic.autooredictconv.convert;

import com.mattdahepic.autooredictconv.OreDictConv;
import com.mattdahepic.autooredictconv.config.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class Convert implements Runnable {
    @Override
    public void run () {
        System.out.println("run");
        List<EntityPlayer> players = OreDictConv.mcServer.getConfigurationManager().playerEntityList;
        for (EntityPlayer player : players) { //for all players on server
            for (ItemStack playerStack : player.getInventory()) { //and all items in inventory
                for (String key : Config.conversions.keySet()) { //check for every possible conversion for that item
                    ItemStack templateStack = Config.conversions.get(key);
                    if (templateStack.getItem().equals(playerStack.getItem())) { //does the item match?
                        if (templateStack.getMetadata() == playerStack.getMetadata()) { //does the metadata match?
                            playerStack = new ItemStack(templateStack.getItem(),playerStack.stackSize,templateStack.getMetadata());
                        }
                    }
                }
            }
        }
    }
}