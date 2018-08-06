package com.zerahi.ravvoid.register;

import com.zerahi.ravvoid.dimension.VoidBiome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.biome.BiomeProvider;

public class VoidBiomeRegistery extends BiomeProvider {

	public static int VOIDBIOMEID = 210;
	public static Biome VOIDBIOME = new VoidBiome(new BiomeProperties("voidbiome").setBaseHeight(0.125F).setHeightVariation(0.05F).setRainDisabled().setWaterColor(6710886).setTemperature(0.8F));
		
//		private static void registerBiomeWithTypes(Biome biome, int weight, BiomeType btype, Type...types){
//			BiomeDictionary.addTypes(biome, types);
//			BiomeManager.addBiome(btype, new BiomeEntry(biome, weight));
//		}
	}
