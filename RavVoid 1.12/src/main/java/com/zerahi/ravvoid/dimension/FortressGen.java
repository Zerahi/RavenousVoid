package com.zerahi.ravvoid.dimension;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.zerahi.ravvoid.register.VoidBlocks;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Rotation;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

public class FortressGen extends WorldGenerator {
	PlacementSettings place;
	private int index;
	private List<String> list = new ArrayList<String> ();
	
		public FortressGen(int i) {
			this.index = i;
			if (list.size() == 0) initList();
		}

		private void initList() {
			list.add("fortress1");
			
		}

		@Override
		public boolean generate(World world, Random rand, BlockPos pos) {
			if (!DistanceCheck.check(list.get(index), pos, 100)) {
				return false;
			}
			
			while(world.isAirBlock(pos))pos = pos.down();
			
			if(world.getBlockState(pos) != VoidBlocks.VOIDSTONE.getDefaultState() && world.getBlockState(pos) != VoidBlocks.ASH.getDefaultState()) return false;
			
			this.place = new PlacementSettings();
			Template temp = new Template();
			InputStream inputstream = MinecraftServer.class.getResourceAsStream("/assets/" + "rv" + "/structures/" + list.get(index) + ".nbt");
			NBTTagCompound nbttagcompound = null;
			try {
				nbttagcompound = CompressedStreamTools.readCompressed(inputstream);
		        if (!nbttagcompound.hasKey("DataVersion", 99))
		        {
		            nbttagcompound.setInteger("DataVersion", 500);
		        }
			} catch (IOException e1) {
			}
	        temp = new Template();
			temp.read(DataFixesManager.createFixer().process(FixTypes.STRUCTURE, nbttagcompound));
			int r = rand.nextInt(4);
			if (r == 0) {
				this.place.setRotation(Rotation.NONE);
				BlockPos size = temp.transformedSize(this.place.getRotation());
				pos = pos.add(-size.getX()/2, 0, -size.getZ()/2);
			} else if (r == 1) {
				this.place.setRotation(Rotation.CLOCKWISE_90);
				BlockPos size = temp.transformedSize(this.place.getRotation());
				pos = pos.add(size.getX()/2, 0, -size.getZ()/2);
			} else if (r == 2) {
				this.place.setRotation(Rotation.CLOCKWISE_180);
				BlockPos size = temp.transformedSize(this.place.getRotation());
				pos = pos.add(size.getX()/2, 0, size.getZ()/2);
			}	else if (r == 3) {
				this.place.setRotation(Rotation.COUNTERCLOCKWISE_90);
				BlockPos size = temp.transformedSize(this.place.getRotation());
				pos = pos.add(-size.getX()/2, 0, size.getZ()/2);
			}	
	        try {
				temp.addBlocksToWorld(world, pos, this.place);
			} catch (Exception e) {
			}
			return true;
		}
	}
