package com.zerahi.ravvoid.dimension;

import com.zerahi.ravvoid.Ref;
import com.zerahi.ravvoid.entity.mob.EntityShade;
import com.zerahi.ravvoid.entity.mob.EntityVoidBeast;
import com.zerahi.ravvoid.register.VoidBlocks;

import net.minecraft.world.biome.Biome;


public class VoidBiome extends Biome {

	public VoidBiome(BiomeProperties properties) {
		super(properties);
		this.setRegistryName(Ref.MODID, "voidbiome");
		this.topBlock = VoidBlocks.ASH.getDefaultState();
		this.fillerBlock = VoidBlocks.VOIDSTONE.getDefaultState();
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityVoidBeast.class, 25, 2, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityShade.class, 10, 0, 1));
		this.decorator = new VoidDecorator();
		this.flowers.clear();
	}
}
