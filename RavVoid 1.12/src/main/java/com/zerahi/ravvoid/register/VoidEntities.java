package com.zerahi.ravvoid.register;

import java.util.ArrayList;

import com.zerahi.ravvoid.entity.mob.EntityDevourer;
import com.zerahi.ravvoid.entity.mob.EntityForgedChaos;
import com.zerahi.ravvoid.entity.mob.EntityShade;
import com.zerahi.ravvoid.entity.mob.EntityVoidBeast;
import com.zerahi.ravvoid.entity.mob.render.RenderDevourer;
import com.zerahi.ravvoid.entity.mob.render.RenderForgedChaos;
import com.zerahi.ravvoid.entity.mob.render.RenderShade;
import com.zerahi.ravvoid.entity.mob.render.RenderVoidBeast;
import com.zerahi.ravvoid.objects.VoidEntity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class VoidEntities {
	public static ArrayList<VoidEntity> Mobs = new ArrayList<VoidEntity>();
	
	public static void registerEntities() {
		
		int index = 0;
		Mobs.add(new VoidEntity("voidbeast", EntityVoidBeast.class, RenderVoidBeast.class, index, 1966634, 4456962)); index++;
		Mobs.add(new VoidEntity("shade", EntityShade.class, RenderShade.class, index, 460551, 4456962)); index++;
		Mobs.add(new VoidEntity("forgedchaos", EntityForgedChaos.class, RenderForgedChaos.class, index, 10682368, 524288)); index++;
		Mobs.add(new VoidEntity("devourer", EntityDevourer.class, RenderDevourer.class, index, 2171169, 131072)); index++;
	}

	public static VoidEntity MobByName(String name) {
		for (VoidEntity v : Mobs) {			
			if (v.Name == name) return v; 
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void RegisterRenders() {
			for (VoidEntity v : Mobs) {
				RenderingRegistry.registerEntityRenderingHandler(v.MobClass, new IRenderFactory() 
				{
					@Override
					public Render createRenderFor(RenderManager manager) 
					{
						return v.GetRenderer(manager);
					}
				});
			}
	}
}
