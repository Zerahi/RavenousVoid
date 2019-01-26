package com.zerahi.ravvoid.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityChaoticNode;
import com.zerahi.ravvoid.register.Triggers;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;
import com.zerahi.ravvoid.utils.proxy.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VoidTear extends Block implements IRegisterModels {


	protected static final AxisAlignedBB bounds = new AxisAlignedBB(2D, 2D, 2D, 2D, 2D, 2D);
	public VoidTear(String name, Material materialIn) {
		super(materialIn);	
		setUnlocalizedName(name);
		setRegistryName(name);
		setLightLevel(0.5f);
		setHardness(0.3F);
		
		VoidBlocks.BLOCKS.add(this);
		VoidItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}

	@Override
	public void registerModels() 
	{
		VoidMod.proxy.registerItem(Item.getItemFromBlock(this), 1, "inventory");
		ClientProxy.stateMap(this);
	}

	@SuppressWarnings("rawtypes")
	public static final IProperty SPOT = PropertyInteger.create("spot", 1, 2);
	
	//Block State setup
	@Override protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] {SPOT}); }
		
	@SuppressWarnings("unchecked")
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta != 0) {
			return this.getDefaultState().withProperty(SPOT, Integer.valueOf((meta)));
		}
		else {
			return this.getDefaultState().withProperty(SPOT, 1);
		}
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = ((Integer)state.getValue(SPOT)).intValue();
		return i;
	}
	//Setup Finished
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return null;
		
	}
	
	@Nullable
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return null;
	}
	
	/**
	 * Called When an Entity Collided with the Block
	 */
	
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		int VoidDimensionID = 10;
		if (worldIn.provider.getDimensionType().getId() == 0) VoidDimensionID = 16; else if (worldIn.provider.getDimensionType().getId() == 16) VoidDimensionID = 0;
		if (!entityIn.isRiding() && !entityIn.isBeingRidden() && !worldIn.isRemote)
			if(entityIn.timeUntilPortal <= 0){
				if(entityIn instanceof EntityPlayerMP){
					EntityPlayerMP player = (EntityPlayerMP)entityIn;
					if (!worldIn.isRemote) Triggers.TEAR.trigger(player);
					player.timeUntilPortal = 30;
					int shift;
					if (worldIn.getBlockState(pos.down(1)).getBlock() == this) shift = 2; else shift = 1;
					TileEntity te = worldIn.getTileEntity(pos.down(shift));
					((TileEntityChaoticNode)te).teleport(entityIn, VoidDimensionID);
				}
			} else entityIn.timeUntilPortal = entityIn.getPortalCooldown();
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return bounds;
	}
	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
	    return NULL_AABB;
	}
	
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
    
	public BlockRenderLayer getBlockLayer()
  	 {
		return BlockRenderLayer.TRANSLUCENT;
  	 }
}
