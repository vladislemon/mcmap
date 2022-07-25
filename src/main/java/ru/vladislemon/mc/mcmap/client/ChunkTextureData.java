package ru.vladislemon.mc.mcmap.client;

import net.minecraft.world.level.ChunkPos;

import java.nio.ByteBuffer;

public record ChunkTextureData(ChunkPos chunkPos, ByteBuffer buffer) {
}
