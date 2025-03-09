package com.radar.ringading.client.renderer;

import com.radar.ringading.Ringading;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class ServiceBellModel<T extends BlockEntity & GeoAnimatable> extends GeoModel<T> {

    @Override
    public ResourceLocation getModelResource(T entity) {
        return new ResourceLocation(Ringading.MODID, "geo/service_bell.json");
    }

    @Override
    public ResourceLocation getTextureResource(T entity) {
        return new ResourceLocation(Ringading.MODID, "textures/block/service_bell.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T entity) {
        return new ResourceLocation(Ringading.MODID, "animations/service_bell.animation.json");
    }
}
