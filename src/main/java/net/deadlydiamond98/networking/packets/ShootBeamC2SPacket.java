package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.entities.projectiles.MasterSwordBeamEntity;
import net.deadlydiamond98.entities.projectiles.SwordBeamEntity;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.custom.Swords.MagicSword;
import net.deadlydiamond98.items.custom.Swords.MasterSword;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

public class ShootBeamC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            ServerWorld world = (ServerWorld) player.getWorld();
            Item item = player.getMainHandStack().getItem();
            if (((player.getHealth() == player.getMaxHealth()) || ManaHandler.CanRemoveManaFromPlayer(player, 1) || player.isCreative())
                    && !(player.getItemCooldownManager().isCoolingDown(item))
                    && player.handSwinging) {
                if (item instanceof MagicSword) {
                    SwordBeamEntity projectile = new SwordBeamEntity(ZeldaEntities.Sword_Beam, world);
                    projectile.setOwner(player);
                    projectile.setPosition(
                            player.getX() + player.getHandPosOffset(item).x,
                            player.getY() + player.getEyeHeight(player.getPose()),
                            player.getZ() + player.getHandPosOffset(item).z);
                    Vec3d vec3d = player.getRotationVec(1.0F);
                    projectile.setVelocity(vec3d.x, vec3d.y, vec3d.z, 0.75F, 0.1F);
                    world.spawnEntity(projectile);

                    player.getItemCooldownManager().set(item, 20);
                    ((MagicSword) item).setSoundPlay(true);
                    player.playSound(ZeldaSounds.SwordShoot, SoundCategory.PLAYERS, 1, 1);
                    if (!(player.getHealth() == player.getMaxHealth())) {
                        ManaHandler.removeManaFromPlayer(player, 1);
                    }
                }
                else if (item instanceof MasterSword) {
                    MasterSwordBeamEntity master_projectile = new MasterSwordBeamEntity(ZeldaEntities.Master_Sword_Beam, world);
                    master_projectile.setOwner(player);
                    master_projectile.setPosition(player.getX(), player.getY() + player.getEyeHeight(player.getPose()) -0.5, player.getZ());
                    Vec3d vec3d = player.getRotationVec(1.0F);
                    master_projectile.setVelocity(vec3d.x, vec3d.y, vec3d.z, 0.9F, 0.1F);
                    world.spawnEntity(master_projectile);

                    player.getItemCooldownManager().set(item, 20);
                    ((MasterSword) item).setSoundPlay(true);
                    player.playSound(ZeldaSounds.SwordShoot, SoundCategory.PLAYERS, 1, 1);
                    if (!(player.getHealth() == player.getMaxHealth())) {
                        ManaHandler.removeManaFromPlayer(player, 1);
                    }
                }
            }
            else if ((item instanceof MasterSword || item instanceof MagicSword) && !(player.getItemCooldownManager().isCoolingDown(item))
                    && player.handSwinging && player.handSwingTicks == 0) {
                player.getWorld().playSound(null, player.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        });
    }
}
