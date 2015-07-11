package com.mattdahepic.autooredictconv.config;

import com.mattdahepic.autooredictconv.OreDictConv;
import com.mattdahepic.mdecore.helpers.LogHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
            if (!config.exists()) {
                config.createNewFile();
                addDefaults(config);
            }
            System.out.println("got here");
            Scanner scn = new Scanner(config);
            while (scn.hasNextLine()) {
                System.out.println("parse");
                parse(scn.nextLine(),conversions);
            }
            scn.close();
        } catch (IOException e) {
            LogHelper.error(OreDictConv.MODID,e.getMessage());
        }
        System.out.println(conversions.keySet());
    }
    private static void parse (String line, Map<String,ItemStack> list) {
        //oreDict=modid:itemName@metaValue
        String oreDict = line.substring(0,line.indexOf("="));
        Item item;
        int meta;
        if (line.indexOf("@") < 0) { //no meta specified
            item = Item.getByNameOrId(line.substring(line.indexOf("=")+1));
            meta = 0;
        } else {
            item = Item.getByNameOrId(line.substring(line.indexOf("=") + 1, line.indexOf("@")));
            meta = Integer.parseInt(line.substring(line.indexOf("@")+1));
        }
        ItemStack stack = new ItemStack(item,1,meta);
        add(list,oreDict,stack);
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