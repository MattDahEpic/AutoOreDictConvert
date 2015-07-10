package com.mattdahepic.autooredictconv.config;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Config {
    public static Map<String,ItemStack> conversions = new HashMap<String, ItemStack>();
    static File configFile;
    public static void load (File config) {
        configFile = config;
        try {
            if (!config.exists()) {
                config.createNewFile();
                addDefaults(config);
            }
            FileReader in = new FileReader(config);
            BufferedReader read = new BufferedReader(in);
            System.out.println("gets here.");
            for (String line; (line = read.readLine()) != null;) {
                System.out.println("parse");
                parse(line,conversions);
            }
            read.close();
            in.close();
        } catch (Exception e) {}
        System.out.println(conversions.keySet());
    }
    private static void parse (String line, Map<String,ItemStack> list) {
        //oreDict=modid:itemName@metaValue
        String oreDict = line.substring(0,line.indexOf("="));
        Item itemName = Item.getByNameOrId(line.substring(line.indexOf("="+1),line.indexOf("@")));
        int meta;
        try {
            meta = Integer.parseInt(line.substring(line.indexOf("@")+1));
        } catch (NumberFormatException e) {
            meta = 0;
        }
        ItemStack item = new ItemStack(itemName,1,meta);
        System.out.println("adding line \""+line+"\" as oreDict="+oreDict+", item=" + item.getDisplayName()+" with meta="+item.getMetadata());
        add(list,oreDict,item);
    }
    public static void add (Map<String,ItemStack> list, String oreDict, ItemStack item) {
        if (list.containsKey(oreDict)) {
            list.replace(oreDict,item);
        } else {
            list.put(oreDict,item);
        }
    }
    private static void addDefaults (File file) {
        try {
            FileWriter out = new FileWriter(file);
            out.write("oreIron=minecraft:iron_ore@0\n");
            out.write("oreGold=minecraft:gold_ore@0\n");
            out.close();
        } catch (Exception e) {}
    }
}