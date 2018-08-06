package com.zerahi.ravvoid.register;

import com.zerahi.ravvoid.Ref;
import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.entity.mob.EntityDevourer;
import com.zerahi.ravvoid.entity.mob.EntityForgedChaos;
import com.zerahi.ravvoid.entity.mob.EntityShade;
import com.zerahi.ravvoid.entity.mob.EntityVoidBeast;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class VoidEntities {
	
	public static void registerEntities() {
		registerEntity("voidbeast",EntityVoidBeast.class, 1, 64, 1966634, 4456962);
		registerEntity("shade", EntityShade.class, 2, 64, 460551, 4456962);
		registerEntity("forgedchaos", EntityForgedChaos.class, 3, 64, 10682368, 524288);
		registerEntity("devourer", EntityDevourer.class, 4, 64, 2171169, 131072);
	}

	private static void registerEntity(String entityName, Class<? extends Entity>entity, int id, int trackingRange, int solidColor, int spotColor) {
				EntityRegistry.registerModEntity(new ResourceLocation(Ref.MODID + ":" + entityName), entity, entityName, id,
				VoidMod.instance, trackingRange, 1, true, solidColor, spotColor);
	}
	
	public static Entity spawnList(int num, World world) {
		if (num == 0) return new EntityVoidBeast(world);
		else if (num == 1) return new EntityShade(world);
		else if (num == 2) return new EntityForgedChaos(world);
		else if (num == 3) return new EntityDevourer(world);
		else return null;
	}

	@SuppressWarnings("rawtypes")
	public static Class getList(int num) {
		if (num == 0) return EntityVoidBeast.class;
		else if (num == 1) return EntityShade.class;
		else if (num == 2) return EntityForgedChaos.class;
		else if (num == 3) return EntityDevourer.class;
		else return null;
	}
}
