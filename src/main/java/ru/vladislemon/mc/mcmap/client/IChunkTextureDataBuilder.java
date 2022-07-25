package ru.vladislemon.mc.mcmap.client;

import net.minecraft.world.level.chunk.ChunkAccess;

import java.nio.ByteBuffer;

public interface IChunkTextureDataBuilder {

    void build(ChunkAccess chunkAccess, ByteBuffer buffer);

}
