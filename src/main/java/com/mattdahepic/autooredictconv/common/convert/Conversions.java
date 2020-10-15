package com.mattdahepic.autooredictconv.common.convert;

import com.mattdahepic.mdecore.common.helpers.ItemHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

import javax.annotation.Nonnull;
import java.util.*;

public class Conversions {
    public static Map<ResourceLocation, Item> conversionMap = new HashMap<>();
    /* HELPERS */
    public static boolean itemHasConversion (ItemStack stack) {
        if (stack.isEmpty()) return false;
        for (ResourceLocation tag : ItemTags.getCollection().getOwningTags(stack.getItem())) {
            //if item has conversion and item isn't already converted
            if (conversionMap.containsKey(tag) && !ItemHelper.isSameIgnoreStackSize(new ItemStack(conversionMap.get(tag)),stack,false)) return true;
        }
        return false;
    }
    /* CONVERSION */
    public static void convert (PlayerEntity player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) { //for every item
            ItemStack playerStack = player.inventory.getStackInSlot(i);
            if (!playerStack.isEmpty()) { //not empty slot
                if (itemHasConversion(playerStack)) {
                    ItemStack convertedItem = convert(playerStack);
                    player.inventory.setInventorySlotContents(i, ItemStack.EMPTY); //clear out the converted items slot
                    ItemHandlerHelper.insertItemStacked(new PlayerInvWrapper(player.inventory),convertedItem,false); //put the converted items in, stacking with items already there
                    player.inventory.markDirty(); //refresh the client
                }
            }
        }
    }

    /**
     * Attempts to convert an item and returns the converted item if possible, the original otherwise
     * @param item the item to attempt to convert
     * @return the converted item or the original if not possible
     */
    public static ItemStack convert (@Nonnull ItemStack item) {
        for (ResourceLocation tag : ItemTags.getCollection().getOwningTags(item.getItem())) { //for each tag this item has
            if (conversionMap.containsKey(tag)) { //do we have a conversion?
                return new ItemStack(conversionMap.get(tag),item.getCount());
            }
        }
        return item;
    }
}
