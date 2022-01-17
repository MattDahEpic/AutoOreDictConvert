package com.mattdahepic.autooredictconv.common.block;

import com.mattdahepic.mdecore.common.registries.BlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AutoOreDictConvBlocks extends BlockRegistry {
    public static Block converter;

    protected CreativeModeTab getGroup() { return CreativeModeTab.TAB_MISC; }

    @SubscribeEvent
    public void register(RegistryEvent.Register<Block> event) {
        converter = registerBlock(new ConverterBlock(),"converter");
    }
}
