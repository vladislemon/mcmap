package ru.vladislemon.mc.mcmap.client;

import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.ArrayList;
import java.util.List;

public class DelegatingChunkUpdateListener implements IChunkUpdateListener {
    private final List<IChunkUpdateListener> listeners = new ArrayList<>();

    public void addListener(IChunkUpdateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IChunkUpdateListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onChunkUpdate(ChunkAccess chunkAccess) {
        for (IChunkUpdateListener listener : listeners) {
            listener.onChunkUpdate(chunkAccess);
        }
    }
}
