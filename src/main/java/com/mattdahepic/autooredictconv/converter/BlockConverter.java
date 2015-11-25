package com.mattdahepic.autooredictconv.converter;

import com.mattdahepic.autooredictconv.AutoOreDictConv;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockConverter extends BlockContainer {
    public static final String NAME = "converter";
    public IIcon[] icon = new IIcon[6];
    public BlockConverter () {
        super(Material.dragonEgg);
        this.setBlockName(NAME);
        this.setBlockTextureName(NAME);
        this.setCreativeTab(CreativeTabs.tabAllSearch);
    }
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileConverter();
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        for (int i = 0; i < 6; i++) {
            this.icon[i] = reg.registerIcon(AutoOreDictConv.MODID+":"+NAME+"_"+i);
        }
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        return this.icon[side];
    }
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileConverter te = (TileConverter)world.getTileEntity(x,y,z);
        //BEGIN ITEM DROPS
        Random rand = new Random();
        TileEntity tile = world.getTileEntity(x,y,z);
        if (!(tile instanceof IInventory)) return;
        IInventory inv = (IInventory)tile;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack item = inv.getStackInSlot(i);
            if (item != null && item.stackSize > 0) {
                EntityItem entityItem = new EntityItem(world,x,y,z,item);
                float factor = 0.05F; //change this to a rlly big number to freak players out
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor + 0.2F;
                world.spawnEntityInWorld(entityItem);
                inv.setInventorySlotContents(i,null);
            }
        }
        //END ITEM DROPS
        super.breakBlock(world, x, y, z, block, meta);
    }
}
