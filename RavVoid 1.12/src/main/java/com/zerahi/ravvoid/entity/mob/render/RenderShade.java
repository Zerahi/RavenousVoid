package com.zerahi.ravvoid.entity.mob.render;

	import com.zerahi.ravvoid.entity.mob.EntityShade;
import com.zerahi.ravvoid.entity.mob.models.ModelShade;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShade extends RenderLiving<EntityShade> {
	
    private static final ResourceLocation SHADETEXTURE = new ResourceLocation("rv:textures/entity/shade.png");

    
    
    public RenderShade(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelShade(), 0F);
    }
    
	@Override
	protected ResourceLocation getEntityTexture(EntityShade entity) {
		return SHADETEXTURE;
	}
}