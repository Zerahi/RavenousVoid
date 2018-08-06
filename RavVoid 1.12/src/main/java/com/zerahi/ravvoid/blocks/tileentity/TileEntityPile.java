package com.zerahi.ravvoid.blocks.tileentity;

import com.zerahi.ravvoid.blocks.PileBlock;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityPile extends TileEntity implements ITickable  {
public int t = 0;
	
	
	
	
	public void update() {
		if (!world.isRemote){
			pos = this.getPos();
			World worldIn = world;
			
			int i = worldIn.getBlockState(pos).getValue(PileBlock.active);
			if (0.1f >= worldIn.getLightBrightness(pos) && worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
	
				if (worldIn.getBlockState(pos.up()).getBlock() == VoidBlocks.PILEBLOCK) {
					worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(PileBlock.active, Integer.valueOf(0)));
	    		}
				
				if (t <= 500) {
					++t;
				}
				else {
					t=0;
		
		    		if (i <= 9 && worldIn.getBlockState(pos.up()).getBlock() == VoidBlocks.PILEBLOCKACTIVE) {
			    		worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(PileBlock.active, Integer.valueOf(i)));
		    		}
		    		else {
						Block.spawnAsEntity(worldIn, pos, new ItemStack(VoidItems.VOIDORB));
		    			worldIn.destroyBlock(pos, false);
		    		}
	    		}
	    	}
	    	else {
	    		if (worldIn.getBlockState(pos.up()).getBlock() == VoidBlocks.PILEBLOCKACTIVE) {
	    		worldIn.setBlockState(pos, VoidBlocks.PILEBLOCK.getDefaultState());
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(PileBlock.active, Integer.valueOf(0)));
				}
			}
			if(worldIn.getBlockState(pos.down()).isFullCube() == false || worldIn.getBlockState(pos.up()).getBlock() != Blocks.AIR) {
	
				worldIn.destroyBlock(pos, true);
			}
		}
		
    }
}
