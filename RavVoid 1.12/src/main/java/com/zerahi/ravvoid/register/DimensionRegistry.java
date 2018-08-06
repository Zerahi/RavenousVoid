package com.zerahi.ravvoid.register;

import com.zerahi.ravvoid.dimension.WorldProviderVoid;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	
	public static final int dimensionId = 16;
	public static DimensionType VOID;
	
	public static void mainRegistry() {
		
		VOID = DimensionType.register("the_void", "_void", dimensionId, WorldProviderVoid.class, false);
		DimensionManager.registerDimension(dimensionId, VOID);
	}

}
