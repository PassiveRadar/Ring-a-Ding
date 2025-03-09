package com.radar.ringading;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Ringading.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    
    private static final ForgeConfigSpec.BooleanValue PLAY_SOUND_ON_INTERACTION =
            BUILDER.comment("Whether to play a sound when someone interacts with a service bell")
                    .define("playSoundOnInteraction", true);
    private static final ForgeConfigSpec.IntValue NOTIFICATION_DISPLAY_TIME =
            BUILDER.comment("How long toast notifications should display (in ticks)")
                    .defineInRange("notificationDisplayTime", 60, 20, 200);
    private static final ForgeConfigSpec.IntValue MAX_TOAST_NOTIFICATIONS =
            BUILDER.comment("Maximum number of toast notifications to show before cooldown (prevents spam)")
                    .defineInRange("maxToastNotifications", 3, 0, 10);
    
    // Build the config spec after all values have been defined
    static final ForgeConfigSpec SPEC = BUILDER.build();
    
    public static boolean playSoundOnInteraction;
    public static int notificationDisplayTime;
    public static int maxToastNotifications;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        playSoundOnInteraction = PLAY_SOUND_ON_INTERACTION.get();
        notificationDisplayTime = NOTIFICATION_DISPLAY_TIME.get();
        maxToastNotifications = MAX_TOAST_NOTIFICATIONS.get();
    }
}
