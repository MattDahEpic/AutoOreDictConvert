package com.mattdahepic.autooredictconv.proxy;

import com.mattdahepic.autooredictconv.AutoOreDictConv;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(AutoOreDictConv.converter),0,new ModelResourceLocation("autooredictconv:converter","inventory"));
    }
}
