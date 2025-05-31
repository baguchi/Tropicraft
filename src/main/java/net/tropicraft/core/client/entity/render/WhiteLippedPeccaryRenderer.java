package net.tropicraft.core.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.Tropicraft;
import net.tropicraft.core.client.TropicraftRenderLayers;
import net.tropicraft.core.client.entity.model.WhiteLippedPeccaryModel;
import net.tropicraft.core.common.entity.passive.WhiteLippedPeccaryEntity;

public class WhiteLippedPeccaryRenderer extends MobRenderer<WhiteLippedPeccaryEntity, LivingEntityRenderState, WhiteLippedPeccaryModel> {
    private static final ResourceLocation TEXTURE = Tropicraft.location("textures/entity/white_lipped_peccary.png");

    public WhiteLippedPeccaryRenderer(EntityRendererProvider.Context context) {
        super(context, new WhiteLippedPeccaryModel(context.bakeLayer(TropicraftRenderLayers.WHITE_LIPPED_PECCARY_LAYER)), 0.5f);
    }

    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    public ResourceLocation getTextureLocation(LivingEntityRenderState entity) {
        return TEXTURE;
    }
}
