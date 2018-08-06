package com.zerahi.ravvoid.register;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class VoidWorldGenerator implements IWorldGenerator {

	private final WorldGenMinable oreGenOverworld;

	public VoidWorldGenerator() {
		oreGenOverworld = new WorldGenMinable(VoidBlocks.VOIDORE.getDefaultState(), 8, BlockMatcher.forBlock(Blocks.STONE));
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		final BlockPos chunkPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);

		switch (world.provider.getDimension()) {
			case 0:
				for (int i = 0; i < 16; i++) {
					oreGenOverworld.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(108) + 5, random.nextInt(16)));
				}
				break;
			default:
				break;
		}
	}
}