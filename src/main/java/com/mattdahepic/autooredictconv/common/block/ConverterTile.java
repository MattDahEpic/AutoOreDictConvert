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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConverterTile extends TileEntity implements ICapabilityProvider, IItemHandler {
    public static final TileEntityType<?> TYPE = TileEntityType.Builder.create(ConverterTile::new,new ConverterBlock()).build(null).setRegistryName(AutoOreDictConv.MODID,"converter");

    private static final int SIZE = 1;
    private NonNullList<ItemStack> contents = NonNullList.withSize(SIZE,ItemStack.EMPTY);

    public ConverterTile() {
        super(TYPE);
    }

    @Override public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.UP || side == Direction.DOWN) {
                return LazyOptional.of(new NonNullSupplier<T>() {
                    @Nonnull
                    @Override
                    public T get() {
                        return (T) ConverterTile.this;
                    }
                });
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public int getSlots() {
        return contents.size();
    }
    @Override
    public ItemStack getStackInSlot (int slot) {
        return slot < contents.size() ? contents.get(slot) : ItemStack.EMPTY;
    }
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (slot < contents.size()) {
            if (contents.get(slot) == ItemStack.EMPTY) {
                if (Conversions.itemHasConversion(stack)) {
                    if (!simulate) contents.set(slot,Conversions.convert(stack));
                    return ItemStack.EMPTY;
                }
            }
        }
        return stack;
    }
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack ret;
        if (contents.get(slot).getCount() <= amount) { //not enough items
            ret = contents.get(slot);
            if (!simulate) contents.set(slot,ItemStack.EMPTY);
        } else { //enough items
            ret = (simulate ? contents.get(slot).copy() : contents.get(slot)).split(amount);
            if (contents.get(slot).getCount() == 0) contents.set(slot,ItemStack.EMPTY);
        }
        if (!simulate) this.markDirty();
        return ret;
    }
    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }
    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return slot < contents.size() && Conversions.itemHasConversion(stack);
    }
}
