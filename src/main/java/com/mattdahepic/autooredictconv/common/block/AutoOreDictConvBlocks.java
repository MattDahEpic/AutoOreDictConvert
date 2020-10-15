package com.mattdahepic.autooredictconv.common.block;

import com.mattdahepic.mdecore.common.registries.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AutoOreDictConvBlocks extends BlockRegistry {
    public static Block converter;

    protected ItemGroup getGroup() { return ItemGroup.MISC; }

    @SubscribeEvent
    public void register(RegistryEvent.Register<Block> event) {
        converter = registerBlock(new ConverterBlock(),"converter");
    }
}
