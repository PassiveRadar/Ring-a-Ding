package com.radar.ringading.init;

import com.radar.ringading.Ringading;
import com.radar.ringading.block.RedstoneServiceBellBlock;
import com.radar.ringading.block.ServiceBellBlock;
import com.radar.ringading.item.RedstoneServiceBellItem;
import com.radar.ringading.item.ServiceBellItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final RegistryObject<Block> SERVICE_BELL = Ringading.BLOCKS.register("service_bell",
            () -> new ServiceBellBlock(BlockBehaviour.Properties.of()
                    .strength(0.1F)
                    .sound(SoundType.METAL)
                    .mapColor(MapColor.GOLD)
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Item> SERVICE_BELL_ITEM = Ringading.ITEMS.register("service_bell",
            () -> new ServiceBellItem(SERVICE_BELL.get(), new Item.Properties()));


    public static final RegistryObject<Block> REDSTONE_SERVICE_BELL = Ringading.BLOCKS.register("redstone_service_bell",
            () -> new RedstoneServiceBellBlock(BlockBehaviour.Properties.of()
                    .strength(0.1F)
                    .sound(SoundType.METAL)
                    .mapColor(MapColor.GOLD)
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Item> REDSTONE_SERVICE_BELL_ITEM = Ringading.ITEMS.register("redstone_service_bell",
            () -> new RedstoneServiceBellItem(REDSTONE_SERVICE_BELL.get(), new Item.Properties()));

    public static void register() {
    }
} 