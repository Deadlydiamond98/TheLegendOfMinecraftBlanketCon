package net.deadlydiamond98.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.deadlydiamond98.blocks.loot.LootSkullBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.item.BlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HeadFeatureRenderer.class)
public class HeadFeatureRendererMixin {

    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/BlockItem;getBlock()Lnet/minecraft/block/Block;",
                    ordinal = 0
            )
    )
    private Block renderDifferentForLootSkull(BlockItem instance, Operation<Block> original) {
        if (instance.getBlock() instanceof LootSkullBlock) {
            return Blocks.ANDESITE;
        }

        return original.call(instance);
    }
}
