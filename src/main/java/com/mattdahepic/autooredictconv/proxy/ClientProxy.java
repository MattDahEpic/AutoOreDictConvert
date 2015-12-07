package com.mattdahepic.autooredictconv.proxy;

import com.mattdahepic.autooredictconv.AutoOreDictConv;
import com.mattdahepic.autooredictconv.block.BlockConverter;
import com.mattdahepic.autooredictconv.keypress.KeyHandler;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(AutoOreDictConv.converter), 0, new ModelResourceLocation(AutoOreDictConv.MODID + ":" + BlockConverter.NAME, "inventory"));
    }
    @Override
    public void registerKeys () {
        KeyHandler.init();
    }
}
