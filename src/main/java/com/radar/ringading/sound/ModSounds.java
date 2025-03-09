package com.radar.ringading.sound;

import com.radar.ringading.Ringading;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Ringading.MODID);

    public static final RegistryObject<SoundEvent> BELL_RING =
            registerSoundEvent("bell_ring");
    public static final RegistryObject<SoundEvent> WEAK_BELL_RING =
            registerSoundEvent("weak_bell_ring");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(Ringading.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(resourceLocation));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
} 