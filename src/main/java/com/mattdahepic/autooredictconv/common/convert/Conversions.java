package com.mattdahepic.autooredictconv.common.convert;

import com.mattdahepic.mdecore.common.helpers.ItemHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
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
            "forge:dusts",
            "forge:gears",
            "forge:coins",
            "forge:plates",
            "minecraft:beacon_payment_items",
            "minecraft:piglin_loved",
            "flux:market_accept",
            "minecolonies:sawmill_ingredient_excluded",
            "minecolonies:blacksmith_product",
            "minecolonies:blacksmith_ingredient",
            "minecolonies:reduceable_product_excluded",
            "minecolonies:reduceable_ingredient",
            "resourcefulbees:valid_apiary"
    );
    /* HELPERS */
    public static boolean itemHasConversion (ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (itemConversionMap.containsKey(stack.getItem()) && !ItemHelper.isSameIgnoreStackSize(new ItemStack(itemConversionMap.get(stack.getItem())),stack,false)) return true;
        for (ResourceLocation tag : ItemTags.getAllTags().getMatchingTags(stack.getItem())) {
            //if item has conversion and item isn't already converted
            if (tagConversionMap.containsKey(tag) && !ItemHelper.isSameIgnoreStackSize(new ItemStack(tagConversionMap.get(tag)),stack,false)) return true;
        }
        return false;
    }
    /* CONVERSION */
    public static void convert (Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) { //for every item
            ItemStack playerStack = player.getInventory().getItem(i);
            if (!playerStack.isEmpty()) { //not empty slot
                if (itemHasConversion(playerStack)) {
                    ItemStack convertedItem = convert(playerStack);
                    player.getInventory().setItem(i, ItemStack.EMPTY); //clear out the converted items slot
                    ItemHandlerHelper.insertItemStacked(new PlayerInvWrapper(player.getInventory()),convertedItem,false); //put the converted items in, stacking with items already there
                    player.getInventory().setChanged(); //refresh the client
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
        if (itemConversionMap.containsKey(item.getItem())) {
            return new ItemStack(itemConversionMap.get(item.getItem()),item.getCount());
        }
        for (ResourceLocation tag : ItemTags.getAllTags().getMatchingTags(item.getItem())) { //for each tag this item has
            if (tagConversionMap.containsKey(tag)) {
                return new ItemStack(tagConversionMap.get(tag),item.getCount());
            }
        }
        return item;
    }
}
