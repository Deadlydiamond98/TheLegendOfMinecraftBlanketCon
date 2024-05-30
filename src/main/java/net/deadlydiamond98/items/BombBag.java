package net.deadlydiamond98.items;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.BombEntity;
import net.deadlydiamond98.items.custombundle.CustomBundle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class BombBag extends CustomBundle {

    public BombBag(Settings settings, int maxStorage, List<Item> itemInsertable) {
        super(settings, maxStorage, itemInsertable);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        Optional<ItemStack> bomb = this.removeOneItem(user.getStackInHand(hand), ZeldaItems.Bomb);
        if (bomb.isPresent()) {
            BombEntity bombEntity = new BombEntity(world, user.getX(), user.getY() + user.getEyeHeight(user.getPose()), user.getZ(),3, 50);
            Vec3d vec3d = user.getRotationVec(1.0F);
            bombEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z);
            world.spawnEntity(bombEntity);
            user.getItemCooldownManager().set(this, 20);
            user.getItemCooldownManager().set(ZeldaItems.Bomb, 20);
            user.playSound(SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 1, 1);
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        else {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
    }
}