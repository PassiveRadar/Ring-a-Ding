package com.radar.ringading.client.renderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ServiceBellRenderer<T extends BlockEntity & GeoAnimatable> extends GeoBlockRenderer<T> {
    public ServiceBellRenderer(BlockEntityRendererProvider.Context context) {
        super(new ServiceBellModel<>());
    }
}
