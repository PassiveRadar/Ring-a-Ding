package com.radar.ringading.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.function.Supplier;

public class S2CBellRingPacket {
    private static final Logger LOGGER = LoggerFactory.getLogger(S2CBellRingPacket.class);

    private final UUID targetPlayerUUID;
    private final String interactorName;

    public S2CBellRingPacket(UUID targetPlayerUUID, String interactorName) {
        this.targetPlayerUUID = targetPlayerUUID;
        this.interactorName = interactorName;
    }

    public static void encode(S2CBellRingPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.targetPlayerUUID);
        buf.writeUtf(msg.interactorName);
    }

    public static S2CBellRingPacket decode(FriendlyByteBuf buf) {
        return new S2CBellRingPacket(buf.readUUID(), buf.readUtf());
    }

    public static void handle(S2CBellRingPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
//            LOGGER.debug("Received bell ring packet for player {} from {}", msg.targetPlayerUUID, msg.interactorName);
            Minecraft minecraft = net.minecraft.client.Minecraft.getInstance();
            LocalPlayer clientPlayer = minecraft.player;

            if (clientPlayer != null && clientPlayer.getUUID().equals(msg.targetPlayerUUID)) {
//                LOGGER.debug("Showing toast notification for player {}", clientPlayer.getName().getString());

                SystemToast.add(
                        minecraft.getToasts(),
                        SystemToast.SystemToastIds.TUTORIAL_HINT,
                        Component.literal("Service Bell"),
                        Component.literal(msg.interactorName + " rang your service bell!")
                );
            }
        });
        ctx.get().setPacketHandled(true);
    }
} 