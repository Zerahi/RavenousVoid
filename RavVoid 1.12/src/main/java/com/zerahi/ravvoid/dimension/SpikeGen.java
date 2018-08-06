package com.zerahi.ravvoid.dimension;

import java.util.Random;

import com.zerahi.ravvoid.register.VoidBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class SpikeGen extends WorldGenerator {

		@Override
		public boolean generate(World world, Random rand, BlockPos pos) {

			while(world.isAirBlock(pos) || world.getBlockState(pos) == VoidBlocks.ASH.getDefaultState())pos = pos.down();

			if(world.getBlockState(pos) != VoidBlocks.VOIDSTONE.getDefaultState()) return false;

			IBlockState block = VoidBlocks.VOIDSTONE.getDefaultState();

			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			for(int i = 0; i < 8 + rand.nextInt(3); i++)
				world.setBlockState(new BlockPos(x, y + i, z), block, 2);
			for(int i = 0; i < 4 + rand.nextInt(2); i++)
				world.setBlockState(new BlockPos(x + 1, y + i, z), block, 2);
			for(int i = 0; i < 4 + rand.nextInt(2); i++)
				world.setBlockState(new BlockPos(x - 1, y + i, z), block, 2);
			for(int i = 0; i < 4 + rand.nextInt(2); i++)
				world.setBlockState(new BlockPos(x, y + i, z + 1), block, 2);
			for(int i = 0; i < 4 + rand.nextInt(2); i++)
				world.setBlockState(new BlockPos(x, y + i, z - 1), block, 2);
			for(int i = 0; i < 2 + rand.nextInt(2); i++)
				world.setBlockState(new BlockPos(x + 1, y + i, z + 1), block, 2);
			for(int i = 0; i < 2 + rand.nextInt(2); i++)
				world.setBlockState(new BlockPos(x - 1, y + i, z - 1), block, 2);
			for(int i = 0; i < 2 + rand.nextInt(2); i++)
				world.setBlockState(new BlockPos(x - 1, y + i, z + 1), block, 2);
			for(int i = 0; i < 2 + rand.nextInt(2); i++)
				world.setBlockState(new BlockPos(x + 1, y + i, z - 1), block, 2);

			return true;
		}
	}
