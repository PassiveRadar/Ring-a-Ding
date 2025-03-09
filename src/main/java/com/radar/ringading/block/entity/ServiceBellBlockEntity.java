package com.radar.ringading.block.entity;

import com.radar.ringading.Config;
import com.radar.ringading.init.ModBlockEntities;
import com.radar.ringading.network.PacketHandler;
import com.radar.ringading.network.S2CBellRingPacket;
import com.radar.ringading.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class ServiceBellBlockEntity extends BlockEntity implements GeoBlockEntity {
    protected static final RawAnimation RING_ANIM = RawAnimation.begin().then("animation.bell.ring", Animation.LoopType.PLAY_ONCE);
    private static final long TOAST_RESET_COOLDOWN = 100; // 5 seconds
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    int toastCounter = 0;
    long lastToastResetTime = 0;
    private UUID ownerUUID = null;
    private String ownerName = "";

    public ServiceBellBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ServiceBellBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.SERVICE_BELL.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ServiceBellBlockEntity blockEntity) {
        if (blockEntity.toastCounter >= Config.maxToastNotifications) {
            long currentTime = level.getGameTime();
            if (currentTime - blockEntity.lastToastResetTime >= TOAST_RESET_COOLDOWN) {
                blockEntity.toastCounter = 0;
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            return PlayState.STOP;
        }).triggerableAnim("ring", RING_ANIM));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void setOwner(Player player) {
        this.ownerUUID = player.getUUID();
        this.ownerName = player.getName().getString();
        setChanged();
    }

    public void onInteract(Player player) {

        setChanged();

        if (!level.isClientSide) {

            if (ownerUUID != null) {

                if (level instanceof ServerLevel serverLevel) {
                    ServerPlayer ownerPlayer = serverLevel.getServer().getPlayerList().getPlayer(ownerUUID);
                    stopTriggeredAnimation("controller", "ring");
                    triggerAnim("controller", "ring");
                    if (ownerPlayer == null) {
                        level.playSound(null, getBlockPos(), ModSounds.WEAK_BELL_RING.get(), SoundSource.BLOCKS, 0.3F, 1.0F);
                    } else {
                        level.playSound(null, getBlockPos(), ModSounds.BELL_RING.get(), SoundSource.BLOCKS, 0.3F, 1.0F);
                        if (toastCounter < Config.maxToastNotifications) {
                            PacketHandler.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(() -> ownerPlayer),
                                    new S2CBellRingPacket(ownerUUID, player.getName().getString())
                            );
                            toastCounter++;

                            // SHTUP
                            if (toastCounter >= Config.maxToastNotifications) {
                                lastToastResetTime = level.getGameTime();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (ownerUUID != null) {
            tag.putUUID("OwnerUUID", ownerUUID);
            tag.putString("OwnerName", ownerName);
        }

        tag.putInt("ToastCounter", toastCounter);
        tag.putLong("LastToastResetTime", lastToastResetTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("OwnerUUID")) {
            ownerUUID = tag.getUUID("OwnerUUID");
            ownerName = tag.getString("OwnerName");
        }

        if (tag.contains("ToastCounter")) {
            toastCounter = tag.getInt("ToastCounter");
        }
        if (tag.contains("LastToastResetTime")) {
            lastToastResetTime = tag.getLong("LastToastResetTime");
        }
    }

} 