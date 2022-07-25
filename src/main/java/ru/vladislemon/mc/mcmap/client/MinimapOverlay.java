package ru.vladislemon.mc.mcmap.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.util.Random;

public class MinimapOverlay extends GuiComponent implements IIngameOverlay {
    long tick = 0;
    //int textureId = 0;
    int size = 128;
    int alpha = 255;
    //ByteBuffer textureBuffer = ByteBuffer.allocateDirect(size * size * 4);
    private final ChunkTextureDataManager chunkTextureDataManager;

    public MinimapOverlay(ChunkTextureDataManager chunkTextureDataManager) {
        this.chunkTextureDataManager = chunkTextureDataManager;
        /*Random random = new Random();
        while (textureBuffer.hasRemaining()) {
            textureBuffer.put((byte) (random.nextInt(255) - 128));
        }
        textureBuffer.flip();*/
    }

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        RenderSystem.enableTexture();
        /*if (tick % 1000 == 0) {
            if (textureId == 0) {
                generateTexture();
            }
            updateTexture();
        }*/
        LocalPlayer player = Minecraft.getInstance().player;
        Vec3 pos = player.position().scale(1.0 / 256.0);
        float left = (float) (pos.x);
        float right = (float) (pos.x + 2);
        float top = (float) (-pos.z + 2);
        float bottom = (float) (-pos.z);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, chunkTextureDataManager.getTextureId());
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();
        poseStack.pushPose();
        poseStack.translate(width - size, 0, 0);
        Matrix4f matrix = poseStack.last().pose();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.vertex(matrix, 0, 0, 0F)
                .uv(left, top)
                .endVertex();
        builder.vertex(matrix, 0, size, 0F)
                .uv(left, bottom)
                .endVertex();
        builder.vertex(matrix, size, size, 0F)
                .uv(right, bottom).
                endVertex();
        builder.vertex(matrix, size, 0, 0F)
                .uv(right, top)
                .endVertex();
        tesselator.end();
        poseStack.popPose();

        tick++;
    }

//    private void generateTexture() {
//        textureId = GL11.glGenTextures();
//        RenderSystem.bindTextureForSetup(textureId);
//        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
//        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
//        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, size, size, 0, GL11.GL_RGBA, GL11.GL_BYTE, (ByteBuffer) null);
//    }
//
//    private void updateTexture() {
//        RenderSystem.bindTextureForSetup(textureId);
//        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, size, size, GL11.GL_RGBA, GL11.GL_BYTE, textureBuffer);
//    }
}
