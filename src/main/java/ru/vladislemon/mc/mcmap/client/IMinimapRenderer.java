package ru.vladislemon.mc.mcmap.client;

import com.mojang.blaze3d.vertex.PoseStack;

public interface IMinimapRenderer {
    void render(PoseStack poseStack, float partialTick);
}
