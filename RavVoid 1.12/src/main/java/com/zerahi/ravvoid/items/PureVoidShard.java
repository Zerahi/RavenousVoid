package com.zerahi.ravvoid.items;

import java.util.Random;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.blocks.PureShardBlock;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PureVoidShard extends Item implements IRegisterModels {
	public PureVoidShard(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(VoidTabs.tabVoid);
		
		VoidItems.ITEMS.add(this);
	}
	
	public void registerModels() 
	{
		VoidMod.proxy.registerItem(this, 0, "inventory");
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack stack = player.getHeldItem(hand);
        boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
        BlockPos blockpos = flag ? pos : pos.offset(facing);

        if (player.canPlayerEdit(blockpos, facing, stack) && 
        		worldIn.mayPlace(worldIn.getBlockState(blockpos).getBlock(), blockpos, false, facing, player)
        		&& VoidBlocks.PURESHARDBLOCK.canPlaceBlockAt(worldIn, blockpos))
        {
            stack.shrink(1);
            Random rand = new Random();
		int x = rand.nextInt(3);
		if (x == 0) {
			worldIn.setBlockState(blockpos, VoidBlocks.PURESHARDBLOCK.getDefaultState().withProperty(PureShardBlock.FACING, EnumFacing.NORTH));
		} else if (x == 1) {
			worldIn.setBlockState(blockpos, VoidBlocks.PURESHARDBLOCK.getDefaultState().withProperty(PureShardBlock.FACING, EnumFacing.EAST));
		}else if (x == 2) {
			worldIn.setBlockState(blockpos, VoidBlocks.PURESHARDBLOCK.getDefaultState().withProperty(PureShardBlock.FACING, EnumFacing.WEST));
		}else if (x == 3) {
			worldIn.setBlockState(blockpos, VoidBlocks.PURESHARDBLOCK.getDefaultState().withProperty(PureShardBlock.FACING, EnumFacing.SOUTH));
		}
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }
}
