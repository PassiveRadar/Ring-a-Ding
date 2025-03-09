package com.radar.ringading.block.entity;

import com.radar.ringading.block.RedstoneServiceBellBlock;
import com.radar.ringading.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RedstoneServiceBellBlockEntity extends ServiceBellBlockEntity {
    private int powerOffTimer = 0;
    private boolean needsPowerUpdate = false;

    public RedstoneServiceBellBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REDSTONE_SERVICE_BELL.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, RedstoneServiceBellBlockEntity blockEntity) {
        ServiceBellBlockEntity.tick(level, pos, state, blockEntity);

        if (blockEntity.needsPowerUpdate && !level.isClientSide) {
            if (blockEntity.powerOffTimer > 0) {
                blockEntity.powerOffTimer--;
            } else if (state.getValue(RedstoneServiceBellBlock.POWERED)) {
                level.setBlock(pos, state.setValue(RedstoneServiceBellBlock.POWERED, false), Block.UPDATE_ALL);
                blockEntity.needsPowerUpdate = false;
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putInt("PowerOffTimer", powerOffTimer);
        tag.putBoolean("NeedsPowerUpdate", needsPowerUpdate);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        if (tag.contains("PowerOffTimer")) {
            powerOffTimer = tag.getInt("PowerOffTimer");
        }
        if (tag.contains("NeedsPowerUpdate")) {
            needsPowerUpdate = tag.getBoolean("NeedsPowerUpdate");
        }
    }

    /**
     * Schedules the bell to turn off its redstone signal after the specified delay
     *
     * @param ticks Number of ticks before turning off the signal
     */
    public void schedulePowerOff(int ticks) {
        this.powerOffTimer = ticks;
        this.needsPowerUpdate = true;
    }
}
