package net.tropicraft.core.client.entity.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.Tropicraft;
import net.tropicraft.core.client.TropicraftRenderLayers;
import net.tropicraft.core.client.entity.model.WhiteCollaredOlivebackModel;
import net.tropicraft.core.client.entity.render.state.SmallBirdRenderState;
import net.tropicraft.core.common.entity.passive.SmallBirdEntity;

public class WhiteCollaredOlivebackRenderer extends MobRenderer<SmallBirdEntity, SmallBirdRenderState, WhiteCollaredOlivebackModel> {
    private static final ResourceLocation TEXTURE = Tropicraft.location("textures/entity/white_collared_oliveback.png");

    public WhiteCollaredOlivebackRenderer(EntityRendererProvider.Context context) {
        super(context, new WhiteCollaredOlivebackModel(context.bakeLayer(TropicraftRenderLayers.WHITE_COLLARED_OLIVEBACK_LAYER)), 0.15f);
    }

    public SmallBirdRenderState createRenderState() {
        return new SmallBirdRenderState();
    }
    @Override
    public ResourceLocation getTextureLocation(SmallBirdRenderState entity) {
        return TEXTURE;
    }
}
