package com.mattdahepic.autooredictconv.converter;

import com.mattdahepic.autooredictconv.AutoOreDictConv;
import com.mattdahepic.mdecore.helpers.DropHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockConverter extends BlockContainer {
    public static final String NAME = "converter";
    public BlockConverter () {
        super(Material.dragonEgg);
        this.setUnlocalizedName(NAME);
        this.setCreativeTab(CreativeTabs.tabAllSearch);
    }
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileConverter();
    }
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileConverter te = (TileConverter)world.getTileEntity(pos);
        //BEGIN ITEM DROPS
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof IInventory)) return;
        IInventory inv = (IInventory)tile;
        DropHelper.dropItemsFromInventory(new Random(),inv,world,pos);
        //END ITEM DROPS
        tile.invalidate();
        super.breakBlock(world,pos,state);
    }
}
