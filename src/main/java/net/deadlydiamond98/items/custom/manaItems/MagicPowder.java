package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.monsters.BubbleEntity;
import net.deadlydiamond98.entities.monsters.FairyEntity;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.block.CoralParentBlock.WATERLOGGED;

public class MagicPowder extends Item implements MagicItem {

    public static final Map<Block, Block> blockConversionMap = new HashMap<>();

    static {
        blockConversionMap.put(Blocks.GRASS, ZeldaBlocks.Loot_Grass);
        blockConversionMap.put(Blocks.GRASS_BLOCK, Blocks.MYCELIUM);

        blockConversionMap.put(Blocks.POWDER_SNOW, Blocks.SNOW);

        blockConversionMap.put(Blocks.HONEY_BLOCK, Blocks.SLIME_BLOCK);
        blockConversionMap.put(Blocks.SLIME_BLOCK, Blocks.HONEY_BLOCK);

        blockConversionMap.put(Blocks.NETHER_GOLD_ORE, Blocks.GILDED_BLACKSTONE);

        blockConversionMap.put(Blocks.NETHERRACK, Blocks.COBBLESTONE);
        blockConversionMap.put(Blocks.CRYING_OBSIDIAN, Blocks.OBSIDIAN);
        blockConversionMap.put(Blocks.WARPED_WART_BLOCK, Blocks.NETHER_WART_BLOCK);

        blockConversionMap.put(Blocks.SCULK, Blocks.MOSS_BLOCK);
        blockConversionMap.put(Blocks.SCULK_CATALYST, Blocks.SCULK);
        blockConversionMap.put(Blocks.SCULK_SHRIEKER, Blocks.SCULK);
        blockConversionMap.put(Blocks.SCULK_VEIN, Blocks.GLOW_LICHEN);

        blockConversionMap.put(Blocks.SOUL_SAND, Blocks.SAND);
        blockConversionMap.put(Blocks.SOUL_SOIL, Blocks.DIRT);
        blockConversionMap.put(Blocks.SOUL_CAMPFIRE, Blocks.CAMPFIRE);
        blockConversionMap.put(Blocks.SOUL_LANTERN, Blocks.LANTERN);
        blockConversionMap.put(Blocks.SOUL_TORCH, Blocks.TORCH);
        blockConversionMap.put(Blocks.SOUL_WALL_TORCH, Blocks.WALL_TORCH);

        blockConversionMap.put(Blocks.DEAD_BRAIN_CORAL, Blocks.BRAIN_CORAL);
        blockConversionMap.put(Blocks.DEAD_BRAIN_CORAL_BLOCK, Blocks.BRAIN_CORAL_BLOCK);
        blockConversionMap.put(Blocks.DEAD_BRAIN_CORAL_FAN, Blocks.BRAIN_CORAL_FAN);
        blockConversionMap.put(Blocks.DEAD_BRAIN_CORAL_WALL_FAN, Blocks.BRAIN_CORAL_WALL_FAN);

        blockConversionMap.put(Blocks.DEAD_BUBBLE_CORAL, Blocks.BUBBLE_CORAL);
        blockConversionMap.put(Blocks.DEAD_BUBBLE_CORAL_BLOCK, Blocks.BUBBLE_CORAL_BLOCK);
        blockConversionMap.put(Blocks.DEAD_BUBBLE_CORAL_FAN, Blocks.BUBBLE_CORAL_FAN);
        blockConversionMap.put(Blocks.DEAD_BUBBLE_CORAL_WALL_FAN, Blocks.BUBBLE_CORAL_WALL_FAN);

        blockConversionMap.put(Blocks.DEAD_FIRE_CORAL, Blocks.FIRE_CORAL);
        blockConversionMap.put(Blocks.DEAD_FIRE_CORAL_BLOCK, Blocks.FIRE_CORAL_BLOCK);
        blockConversionMap.put(Blocks.DEAD_FIRE_CORAL_FAN, Blocks.FIRE_CORAL_FAN);
        blockConversionMap.put(Blocks.DEAD_FIRE_CORAL_WALL_FAN, Blocks.FIRE_CORAL_WALL_FAN);

        blockConversionMap.put(Blocks.DEAD_HORN_CORAL, Blocks.HORN_CORAL);
        blockConversionMap.put(Blocks.DEAD_HORN_CORAL_BLOCK, Blocks.HORN_CORAL_BLOCK);
        blockConversionMap.put(Blocks.DEAD_HORN_CORAL_FAN, Blocks.HORN_CORAL_FAN);
        blockConversionMap.put(Blocks.DEAD_HORN_CORAL_WALL_FAN, Blocks.HORN_CORAL_WALL_FAN);

        blockConversionMap.put(Blocks.DEAD_TUBE_CORAL, Blocks.TUBE_CORAL);
        blockConversionMap.put(Blocks.DEAD_TUBE_CORAL_BLOCK, Blocks.TUBE_CORAL_BLOCK);
        blockConversionMap.put(Blocks.DEAD_TUBE_CORAL_FAN, Blocks.TUBE_CORAL_FAN);
        blockConversionMap.put(Blocks.DEAD_TUBE_CORAL_WALL_FAN, Blocks.TUBE_CORAL_WALL_FAN);
    }

