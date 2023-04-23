package com.mattdahepic.autooredictconv.common.block;

import com.mattdahepic.autooredictconv.common.AutoOreDictConv;
import com.mattdahepic.autooredictconv.common.convert.Conversions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConverterTile extends BlockEntity implements ICapabilityProvider, IItemHandler {
    public static final BlockEntityType<?> TYPE = BlockEntityType.Builder.of(ConverterTile::new, AutoOreDictConv.CONVERTER_BLOCK.get()).build(null);

    private static final int SIZE = 1;
    private NonNullList<ItemStack> contents = NonNullList.withSize(SIZE,ItemStack.EMPTY);

    public ConverterTile(BlockPos pos, BlockState state) {
        super(TYPE, pos, state);
    }

    @Override public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
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
        if (!simulate) this.setChanged();
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
