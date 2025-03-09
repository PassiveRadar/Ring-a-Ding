package com.radar.ringading.init;

import com.radar.ringading.Ringading;
import com.radar.ringading.block.entity.RedstoneServiceBellBlockEntity;
import com.radar.ringading.block.entity.ServiceBellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Ringading.MODID);

    // Just to make sure...
    public static void register() {
    }

    public static final RegistryObject<BlockEntityType<ServiceBellBlockEntity>> SERVICE_BELL =
            BLOCK_ENTITIES.register("service_bell",
                    () -> BlockEntityType.Builder.of(ServiceBellBlockEntity::new, ModBlocks.SERVICE_BELL.get())
                            .build(null));
    public static final RegistryObject<BlockEntityType<RedstoneServiceBellBlockEntity>> REDSTONE_SERVICE_BELL =
            BLOCK_ENTITIES.register("redstone_service_bell",
                    () -> BlockEntityType.Builder.of(RedstoneServiceBellBlockEntity::new, ModBlocks.REDSTONE_SERVICE_BELL.get())
                            .build(null));
} 