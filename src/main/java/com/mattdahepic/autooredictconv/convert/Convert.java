package com.mattdahepic.autooredictconv.convert;

/*
public class Convert {
    public static String[] oreValues = Config.oreValues;
    private Convert () {}
    public static void register () {}
    //@SideOnly(Side.SERVER)
    public static void convert (EntityPlayerMP player) {
        if (!player.worldObj.isRemote) { //if on server
            InventoryPlayer inventoryPlayer = player.inventory;
            if (oreValues != null) { //if config is empty: OH NO! GOD NO! WHY! WHY WOULD SOMEONE DO THAT!
                for (int i = 0; i < inventoryPlayer.getSizeInventory(); i++) { //i = inventory slot of player
                    //Log.info("Checking inventory slot " + i);
                    if (inventoryPlayer.getStackInSlot(i) != null) {
                        //Log.info("Slot " + i + " contains item.");
                        for (int j = 0; j < oreValues.length; j++) { //j = how many ore values in config
                            //Log.info(" Checking slot for config entry " + j);
                            int[] oreIds = OreDictionary.getOreIDs(inventoryPlayer.getStackInSlot(i));
                            for (int k = 0; k < oreIds.length; k++) { //k = how many ore dict entries this item is registered to
                                //Log.info("  Checking ore dictionary entry " + k + " for this item.");
                                String oreName = OreDictionary.getOreName(oreIds[k]);
                                if (oreValues[j].startsWith(oreName)) {
                                    //Log.info("   This slot matches this config entry! Converting!");
                                    int tempSize = inventoryPlayer.getStackInSlot(i).stackSize;
                                    for (int l = 1; l <= tempSize; l++) { //l = amount of items in slot that matches the ore value
                                        ItemStack tempItem = Config.getItem(oreName);
                                        if (tempItem != null) {
                                            //Log.info("    Converted item! Round " + l);
                                            inventoryPlayer.decrStackSize(i, 1);
                                            inventoryPlayer.addItemStackToInventory(Config.getItem(oreName));
                                        } else {
                                            //Log.warn("    Config item for entry " + oreName + " is invalid. Not converting item.");
                                        }
                                    }
                                } else {
                                    //Log.warn("  Ore dictionary entry " + k + " for this item does not match config entry " + j + ".");
                                }
                            }
                        }
                    } else {
                        //Log.info("Slot " + i + " empty!");
                    }
                }
            } else {
                //Log.warn("No ore dictionary convertable items defined in config!");
            }
        } else {
            //Log.error("Attempting to convert on client! Not Allowed!");
        }
    }
}
*/