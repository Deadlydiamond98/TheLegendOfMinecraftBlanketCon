package net.deadlydiamond98.items.custom.custombundle;

import net.deadlydiamond98.entities.bombs.BombEntity;
import net.deadlydiamond98.entities.bombs.BombchuEntity;
import net.deadlydiamond98.items.custom.bomb.BombItem;
import net.deadlydiamond98.items.custom.bomb.BombchuItem;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BombBag extends CustomBundle {

    public BombBag(Settings settings, int maxStorage, List<TagKey> itemTags) {
        super(settings, maxStorage, itemTags);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!user.isSneaking()) {
            Optional<ItemStack> bombStack = this.getFirstItem(user.getStackInHand(hand));
            if (bombStack.isPresent()) {
                Optional<ItemStack> bomb = this.removeOneItem(user.getStackInHand(hand), bombStack.get().getItem());
                if (bomb.isPresent() && (bombStack.get().isOf(ZeldaItems.Bomb) || bombStack.get().isOf(ZeldaItems.Super_Bomb))) {
                    createBomb(world, user, bomb);
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
                else if (bomb.isPresent() && bombStack.get().isOf(ZeldaItems.Bombchu)) {
                    createBombchu(world, user, bomb);
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
                return TypedActionResult.fail(user.getStackInHand(hand));
            }
        }
        return super.use(world, user, hand);
    }

    private void createBombchu(World world, PlayerEntity user, Optional<ItemStack> bomb) {
        BombchuEntity bombEntity = new BombchuEntity(world, user.getX(), user.getY() + user.getEyeHeight(user.getPose()), user.getZ(),
                ((BombchuItem) bomb.get().getItem()).getPower(), ((BombchuItem) bomb.get().getItem()).getFuse(), ((BombchuItem) bomb.get().getItem()).getSpeed(),
                true);
        Vec3d vec3d = user.getRotationVec(1.0F);
        bombEntity.setYaw(user.headYaw);
        bombEntity.setVelocity(vec3d.x * ((BombchuItem) bomb.get().getItem()).getSpeed(), vec3d.y, vec3d.z * ((BombchuItem) bomb.get().getItem()).getSpeed());
        bombEntity.setOwner(user);
        world.spawnEntity(bombEntity);
        user.getItemCooldownManager().set(this, 40);
        user.getItemCooldownManager().set(ZeldaItems.Bomb, 20);
        user.getItemCooldownManager().set(ZeldaItems.Super_Bomb, 20);
        user.getItemCooldownManager().set(ZeldaItems.Bombchu, 20);

        user.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 1, 1);
    }

    private void createBomb(World world, PlayerEntity user, Optional<ItemStack> bomb) {
        BombEntity bombEntity = new BombEntity(world, user.getX(), user.getY() + user.getEyeHeight(user.getPose()), user.getZ(),
                ((BombItem) bomb.get().getItem()).getPower(), ((BombItem) bomb.get().getItem()).getFuse(),
                ((BombItem) bomb.get().getItem()).getType());
        Vec3d vec3d = user.getRotationVec(1.0F);
        bombEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z);
        bombEntity.setYaw(user.getHeadYaw());
        world.spawnEntity(bombEntity);
        user.getItemCooldownManager().set(this, 40);
        user.getItemCooldownManager().set(ZeldaItems.Bomb, 20);
        user.getItemCooldownManager().set(ZeldaItems.Super_Bomb, 20);
        user.getItemCooldownManager().set(ZeldaItems.Bombchu, 20);
        user.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 1, 1);
    }
}