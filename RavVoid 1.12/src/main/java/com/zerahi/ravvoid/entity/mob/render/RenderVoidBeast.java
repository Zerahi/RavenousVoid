package com.zerahi.ravvoid.entity.mob.render;

	import com.zerahi.ravvoid.entity.mob.EntityVoidBeast;
import com.zerahi.ravvoid.entity.mob.models.ModelVoidBeast;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVoidBeast extends RenderLiving<EntityVoidBeast> {
	
    private static final ResourceLocation VOIDBEASTTEXTURE = new ResourceLocation("rv:textures/entity/voidbeast.png");

    public RenderVoidBeast(RenderManager manager)
    {
        super(manager, new ModelVoidBeast(), 0.6F);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityVoidBeast entity) {
		return VOIDBEASTTEXTURE;
	}
}