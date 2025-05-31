package net.tropicraft.core.client.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemClusterRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tropicraft.Tropicraft;
import net.tropicraft.core.client.TropicraftRenderLayers;
import net.tropicraft.core.client.entity.model.EIHMachineModel;
import net.tropicraft.core.common.block.TropicraftBlocks;
import net.tropicraft.core.common.block.tileentity.DrinkMixerBlockEntity;
import net.tropicraft.core.common.item.CocktailItem;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import javax.annotation.Nullable;
import java.util.List;

public class DrinkMixerRenderer extends MachineRenderer<DrinkMixerBlockEntity> {
    private static final ResourceLocation MUG_TEXTURE = Tropicraft.location("textures/block/te/bamboo_mug.png");
    private static final Material MATERIAL = new Material(TextureAtlas.LOCATION_BLOCKS, Tropicraft.location("textures/block/te/drink_mixer.png"));

    private final Model mugModel;
    private final Model mugLiquidModel;
    private final ItemModelResolver itemModelResolver;
    private final RandomSource random = RandomSource.create();
    private final ItemClusterRenderState clusterState = new ItemClusterRenderState();

    private static final Vector3fc[] INGREDIENT_OFFSETS = new Vector3fc[]{
            new Vector3f(0.3f, -0.5f, 0.05f),
            new Vector3f(-0.3f, -0.5f, 0.05f),
            new Vector3f(0.0f, 0.3f, -0.1f),
    };

    private static final Vector3fc[] INGREDIENT_SCALES = new Vector3fc[]{
            new Vector3f(1, 1, 1),
            new Vector3f(1, 1, 1),
            new Vector3f(0.8f, 0.8f, 0.8f),
    };

    public DrinkMixerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, TropicraftBlocks.DRINK_MIXER.get(), new EIHMachineModel(context.getModelSet().bakeLayer(TropicraftRenderLayers.EIHMACHINE_LAYER)));
        itemModelResolver = context.getItemModelResolver();
        mugModel = new Model.Simple(context.getModelSet().bakeLayer(TropicraftRenderLayers.BAMBOO_MUG), RenderType::entityCutout);
        mugLiquidModel = new Model.Simple(context.getModelSet().bakeLayer(TropicraftRenderLayers.BAMBOO_MUG_LIQUID), RenderType::entityCutout);
    }

    @Override
    protected Material getMaterial() {
        return MATERIAL;
    }

    @Override
    public void renderIngredients(DrinkMixerBlockEntity te, PoseStack stack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        if (!te.isDoneMixing()) {
            List<ItemStack> ingredients = te.getDrinkIngredients();
            for (int index = 0; index < ingredients.size(); index++) {
                ItemStack ingredient = ingredients.get(index);
                renderIngredient(te.getLevel(), stack, buffer, combinedOverlayIn, combinedLightIn, ingredient, index);
            }
        }

        if (te.isMixing() || !te.result.isEmpty()) {
            stack.pushPose();
            stack.translate(-0.2f, -0.25f, 0.0f);
            VertexConsumer consumer = buffer.getBuffer(mugModel.renderType(MUG_TEXTURE));
            mugModel.renderToBuffer(stack, consumer, combinedLightIn, combinedOverlayIn);
            if (te.isDoneMixing()) {
                int liquidColor = CocktailItem.getCocktail(te.result).color();
                mugLiquidModel.renderToBuffer(stack, consumer, combinedLightIn, combinedOverlayIn, ARGB.opaque(liquidColor));
            }
            stack.popPose();
        }
    }

    private void renderIngredient(@Nullable Level level, PoseStack stack, MultiBufferSource buffer, int combinedOverlayIn, int combinedLight, ItemStack ingredient, int ingredientIndex) {
        itemModelResolver.updateForTopItem(clusterState.item, ingredient, ItemDisplayContext.FIXED, level, null, 0);
        clusterState.count = ItemClusterRenderState.getRenderedAmount(ingredient.getCount());
        clusterState.seed = ItemClusterRenderState.getSeedForItemStack(ingredient);

        stack.pushPose();
        stack.mulPose(Axis.XP.rotationDegrees(90));
        stack.mulPose(Axis.YP.rotationDegrees(90));
        stack.mulPose(Axis.ZP.rotationDegrees(90));
        Vector3fc offsets = INGREDIENT_OFFSETS[ingredientIndex];
        Vector3fc scales = INGREDIENT_SCALES[ingredientIndex];
        stack.translate(offsets.x(), offsets.y(), offsets.z());
        stack.scale(scales.x(), scales.y(), scales.z());
        ItemEntityRenderer.renderMultipleFromCount(stack, buffer, combinedLight, clusterState, random);
        stack.popPose();
    }
}
