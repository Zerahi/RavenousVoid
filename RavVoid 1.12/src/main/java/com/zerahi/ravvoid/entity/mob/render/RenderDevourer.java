package com.zerahi.ravvoid.entity.mob.render;

import com.zerahi.ravvoid.entity.mob.EntityDevourer;
import com.zerahi.ravvoid.entity.mob.models.ModelDevourer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDevourer extends RenderLiving<EntityDevourer> {
	
    private static final ResourceLocation DEVOURER = new ResourceLocation("rv:textures/entity/devourer.png");

    public RenderDevourer(RenderManager manager)
    {
        super(manager, new ModelDevourer(), 1F);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityDevourer entity) {
		return DEVOURER;
	}
}