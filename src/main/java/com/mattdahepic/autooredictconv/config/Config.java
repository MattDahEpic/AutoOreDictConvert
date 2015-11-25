package com.mattdahepic.autooredictconv.config;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Config {
    public static Map<String,ItemStack> conversions = new HashMap<String, ItemStack>();
    static File configFile;
    public static void load (File config) {
        configFile = config;
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
                addDefaults(configFile);
            }
            Scanner scn = new Scanner(configFile);
            while (scn.hasNextLine()) {
                parse(scn.nextLine());
            }
            scn.close();
        } catch (IOException e) {}
    }
    private static void parse (String line) {
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
            ItemStack stack = new ItemStack(GameRegistry.findItem(modid, name));
            stack.setItemDamage(meta);
            stack.stackSize = 1;
            add(oreDict, stack);
        } catch (Exception e) {
            throw new RuntimeException("Error processing entry \""+line+"\"! Does the item exist?",e);
        }
    }
    public static void reloadFromDisk () {
        conversions.clear();
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
            if (success) conversions.remove(oreDict);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }
    public static void add (String oreDict, ItemStack item) {
        if (conversions.containsKey(oreDict)) {
            conversions.replace(oreDict, item);
        } else {
            conversions.put(oreDict, item);
        }
    }
    public static void write (String oreDict, ItemStack item) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(configFile, true));
            out.append(oreDict + "=" + Item.itemRegistry.getNameForObject(item.getItem()) + "@" + item.getItemDamage() + "\n");
            out.close();
        } catch (IOException e) {}
    }
    private static void addDefaults (File file) {
        try {
            FileWriter out = new FileWriter(file);
            out.write("oreIron=minecraft:iron_ore@0\n");
            out.write("oreGold=minecraft:gold_ore@0\n");
            out.write("oreLapis=minecraft:lapis_ore@0\n");
            out.write("oreDiamond=minecraft:diamond_ore@0\n");
            out.write("oreEmerald=minecraft:emerald_ore@0\n");
            out.close();
        } catch (IOException e) {}
    }
}