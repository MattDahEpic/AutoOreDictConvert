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
    public static Map<ResourceLocation, Item> tagConversionMap = new HashMap<>();
    public static Map<Item,Item> itemConversionMap = new HashMap<>();
    public static final List<String> tagBlacklist = Arrays.asList(
            "forge:ores",
            "forge:ingots",
            "forge:blocks",
            "minecraft:beacon_payment_items"
    );
    /* HELPERS */
    public static boolean itemHasConversion (ItemStack stack) {
        if (stack.isEmpty()) return false;
        for (ResourceLocation tag : ItemTags.getCollection().getOwningTags(stack.getItem())) {
            //if item has conversion and item isn't already converted
            if (tagConversionMap.containsKey(tag) && !ItemHelper.isSameIgnoreStackSize(new ItemStack(tagConversionMap.get(tag)),stack,false)) return true;
            if (itemConversionMap.containsKey(stack.getItem()) && !ItemHelper.isSameIgnoreStackSize(new ItemStack(itemConversionMap.get(stack.getItem())),stack,false)) return true;
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
            if (tagConversionMap.containsKey(tag)) {
                return new ItemStack(tagConversionMap.get(tag),item.getCount());
            }
            if (itemConversionMap.containsKey(item.getItem())) {
                return new ItemStack(itemConversionMap.get(item.getItem()),item.getCount());
            }
        }
        return item;
    }
}
