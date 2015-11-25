package com.mattdahepic.autooredictconv.converter;

import com.mattdahepic.autooredictconv.converter.BlockConverter;
import com.mattdahepic.autooredictconv.convert.Convert;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TileConverter extends TileEntity implements ISidedInventory {
    public static final String NAME = "converter";
    public static final String INVENTORY_NAME = "Auto Converter";
    public static final int SIZE = 15;
    public ItemStack[] contents = new ItemStack[SIZE];
    public TileConverter () {}
    @Override
    public int getSizeInventory() {
        return SIZE;
    }
    @Override
    public ItemStack getStackInSlot(int slot) {
        return contents[slot];
    }
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.contents[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) stack.stackSize = this.getInventoryStackLimit();
        this.markDirty();
    }
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    @Override
    public void openInventory() {}
    @Override
    public void closeInventory() {}
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }
    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return getStackInSlot(slot);
    }
    @Override
    public boolean hasCustomInventoryName () {
        return true;
    }
    @Override
    public boolean isItemValidForSlot (int slot, ItemStack stack) {
        return true;
    }
    @Override
    public ItemStack decrStackSize (int slot, int amount) {
        if (contents[slot] != null) {
            ItemStack ret;
            if (contents[slot].stackSize <= amount) { //not enough items
                ret = contents[slot];
                contents[slot] = null;
            } else { //enough items
                ret = contents[slot].splitStack(amount);
                if (contents[slot].stackSize == 0) contents[slot] = null;
            }
            this.markDirty();
            return ret;
        }
        return null;
    }
    @Override
    public String getInventoryName() {
        return INVENTORY_NAME;
    }
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i) != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }
        nbt.setTag("Items", list);
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList list = nbt.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
        }
    }
    @Override
    public boolean canInsertItem(int slot,ItemStack item,int side) {
        return side == 1;
    }
    @Override
    public boolean canExtractItem(int slot,ItemStack item,int side) {
        return side == 0 && this.getStackInSlot(slot) != null;
    }
    @Override
    public int[] getAccessibleSlotsFromSide (int side) {
        return new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
    }
    @Override
    public void updateEntity () {
        super.updateEntity();
        if (Convert.convertInventory(contents)) this.markDirty();
    }
}
