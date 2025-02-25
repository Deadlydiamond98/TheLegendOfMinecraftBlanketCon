package net.deadlydiamond98.items.items.manaitems.magicpower;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.magiclib.items.consumers.TransformationItem;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicPowder extends TransformationItem {

    private BlockPos particlePos;

    public MagicPowder(Settings settings, int manaCost, boolean consumed, int cooldown, boolean hasDefault) {
        super(settings, manaCost, consumed, cooldown, hasDefault, EntityType.SLIME, 30);
        this.particlePos = new BlockPos(0, 0,0);
    }

    @Override
    protected void initializeBlockConversions() {
        super.initializeBlockConversions();

//        blockConversionMap.put(Blocks.CRAFTING_TABLE, ZeldaBlocks.Magic_Workbench);

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

    @Override
    protected void initializeEntityConversions() {
        super.initializeEntityConversions();
        entityConversionMap.put(ZeldaEntities.Bubble_Entity, ZeldaEntities.Fairy_Entity);
        entityConversionMap.put(EntityType.WITHER_SKELETON, EntityType.SKELETON);
        entityConversionMap.put(EntityType.ZOMBIFIED_PIGLIN, EntityType.PIGLIN);
        entityConversionMap.put(EntityType.ZOGLIN, EntityType.HOGLIN);
        entityConversionMap.put(EntityType.WITCH, EntityType.VILLAGER);
    }

    @Override
    protected void initializeItemConversions() {
        super.initializeItemConversions();
//        itemConversionMap.put(Items.ARROW, ZeldaItems.Silver_Arrow);
//        itemConversionMap.put(Items.IRON_SWORD, ZeldaItems.Magic_Sword);
//        itemConversionMap.put(ZeldaItems.Red_Ring, ZeldaItems.Blue_Ring);
    }

    @Override
    protected void initializeEntityBlacklist() {
        super.initializeEntityBlacklist();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        this.particlePos = context.getBlockPos().offset(context.getSide(), 1);
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        this.particlePos = entity.getBlockPos();
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    protected void consumeMana(PlayerEntity user, ItemStack stack, World world) {
        super.consumeMana(user, stack, world);
        world.playSound(null, user.getBlockPos(), ZeldaSounds.Transform,
                SoundCategory.PLAYERS, 1.0f, 1.0f);
        addParticles(user, this.particlePos.getX(), this.particlePos.getY(), this.particlePos.getZ());
    }

    @Override
    protected void doNoManaEvent(PlayerEntity user, World world) {
        super.doNoManaEvent(user, world);
        world.playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana,
                SoundCategory.PLAYERS, 1.0f, 1.0f);
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
        tooltip.add(Text.translatable("item.zeldacraft.magic_powder.tooltipa").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("item.zeldacraft.magic_powder.tooltipb").formatted(Formatting.GREEN));
    }
}
