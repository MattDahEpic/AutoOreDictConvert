package com.mattdahepic.autooredictconv.proxy;

import com.mattdahepic.autooredictconv.AutoOreDictConv;
import com.mattdahepic.autooredictconv.block.BlockConverter;
import com.mattdahepic.autooredictconv.block.TileConverter;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void registerRenderers() {}
    public void registerKeys () {}
    public void registerBlocks() {
        AutoOreDictConv.converter = new BlockConverter();
        GameRegistry.registerBlock(AutoOreDictConv.converter,BlockConverter.NAME);
    }
    public void registerTiles() {
        GameRegistry.registerTileEntity(TileConverter.class,TileConverter.NAME);
    }
    public void registerRecipes() {
        GameRegistry.addShapedRecipe(new ItemStack(AutoOreDictConv.converter),"aca","aba","aca",'a', Blocks.crafting_table,'b',Blocks.chest,'c', Items.string);
    }
}
