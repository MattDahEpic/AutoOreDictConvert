package com.mattdahepic.autooredictconv.proxy;

import com.mattdahepic.autooredictconv.AutoOreDictConv;
import com.mattdahepic.autooredictconv.block.BlockConverter;
import com.mattdahepic.autooredictconv.block.TileConverter;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void registerRenderers() {}
    public void registerKeys () {}
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> e) {
        AutoOreDictConv.converter = new BlockConverter();
        e.getRegistry().register(AutoOreDictConv.converter.setRegistryName(BlockConverter.NAME));
    }
    @SubscribeEvent
    public void registerItems (RegistryEvent.Register<Item> e) {
        System.out.println("######################\nITEM REGISTER\n################");
        e.getRegistry().register(new ItemBlock(AutoOreDictConv.converter).setRegistryName(BlockConverter.NAME));
    }
    public void registerTiles() {
        GameRegistry.registerTileEntity(TileConverter.class,"auto_converter");
    }
    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> e) {
        GameRegistry.addShapedRecipe(new ResourceLocation("auto_converter"),new ResourceLocation("autooredictconv"),new ItemStack(AutoOreDictConv.converter),"aca","aba","aca",'a', Blocks.CRAFTING_TABLE,'b',Blocks.CHEST,'c', Items.STRING);
    }
}
