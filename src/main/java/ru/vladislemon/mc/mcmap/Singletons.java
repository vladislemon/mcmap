package ru.vladislemon.mc.mcmap;

import ru.vladislemon.mc.mcmap.client.DelegatingChunkUpdateListener;

public class Singletons {
    public static final DelegatingChunkUpdateListener CHUNK_UPDATE_LISTENER = new DelegatingChunkUpdateListener();
}
