package com.mattdahepic.autooredictconv.common.block;

import com.mattdahepic.autooredictconv.common.AutoOreDictConv;
import com.mattdahepic.autooredictconv.common.convert.Conversions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;

public class ConverterTile extends TileEntity implements ISidedInventory {
    public static final TileEntityType<?> TYPE = TileEntityType.Builder.create(ConverterTile::new,new ConverterBlock()).build(null).setRegistryName(AutoOreDictConv.MODID,"converter");

    private static final int SIZE = 1;
    private NonNullList<ItemStack> contents = NonNullList.withSize(SIZE,ItemStack.EMPTY);

    public ConverterTile() {
        super(TYPE);
    }

    @Override public int getSizeInventory() {return SIZE;}
    @Override public int getInventoryStackLimit() {return 64;}
    @Override public void openInventory(PlayerEntity player) {}
    @Override public void closeInventory(PlayerEntity player) {}
    @Override public int[] getSlotsForFace (Direction side) { return side == Direction.UP || side == Direction.DOWN ? new int[]{0} : new int[]{}; }
    @Override public boolean isEmpty () {return contents.isEmpty();}
    @Override public boolean isUsableByPlayer (PlayerEntity player) { return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D; }

    @Override public boolean canInsertItem (int slot, ItemStack stack, Direction side) { return side == Direction.UP && contents.get(slot) == ItemStack.EMPTY && isItemValidForSlot(slot,stack); }
    @Override public boolean canExtractItem (int slot, ItemStack item, Direction dir) { return dir == Direction.DOWN && this.getStackInSlot(slot) != null; }
    @Override public boolean isItemValidForSlot (int slot, ItemStack stack) { return slot < contents.size() && Conversions.itemHasConversion(stack); }
    @Override public ItemStack getStackInSlot (int slot) { return slot < contents.size() ? contents.get(slot) : ItemStack.EMPTY; }
    @Override public void clear() { for (int i = 0; i < this.getSizeInventory(); ++i) this.setInventorySlotContents(i,ItemStack.EMPTY); }

    @Override
    public ItemStack removeStackFromSlot (int slot) {
        return contents.set(slot,ItemStack.EMPTY);
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
            ret = contents.get(slot).split(amount);
            if (contents.get(slot).getCount() == 0) contents.set(slot,ItemStack.EMPTY);
        }
        this.markDirty();
        return ret;
    }
}
