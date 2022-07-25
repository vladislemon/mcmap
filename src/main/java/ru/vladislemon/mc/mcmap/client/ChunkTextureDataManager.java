package ru.vladislemon.mc.mcmap.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

public class ChunkTextureDataManager implements IChunkUpdateListener {
    private final IChunkTextureDataBuilder chunkTextureDataBuilder;
    private final ByteBuffer textureBuffer = ByteBuffer.allocateDirect(16 * 16 * 4);
    private int textureId = 0;

    public ChunkTextureDataManager(IChunkTextureDataBuilder chunkTextureDataBuilder) {
        this.chunkTextureDataBuilder = chunkTextureDataBuilder;
    }

    public int getTextureId() {
        if (textureId == 0) {
            initTexture();
        }
        return textureId;
    }

    @Override
    public void onChunkUpdate(ChunkAccess chunkAccess) {
        RenderSystem.recordRenderCall(() -> {
            if (textureId == 0) {
                initTexture();
            }
            textureBuffer.clear();
            chunkTextureDataBuilder.build(chunkAccess, textureBuffer);
            textureBuffer.flip();
            uploadTextureData(chunkAccess.getPos());
        });
    }

    private void initTexture() {
        textureId = GL11.glGenTextures();
        RenderSystem.bindTextureForSetup(textureId);
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 256, 256, 0, GL11.GL_RGBA, GL11.GL_BYTE, (ByteBuffer) null);
    }

    private void uploadTextureData(ChunkPos chunkPos) {
        int x = mod(chunkPos.x, 16) * 16;
        int z = mod(-chunkPos.z, 16) * 16;
        RenderSystem.bindTextureForSetup(textureId);
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, z, 16, 16, GL11.GL_RGBA, GL11.GL_BYTE, textureBuffer);
    }

    private static int mod(int a, int b) {
        return (a % b + b) % b;
    }
}
