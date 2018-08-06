package com.zerahi.ravvoid.entity.mob.render;

import com.zerahi.ravvoid.entity.mob.EntityForgedChaos;
import com.zerahi.ravvoid.entity.mob.models.ModelForgedChaos;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderForgedChaos extends RenderLiving<EntityForgedChaos> {
	
    private static final ResourceLocation FORGEDCHAOS = new ResourceLocation("rv:textures/entity/forgedchaos.png");

    public RenderForgedChaos(RenderManager manager)
    {
        super(manager, new ModelForgedChaos(), 1F);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityForgedChaos entity) {
		return FORGEDCHAOS;
	}
}