package com.mattdahepic.autooredictconv.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

public class ConverterBlock extends Block implements EntityBlock {
    public static final String NAME = "converter";
    public ConverterBlock() {
        super(Properties.of(Material.WOOD)
                .sound(SoundType.WOOD)
                .strength(2.5f));
    }

    @Override @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ConverterTile(pos, state);
    }
}
