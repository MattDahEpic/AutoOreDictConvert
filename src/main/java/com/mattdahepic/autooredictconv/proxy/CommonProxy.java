package com.mattdahepic.autooredictconv.proxy;

import com.mattdahepic.autooredictconv.common.AutoOreDictConv;
import com.mattdahepic.autooredictconv.common.block.ConverterBlock;
import com.mattdahepic.autooredictconv.common.block.ConverterTile;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
    /*public void registerRenderers() {}
    public void registerKeys () {}
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> e) {
        AutoOreDictConv.converter = new ConverterBlock();
        e.getRegistry().register(AutoOreDictConv.converter.setRegistryName(ConverterBlock.NAME));
    }
    @SubscribeEvent
    public void registerItems (RegistryEvent.Register<Item> e) {
        System.out.println("######################\nITEM REGISTER\n################");
        e.getRegistry().register(new ItemBlock(AutoOreDictConv.converter).setRegistryName(ConverterBlock.NAME));
    }
    public void registerTiles() {
        GameRegistry.registerTileEntity(ConverterTile.class,"auto_converter");
    }
    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> e) {
        GameRegistry.addShapedRecipe(new ResourceLocation("auto_converter"),new ResourceLocation("autooredictconv"),new ItemStack(AutoOreDictConv.converter),"aca","aba","aca",'a', Blocks.CRAFTING_TABLE,'b',Blocks.CHEST,'c', Items.STRING);
    }*/
}
