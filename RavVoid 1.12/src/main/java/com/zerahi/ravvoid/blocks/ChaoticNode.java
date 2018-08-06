package com.zerahi.ravvoid.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityChaoticNode;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ChaoticNode extends Block implements ITileEntityProvider, IRegisterModels {

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.75D, 0.875D);
	
	public ChaoticNode(String name, Material materialIn) {
		super(materialIn);	
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(0.5F);
		setHardness(50.0F);
		setResistance(2000.0F);
		
		VoidBlocks.BLOCKS.add(this);
		VoidItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}

	@Override
	public void registerModels() {
		VoidMod.proxy.registerItem(Item.getItemFromBlock(this), 0, "inventory");		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (worldIn.isAirBlock(pos.up()) && worldIn.isAirBlock(pos.up(2))) {
			worldIn.setBlockState(pos.up(), VoidBlocks.VOIDTEAR.getDefaultState().withProperty(VoidTear.SPOT, 2));
			worldIn.setBlockState(pos.up(2), VoidBlocks.VOIDTEAR.getDefaultState().withProperty(VoidTear.SPOT, 1));
		}
	}
	
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityChaoticNode();
    }
	
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return bounds;
    }

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
	    return bounds;
	}
	
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
	    worldIn.destroyBlock(pos.up(2), false);
	    worldIn.destroyBlock(pos.up(), false);
	}
    
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isSideSolid(pos.down(), EnumFacing.UP, true && worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR);
    }
}
