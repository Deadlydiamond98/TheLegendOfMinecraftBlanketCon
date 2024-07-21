package net.deadlydiamond98.items.custom.custombundle;

import net.deadlydiamond98.entities.bombs.BombEntity;
import net.deadlydiamond98.entities.bombs.BombchuEntity;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.items.custom.bomb.BombItem;
import net.deadlydiamond98.items.custom.bomb.BombchuItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class SeedBulletBag extends CustomBundle {

    public SeedBulletBag(Settings settings, int maxStorage, List<TagKey> itemTags) {
        super(settings, maxStorage, itemTags);
    }
}