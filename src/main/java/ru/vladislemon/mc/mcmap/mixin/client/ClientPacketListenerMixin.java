package ru.vladislemon.mc.mcmap.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.vladislemon.mc.mcmap.Singletons;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
//	@Inject(method = "handleChunkBlocksUpdate", at = @At("RETURN"))
//	public void handleChunkBlocksUpdateFTBC(ClientboundSectionBlocksUpdatePacket packet, CallbackInfo ci) {
//		FTBChunksClient.handlePacket(packet);
//	}

    @Inject(method = "handleLevelChunkWithLight", at = @At("RETURN"))
    public void handleLevelChunkFTBC(ClientboundLevelChunkWithLightPacket packet, CallbackInfo ci) {
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            ChunkAccess chunkAccess = level.getChunk(packet.getX(), packet.getZ(), ChunkStatus.FULL, false);
            if (chunkAccess != null) {
                Singletons.CHUNK_UPDATE_LISTENER.onChunkUpdate(chunkAccess);
            }
        }
    }

//	@Inject(method = "handleBlockUpdate", at = @At("RETURN"))
//	public void handleBlockUpdateFTBC(ClientboundBlockUpdatePacket packet, CallbackInfo ci) {
//		FTBChunksClient.handlePacket(packet);
//	}
}
