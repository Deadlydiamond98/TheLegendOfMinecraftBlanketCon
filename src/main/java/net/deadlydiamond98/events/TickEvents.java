package net.deadlydiamond98.events;

import net.deadlydiamond98.entities.SwordBeamEntity;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.MagicSword;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class TickEvents {
    public static void endTickEvent() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                World world = player.getWorld();

                // Attacking with Item Logic, for things like Sword Beams
                if ((player.handSwingTicks == 1) && player.getAttackCooldownProgress(0.5f) < 1) {
                    if (player.getMainHandStack().getItem() instanceof MagicSword) {
                        SwordBeamEntity projectile = new SwordBeamEntity(ZeldaEntities.Sword_Beam, world);
                        projectile.setOwner(player);
                        projectile.setPosition(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ());
                        Vec3d vec3d = player.getRotationVec(1.0F);
                        projectile.setVelocity(vec3d.x, vec3d.y, vec3d.z, 0.005F, 0.0F);
                        world.spawnEntity(projectile);
                    }
                }
            }
        });
    }

    public static void registerTickEvent() {
        endTickEvent();
    }
}
