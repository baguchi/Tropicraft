package net.tropicraft.core.client.entity.render;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.tropicraft.core.client.entity.render.state.SmallBirdRenderState;
import net.tropicraft.core.common.entity.passive.SmallBirdEntity;

public abstract class SmallBirdRenderer<M extends EntityModel<SmallBirdRenderState>> extends MobRenderer<SmallBirdEntity, SmallBirdRenderState, M> {
    public SmallBirdRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Override
    public SmallBirdRenderState createRenderState() {
        return new SmallBirdRenderState();
    }

    @Override
    public void extractRenderState(SmallBirdEntity entity, SmallBirdRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.flightAnimation = entity.getFlightAnimation(partialTicks);
    }
}
