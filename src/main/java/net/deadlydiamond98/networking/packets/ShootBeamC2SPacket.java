package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.entities.SwordBeamEntity;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.Swords.MagicSword;
import net.deadlydiamond98.sounds.ZeldaSounds;
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
            if (((player.getHealth() == player.getMaxHealth()) || player.isCreative())
                    && !(player.getItemCooldownManager().isCoolingDown(item))
                    && player.handSwinging) {
                if (item instanceof MagicSword) {
                    SwordBeamEntity projectile = new SwordBeamEntity(ZeldaEntities.Sword_Beam, world);
                    projectile.setOwner(player);
                    projectile.setPosition(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ());
                    Vec3d vec3d = player.getRotationVec(1.0F);
                    projectile.setVelocity(vec3d.x, vec3d.y, vec3d.z, 0.75F, 0.0F);
                    world.spawnEntity(projectile);

                    player.getItemCooldownManager().set(item, 20);
                    ((MagicSword) item).setSoundPlay(true);
                    player.playSound(ZeldaSounds.SwordShoot, SoundCategory.PLAYERS, 1, 1);
                }
            }
        });
    }
}
