package com.mattdahepic.autooredictconv.block;

import com.mattdahepic.autooredictconv.convert.Conversions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileConverter extends TileEntity implements ISidedInventory {
    private static final int SIZE = 1;
    private NonNullList<ItemStack> contents = NonNullList.withSize(SIZE,ItemStack.EMPTY);

    @Override public ITextComponent getDisplayName () { return new TextComponentString(getName()); }
    @Override public String getName () { return "Auto Converter"; }
    @Override public boolean hasCustomName() { return true; }
    @Override public int getSizeInventory() {return SIZE;}
    @Override public int getInventoryStackLimit() {return 64;}
    @Override public void openInventory(EntityPlayer player) {}
    @Override public void closeInventory(EntityPlayer player) {}
    @Override public int[] getSlotsForFace (EnumFacing side) { return side == EnumFacing.UP || side == EnumFacing.DOWN ? new int[]{0} : new int[]{}; }
    @Override public boolean isEmpty () {return contents.isEmpty();}
    @Override public boolean isUsableByPlayer (EntityPlayer player) { return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D; }

    @Override public boolean canInsertItem (int slot, ItemStack stack, EnumFacing side) { return side == EnumFacing.UP && contents.get(slot) == ItemStack.EMPTY && isItemValidForSlot(slot,stack); }
    @Override public boolean canExtractItem (int slot, ItemStack item, EnumFacing dir) { return dir == EnumFacing.DOWN && this.getStackInSlot(slot) != null; }
    @Override public boolean isItemValidForSlot (int slot, ItemStack stack) { return slot < contents.size() && Conversions.itemHasConversion(stack); }
    @Override public ItemStack getStackInSlot (int slot) { return slot < contents.size() ? contents.get(slot) : ItemStack.EMPTY; }
    @Override public void clear() { for (int i = 0; i < this.getSizeInventory(); ++i) this.setInventorySlotContents(i,ItemStack.EMPTY); }

    @Override
    public ItemStack removeStackFromSlot (int slot) {
        ItemStack temp = getStackInSlot(slot);
        contents.set(slot,ItemStack.EMPTY);
        return temp;
    }
    @Override
    public void setInventorySlotContents (int slot, ItemStack stack) {
        contents.set(slot,Conversions.convert(stack));
        if (stack != null && stack.getCount() > this.getInventoryStackLimit()) stack.setCount(this.getInventoryStackLimit());
        this.markDirty();
    }
    @Override
    public ItemStack decrStackSize (int slot, int amount) {
        ItemStack ret;
        if (contents.get(slot).getCount() <= amount) { //not enough items
            ret = contents.get(slot);
            contents.set(slot,ItemStack.EMPTY);
        } else { //enough items
            ret = contents.get(slot).splitStack(amount);
            if (contents.get(slot).getCount() == 0) contents.set(slot,ItemStack.EMPTY);
        }
        this.markDirty();
        return ret;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
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
        return nbt;
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList list = nbt.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            this.setInventorySlotContents(slot, new ItemStack(stackTag));
        }
    }

    @Override public int getField(int id) { return 0; }
    @Override public void setField(int id, int value) {}
    @Override public int getFieldCount() { return 0; }
}
