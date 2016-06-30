package com.mattdahepic.autooredictconv.block;

import com.mattdahepic.mdecore.helpers.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockConverter extends Block implements ITileEntityProvider {
    public static final String NAME = "auto_converter";
    public BlockConverter () {
        super(Material.DRAGON_EGG);
        this.setUnlocalizedName(NAME);
        this.setCreativeTab(CreativeTabs.SEARCH);
    }
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileConverter();
    }
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        //BEGIN ITEM DROPS
        if (!(tile instanceof IInventory)) return;
        IInventory inv = (IInventory)tile;
        WorldHelper.dropItemsFromInventory(RANDOM,inv,world,pos);
        //END ITEM DROPS
        tile.invalidate();
        super.breakBlock(world,pos,state);
    }
}
