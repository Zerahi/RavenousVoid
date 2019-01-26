package com.zerahi.ravvoid.objects;

import java.lang.reflect.InvocationTargetException;

import com.zerahi.ravvoid.Ref;
import com.zerahi.ravvoid.VoidMod;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@SuppressWarnings("rawtypes")
public class VoidEntity {
	public ResourceLocation LootTable;
	public String Name;
	public Class MobClass;
	public Class RenderClass;
	public int ID;
	public int EggSolid;
	public int EggSpot;
	
	
	public VoidEntity(String name, Class mobClass, Class renderClass, int id, int eggSolid, int eggSpot) {
		this.Name = name;
		this.MobClass = mobClass;
		this.ID = id;
		this.EggSolid = eggSolid;
		this.EggSpot = eggSpot;
		this.RenderClass = renderClass;
		this.Register();
	}

	@SuppressWarnings("unchecked")
	public void Register() {
		EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID + ":" + Name), MobClass, Name, ID,
				VoidMod.instance, 64, 1, true, EggSolid, EggSpot);

		this.LootTable = LootTableList.register(new ResourceLocation(Ref.MODID, Name));
	}

	@SuppressWarnings("unchecked")
	public Entity NewMob(World world) {
		// TODO Auto-generated method stub
		try {
			return (Entity) MobClass.getConstructor(World.class).newInstance(world);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Render<? extends Entity> GetRenderer(RenderManager renderManager) {
		try {
			return  (Render)RenderClass.getConstructor(RenderManager.class).newInstance(renderManager);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			@SuppressWarnings("unused")
			Exception t = e;
		}
		return null;
	}
}
 