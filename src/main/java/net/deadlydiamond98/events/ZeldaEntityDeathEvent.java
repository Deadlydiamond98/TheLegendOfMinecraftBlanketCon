package net.deadlydiamond98.events;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.util.ZeldaConfig;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Random;

public class ZeldaEntityDeathEvent implements ServerLivingEntityEvents.AfterDeath {
    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        World world = entity.getEntityWorld();
        if (!world.isClient) {
            if (damageSource.getAttacker() instanceof PlayerEntity) {
                if (!(entity instanceof PlayerEntity) && entity instanceof LivingEntity && ZeldaConfig.mobsDropShards) {
                    Random random = new Random();
                    int randomNumber = random.nextInt(0, 100);

                    if (randomNumber > 75) {
                        world.spawnEntity(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), 
                                ZeldaItems.Emerald_Shard.getDefaultStack()));
                    }
                }
            }
        }
    }
}
