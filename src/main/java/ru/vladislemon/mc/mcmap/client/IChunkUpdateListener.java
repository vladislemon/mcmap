package ru.vladislemon.mc.mcmap.client;

import net.minecraft.world.level.chunk.ChunkAccess;

public interface IChunkUpdateListener {

    void onChunkUpdate(ChunkAccess chunkAccess);

}
