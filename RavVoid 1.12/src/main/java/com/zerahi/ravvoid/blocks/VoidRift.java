package com.zerahi.ravvoid.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.dimension.TeleporterVoid;
import com.zerahi.ravvoid.register.Triggers;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;
import com.zerahi.ravvoid.utils.proxy.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class VoidRift extends Block implements IRegisterModels {
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
		int VoidDimensionID = 10;
		if (worldIn.provider.getDimensionType().getId() == 0) VoidDimensionID = 16; else if (worldIn.provider.getDimensionType().getId() == 16) VoidDimensionID = 0;
		if (!entityIn.isRiding() && !entityIn.isBeingRidden() && !worldIn.isRemote)
			if(entityIn.timeUntilPortal <= 0){
				if(entityIn instanceof EntityPlayerMP){
					EntityPlayerMP player = (EntityPlayerMP)entityIn;
					if (!worldIn.isRemote) Triggers.THROUGH.trigger(player);
					//					thePlayer.addStat(ACAchievements.enter_abyssal_wasteland, 1);

					player.timeUntilPortal = 30;
					if (player.dimension != VoidDimensionID)
					{
						if(!ForgeHooks.onTravelToDimension(player, VoidDimensionID)) return;
						player.mcServer.getPlayerList().transferPlayerToDimension(player, VoidDimensionID, new TeleporterVoid(player.mcServer.getWorld(VoidDimensionID), this, Blocks.AIR.getDefaultState()));
					}
					else {
						if(!ForgeHooks.onTravelToDimension(player, 0)) return;
						player.mcServer.getPlayerList().transferPlayerToDimension(player, 0, new TeleporterVoid(player.mcServer.getWorld(0), this, Blocks.AIR.getDefaultState()));
					}
				} else {
					MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
					entityIn.timeUntilPortal = entityIn.getPortalCooldown();

					if(entityIn.dimension != VoidDimensionID){
						if(!ForgeHooks.onTravelToDimension(entityIn, VoidDimensionID)) return;

						int i = entityIn.dimension;

						entityIn.dimension = VoidDimensionID;
						worldIn.removeEntityDangerously(entityIn);

						entityIn.isDead = false;

						server.getPlayerList().transferEntityToWorld(entityIn, i, server.getWorld(i), server.getWorld(VoidDimensionID), new TeleporterVoid(server.getWorld(VoidDimensionID), this, Blocks.AIR.getDefaultState()));
					} else {
						if(!ForgeHooks.onTravelToDimension(entityIn, 0)) return;

						entityIn.dimension = 0;
						worldIn.removeEntityDangerously(entityIn);

						entityIn.isDead = false;

						server.getPlayerList().transferEntityToWorld(entityIn, VoidDimensionID, server.getWorld(VoidDimensionID), server.getWorld(0), new TeleporterVoid(server.getWorld(0), this, Blocks.AIR.getDefaultState()));
					}
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
