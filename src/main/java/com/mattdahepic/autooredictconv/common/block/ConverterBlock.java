package com.mattdahepic.autooredictconv.common.block;

import com.mattdahepic.autooredictconv.common.AutoOreDictConv;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ConverterBlock extends Block {
    public static final String NAME = "converter";
    public ConverterBlock() {
        super(Properties.create(Material.WOOD));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Override @Nullable
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ConverterTile();
    }
}
