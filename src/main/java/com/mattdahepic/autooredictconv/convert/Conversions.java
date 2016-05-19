package com.mattdahepic.autooredictconv.convert;

import com.mattdahepic.mdecore.helpers.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Conversions {
    public static Map<String,ItemStack> conversionMap = new HashMap<String, ItemStack>();
    /* HELPERS */
    public static boolean itemHasConversion (ItemStack stack) {
        for (int id : OreDictionary.getOreIDs(stack)) {
            String oreName = OreDictionary.getOreName(id);
            if (conversionMap.containsKey(oreName)) return true;
        }
        return false;
    }
    /* CONVERSION */
    public static void convert (EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) { //and every item
            ItemStack playerStack = player.inventory.getStackInSlot(i);
            if (playerStack != null) { //not empty slot
                if (itemHasConversion(playerStack)) {
                    player.inventory.setInventorySlotContents(i, convert(playerStack));
                    player.inventory.markDirty();
                }
            }
        }
    }

    /**
     * Attempts to convert an item and returns the converted item if possible, the original otherwise
     * @param itemToConvert the item to attempt to convert
     * @return the converted item or the original if not possible
     */
    public static ItemStack convert (@Nonnull ItemStack itemToConvert) {
        List<String> oreNames = new ArrayList<String>();
        for (int id : OreDictionary.getOreIDs(itemToConvert)) oreNames.add(OreDictionary.getOreName(id));
        for (String name : oreNames) { //for each oredict name this item is
            if (conversionMap.containsKey(name)) { //do we have a conversion?
                ItemStack templateStack = conversionMap.get(name).copy();
                templateStack.stackSize = itemToConvert.stackSize;
                return templateStack;
            }
        }
        return itemToConvert;
    }

    public static class Config {
        private static File configFile;

        public static void load(File file) {
            configFile = file;
            try {
                if (!configFile.exists()) {
                    configFile.createNewFile();
                    writeDefaults(configFile);
                }
                Scanner scn = new Scanner(configFile);
                while (scn.hasNextLine()) {
                    parse(scn.nextLine());
                }
                scn.close();
            } catch (IOException ignored) {
            }
        }
        private static void parse(String line) {
            try {
                if (line.startsWith("//")) return;
                //oreDict=modid:itemName@metaValue
                String oreDict = line.substring(0, line.indexOf("="));
                String modid = line.substring(line.indexOf("=") + 1, line.indexOf(":"));
                String name;
                int meta;
                if (!line.contains("@")) { //no meta specified
                    name = line.substring(line.indexOf("=") + 1);
                    meta = 0;
                } else {
                    name = line.substring(line.indexOf(":") + 1, line.indexOf("@"));
                    meta = Integer.parseInt(line.substring(line.indexOf("@") + 1));
                }
                ItemStack stack = GameRegistry.makeItemStack(modid+":"+name,meta,1,null);
                conversionMap.put(oreDict, stack);
            } catch (Exception e) {
                throw new RuntimeException("Error processing entry \"" + line + "\"! Does the item exist?", e);
            }
        }
        public static void reloadFromDisk() {
            conversionMap.clear();
            load(configFile);
        }
        public static boolean remove (String oreDict) {
            boolean success = false;
            try {
                File temp = new File(configFile.getAbsolutePath()+".tmp");
                BufferedWriter out = new BufferedWriter(new FileWriter(temp));
                Scanner scn = new Scanner(configFile);
                while (scn.hasNextLine()) {
                    String line = scn.nextLine();
                    if (!line.startsWith(oreDict)) out.write(line+"\n");
                }
                scn.close();
                out.close();
                configFile.delete();
                success = temp.renameTo(configFile);
                if (success) conversionMap.remove(oreDict);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return success;
        }
        public static void addAndWwrite(String oreDict, ItemStack item) {
            if (!ItemHelper.isSameIgnoreStackSize(conversionMap.get(oreDict),item,false))
                conversionMap.put(oreDict,item);
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(configFile, true));
                out.append(oreDict + "=" + Item.itemRegistry.getNameForObject(item.getItem()) + "@" + item.getMetadata() + "\n");
                out.close();
            } catch (IOException e) {
            }
        }

        private static void writeDefaults(File file) {
            try {
                FileWriter out = new FileWriter(file);
                out.write("oreIron=minecraft:iron_ore@0\n");
                out.write("oreGold=minecraft:gold_ore@0\n");
                out.write("oreLapis=minecraft:lapis_ore@0\n");
                out.write("oreDiamond=minecraft:diamond_ore@0\n");
                out.write("oreEmerald=minecraft:emerald_ore@0\n");
                out.close();
            } catch (IOException e) {
            }
        }
    }
}
