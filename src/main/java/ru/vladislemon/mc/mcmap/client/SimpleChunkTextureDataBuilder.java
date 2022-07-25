package ru.vladislemon.mc.mcmap.client;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.MaterialColor;

import java.nio.ByteBuffer;

public class SimpleChunkTextureDataBuilder implements IChunkTextureDataBuilder {

    @Override
    public void build(ChunkAccess chunkAccess, ByteBuffer buffer) {
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        ChunkPos pos = chunkAccess.getPos();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                //int height = chunkAccess.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
                //int color = Math.max(0, height);
                /*blockPos.set(x, height, z);
                BlockState blockState = chunkAccess.getBlockState(blockPos);
                MaterialColor mapColor = blockState.getMapColor(chunkAccess, blockPos);
                buffer.put((byte) ((mapColor.col >> 16) & 0xFF));
                buffer.put((byte) ((mapColor.col >> 8) & 0xFF));
                buffer.put((byte) (mapColor.col & 0xFF));
                buffer.put(Byte.MAX_VALUE);*/
                buffer.put((byte) pos.x);
                buffer.put((byte) pos.z);
                buffer.put((byte) 0);
                buffer.put(Byte.MAX_VALUE);
            }
        }
    }
}
