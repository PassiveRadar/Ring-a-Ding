package com.radar.ringading;

import com.mojang.logging.LogUtils;
import com.radar.ringading.client.renderer.ServiceBellRenderer;
import com.radar.ringading.init.ModBlockEntities;
import com.radar.ringading.init.ModBlocks;
import com.radar.ringading.network.PacketHandler;
import com.radar.ringading.sound.ModSounds;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Ringading.MODID)
public class Ringading {

    // very important pls, also some MOD_ID, while I MODID, anyways idk which came first...
    public static final String MODID = "ringading";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    private static final Logger LOGGER = LogUtils.getLogger();

    public Ringading() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModSounds.register(modEventBus);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        ModBlocks.register();
        ModBlockEntities.register();

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Ringading mod is initializing!");
        event.enqueueWork(() -> {
            PacketHandler.register();
            LOGGER.info("Registered network packets");
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.SERVICE_BELL_ITEM);
            event.accept(ModBlocks.REDSTONE_SERVICE_BELL_ITEM);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BlockEntityRenderers.register(ModBlockEntities.SERVICE_BELL.get(), ServiceBellRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.REDSTONE_SERVICE_BELL.get(), ServiceBellRenderer::new);
            LOGGER.info("Ring-a-ding mod client setup complete! Please enjoy annoying your friends with service bells!");
        }
    }
}