    public MagicPowder(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (ManaHandler.CanRemoveManaFromPlayer(user, 10)) {
            if (entity instanceof BubbleEntity) {
                return convertEntity(ZeldaEntities.Fairy_Entity, entity, user, stack);
            }
            else if (entity instanceof WitherSkeletonEntity) {
                return convertEntity(EntityType.SKELETON, entity, user, stack);
            }
            else if (entity instanceof ZombifiedPiglinEntity) {
                return convertEntity(EntityType.ZOMBIFIED_PIGLIN, entity, user, stack);
            }
            else if (entity instanceof ZoglinEntity) {
                return convertEntity(EntityType.ZOGLIN, entity, user, stack);
            }
            else if (entity instanceof WitchEntity) {
                return convertEntity(EntityType.VILLAGER, entity, user, stack);
            }
            else if (!(entity instanceof SlimeEntity) && !(entity instanceof PlayerEntity)) {
                return convertEntity(EntityType.SLIME, entity, user, stack);
            }
        }
        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
        return super.useOnEntity(stack, user, entity, hand);
    }

    private <T extends Entity> ActionResult convertEntity(EntityType<T> converted, Entity entity, PlayerEntity user, ItemStack stack) {
        World world = user.getWorld();

        Entity newEntity = converted.create(world);
        if (newEntity == null) {
            return ActionResult.FAIL;
        }

        newEntity.setPos(entity.getX(), entity.getY(), entity.getZ());

        if (newEntity instanceof SlimeEntity slime) {
            Box boundingBox = entity.getBoundingBox();

            double width = boundingBox.getXLength();
            double height = boundingBox.getYLength();
            double depth = boundingBox.getZLength();
            double volume = width * height * depth;
            volume = volume == 0 ? 1 : volume;
            slime.setSize((int) volume, false);
        }

        entity.discard();
        world.spawnEntity(newEntity);

        ManaHandler.removeManaFromPlayer(user, 10);
        stack.decrement(1);
        user.getItemCooldownManager().set(this, 20);

        world.playSound(null, newEntity.getBlockPos(), ZeldaSounds.Transform,
                SoundCategory.PLAYERS, 1.0f, 1.0f);

        addParticles(user, entity.getX(), entity.getY(), entity.getZ());

        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockPos particlePos = pos.offset(context.getSide(), 1);
        BlockState state = world.getBlockState(pos);

        if (ManaHandler.CanRemoveManaFromPlayer(context.getPlayer(), 10)) {
            Block replacementBlock = blockConversionMap.get(state.getBlock());
            if (replacementBlock != null) {
                BlockState newState = replacementBlock.getDefaultState();

                for (Property<?> property : state.getProperties()) {
                    if (newState.contains(property)) {
                        newState = copyProperty(state, newState, property);
                    }
                }

                world.setBlockState(pos, newState);

            }

            Box box = new Box(particlePos);
            List<Entity> entities = world.getEntitiesByClass(Entity.class, box, entity -> entity instanceof ItemEntity);

            for (Entity entity : entities) {
                ItemEntity itemEntity = (ItemEntity) entity;
                if (itemEntity.getStack().isOf(Items.ARROW)) {
                    ItemStack item = ZeldaItems.Silver_Arrow.getDefaultStack();
                    item.setCount(itemEntity.getStack().getCount());
                    itemEntity.setStack(item);
                }
            }

            world.playSound(null, pos, ZeldaSounds.Transform,
                    SoundCategory.PLAYERS, 1.0f, 1.0f);
            addParticles(context.getPlayer(), particlePos.getX(), particlePos.getY(), particlePos.getZ());
            ManaHandler.removeManaFromPlayer(context.getPlayer(), 10);
            context.getStack().decrement(1);
            context.getPlayer().getItemCooldownManager().set(this, 20);
            return ActionResult.SUCCESS;
        }
        world.playSound(null, context.getPlayer().getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
        return super.useOnBlock(context);
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState fromState, BlockState toState, Property<T> property) {
        return toState.with(property, fromState.get(property));
    }

    private void addParticles(PlayerEntity user, double x, double y, double z) {
        for (int i = 0; i < 50; i++) {
            double randx = x + (user.getRandom().nextBetween(-16, 16) * (1.0/16.0));
            double randy = y + (user.getRandom().nextBetween(-16, 16) * (1.0/16.0));
            double randz = z + (user.getRandom().nextBetween(-16, 16) * (1.0/16.0));
            user.getWorld().addParticle(ParticleTypes.TOTEM_OF_UNDYING, randx, randy, randz, 0, 0, 0);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.zeldacraft.magic_powder.tooltipa".formatted(Formatting.GREEN)));
        tooltip.add(Text.translatable("item.zeldacraft.magic_powder.tooltipb".formatted(Formatting.GREEN)));
    }

    @Override
    public int getManaCost() {
        return 10;
    }
}
