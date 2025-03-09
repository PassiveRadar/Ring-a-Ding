package com.radar.ringading.network;

import com.radar.ringading.Ringading;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Ringading.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        // Register packets here
        INSTANCE.registerMessage(packetId++, S2CBellRingPacket.class,
                S2CBellRingPacket::encode,
                S2CBellRingPacket::decode,
                S2CBellRingPacket::handle);
    }
} 