package com.zerahi.ravvoid.register;

import com.zerahi.ravvoid.entity.mob.EntityDevourer;
import com.zerahi.ravvoid.entity.mob.EntityForgedChaos;
import com.zerahi.ravvoid.entity.mob.EntityShade;
import com.zerahi.ravvoid.entity.mob.EntityVoidBeast;
import com.zerahi.ravvoid.entity.mob.render.RenderDevourer;
import com.zerahi.ravvoid.entity.mob.render.RenderForgedChaos;
import com.zerahi.ravvoid.entity.mob.render.RenderShade;
import com.zerahi.ravvoid.entity.mob.render.RenderVoidBeast;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RegisterEntityRenders {
	public static void registerBeast(Class<? extends EntityVoidBeast> voidbeast)
	{
		RenderingRegistry.registerEntityRenderingHandler(voidbeast, new IRenderFactory<EntityVoidBeast>() 
		{
			@Override
			public Render<? super EntityVoidBeast> createRenderFor(RenderManager manager) 
			{
				return new RenderVoidBeast(manager);
			}
		});
	}
	public static void registerShade(Class<? extends EntityShade> shade)
	{
		RenderingRegistry.registerEntityRenderingHandler(shade, new IRenderFactory<EntityShade>() 
		{
			@Override
			public Render<? super EntityShade> createRenderFor(RenderManager manager) 
			{
				return new RenderShade(manager);
			}
		});
	}
	public static void registerForged(Class<? extends EntityForgedChaos> forgedchaos)
	{
		RenderingRegistry.registerEntityRenderingHandler(forgedchaos, new IRenderFactory<EntityForgedChaos>() 
		{
			@Override
			public Render<? super EntityForgedChaos> createRenderFor(RenderManager manager) 
			{
				return new RenderForgedChaos(manager);
			}
		});
	}
	public static void registerDevourer(Class<? extends EntityDevourer> devourer)
	{
		RenderingRegistry.registerEntityRenderingHandler(devourer, new IRenderFactory<EntityDevourer>() 
		{
			@Override
			public Render<? super EntityDevourer> createRenderFor(RenderManager manager) 
			{
				return new RenderDevourer(manager);
			}
		});
	}
}
