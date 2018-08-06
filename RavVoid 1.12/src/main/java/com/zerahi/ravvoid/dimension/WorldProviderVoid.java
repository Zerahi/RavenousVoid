package com.zerahi.ravvoid.dimension;

import com.zerahi.ravvoid.register.DimensionRegistry;
import com.zerahi.ravvoid.register.VoidBiomeRegistery;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class WorldProviderVoid extends WorldProvider {

	@Override
	public void init() {
		this.biomeProvider = new BiomeProviderSingle(VoidBiomeRegistery.VOIDBIOME);
		this.hasSkyLight = false;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new VoidChunkGenerator(world, world.getSeed(), true);
	}

	// may try later
//	@Override
//	protected void generateLightBrightnessTable() {
//		float f = 0.25F;
//
//		for (int i = 0; i <= 15; ++i) {
//			float f1 = 1.0F - i / 15.0F;
//			lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
//		}
//	}
	
	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.VOID;
	}
	
	@Override
	public boolean isSurfaceWorld() {
		
		return false;
	}
	
	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}
	
	/**
	 * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
	 */
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		return 0.0F;
	}
	
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
	{
		return null;
	}
	
	@Override
	public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
		return new Vec3d(0, 0, 0);
	}
	
	@Override
	public boolean canCoordinateBeSpawn(int x, int z) {
		
		return false;
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setSkyRenderer(IRenderHandler skyRenderer) {
		super.setSkyRenderer(new VoidSkyRenderer());
	}
	
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 128.0F;
    }

    public int getAverageGroundLevel()
    {
        return 70;
    }
	    

	@Override
	public String getSaveFolder() {
		return "Void";
	}
}
