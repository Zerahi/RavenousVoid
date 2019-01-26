package com.zerahi.ravvoid.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityVoidRift;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;
import com.zerahi.ravvoid.utils.proxy.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VoidRift extends Block implements ITileEntityProvider, IRegisterModels {
	@SuppressWarnings("unused")
	private int Spot;

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(2D, 2D, 2D, 2D, 2D, 2D);
	public VoidRift(String name, Material materialIn, int spotIn) {
		super(materialIn);	
		setUnlocalizedName(name+spotIn);
		setRegistryName(name + spotIn);
		setLightLevel(0.5f);
		setHardness(0.3F);
		Spot = spotIn;
		
		VoidBlocks.BLOCKS.add(this);
		VoidItems.ITEMS.add(new ItemBlock(this).setRegistryName(name + spotIn));
	}

	@Override
	public void registerModels() 
	{
		VoidMod.proxy.registerItem(Item.getItemFromBlock(this), 5, "inventory");	
		ClientProxy.stateMap(this);
	}

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	//Block State setup
	@Override protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] {FACING}); }
		
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}
		
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = state.getValue(FACING).getHorizontalIndex();
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
		BlockPos Tpos = pos;
		boolean found = false;
		if (!this.getLocalizedName().endsWith("5")) {
			for (int x = -1; x <=1; x++) {
				for (int y = -1; y <=1; y++) {
					for (int z = -1; z <=1; z++) {
						if (!found) {
							if (worldIn.getBlockState(Tpos.add(x, y, z)).getBlock().getRegistryName().getResourcePath().endsWith("5")) {
								Tpos = Tpos.add(x, y, z);
								found = true;
							}
						}
					}
				}
			}
		} else Tpos = pos;
		

		int DestDimID = 10;
		if (worldIn.provider.getDimensionType().getId() == 0) DestDimID = 16; else if (worldIn.provider.getDimensionType().getId() == 16) DestDimID = 0;
		if (!entityIn.isRiding() && !entityIn.isBeingRidden() && !worldIn.isRemote)
			if(entityIn.timeUntilPortal <= 0){
				if(entityIn instanceof EntityPlayerMP){
					EntityPlayerMP player = (EntityPlayerMP)entityIn;
					player.timeUntilPortal = 30;
					TileEntity te = worldIn.getTileEntity(Tpos);
					BlockPos dpos = Tpos;if (DestDimID == 16) {
						dpos = dpos.offset(worldIn.getBlockState(pos).getValue(FACING), 1);
					}
					((TileEntityVoidRift)te).teleport(entityIn, dpos, DestDimID);
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

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if (this.getRegistryName().getResourcePath().endsWith("5")) {
			return new TileEntityVoidRift();
		}
		return null;
	}
}
