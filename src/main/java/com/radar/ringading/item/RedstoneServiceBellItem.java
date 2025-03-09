package com.radar.ringading.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class RedstoneServiceBellItem extends BlockItem {

    public RedstoneServiceBellItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.ringading.redstone_service_bell.tooltip.1")
                .withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
        tooltip.add(Component.translatable("item.ringading.service_bell.tooltip.1")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.ringading.service_bell.tooltip.2")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

        super.appendHoverText(stack, level, tooltip, flag);
    }
} 