package com.mattdahepic.autooredictconv.common.block;

import com.mattdahepic.mdecore.common.registries.TileEntityRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AutoOreDictConvTiles extends TileEntityRegistry {
    @SubscribeEvent
    public void register(RegistryEvent.Register<BlockEntityType<?>> event) {
        event.getRegistry().register(ConverterTile.TYPE);
    }
}
