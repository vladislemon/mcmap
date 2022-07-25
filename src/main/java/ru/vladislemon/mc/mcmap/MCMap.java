package ru.vladislemon.mc.mcmap;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;
import ru.vladislemon.mc.mcmap.client.ChunkTextureDataManager;
import ru.vladislemon.mc.mcmap.client.IChunkTextureDataBuilder;
import ru.vladislemon.mc.mcmap.client.MinimapOverlay;
import ru.vladislemon.mc.mcmap.client.SimpleChunkTextureDataBuilder;

@Mod(MCMap.MOD_ID)
public class MCMap {
    public static final String MOD_ID = "mcmap";

    public MCMap() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetupEvent);
        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(
                IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (incoming, isNetwork) -> true)
        );
    }

    @OnlyIn(Dist.CLIENT)
    private void onClientSetupEvent(FMLClientSetupEvent event) {
        IChunkTextureDataBuilder chunkTextureDataBuilder = new SimpleChunkTextureDataBuilder();
        ChunkTextureDataManager chunkTextureDataManager = new ChunkTextureDataManager(chunkTextureDataBuilder);
        MinimapOverlay overlay = new MinimapOverlay(chunkTextureDataManager);
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.HOTBAR_ELEMENT, "mcmap-minimap", overlay);
        Singletons.CHUNK_UPDATE_LISTENER.addListener(chunkTextureDataManager);
    }
}
