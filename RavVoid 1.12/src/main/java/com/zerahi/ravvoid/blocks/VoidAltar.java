package com.zerahi.ravvoid.blocks;

import com.zerahi.ravvoid.Ref;
import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityAltar;
import com.zerahi.ravvoid.register.Triggers;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IDisplay;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VoidAltar extends Block implements ITileEntityProvider, IRegisterModels {

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(0.125d, 0.0d, 0.125d, 0.875d, 0.875d, 0.875d);
	
	public VoidAltar(String name, Material materialIn) {
		super(materialIn);	
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(VoidTabs.tabVoid);
		setLightLevel(0.3f);
		setHardness(0.5F);
		setResistance(0.5F);
		
		VoidBlocks.BLOCKS.add(this);
		VoidItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}

	@Override
	public void registerModels() {
		VoidMod.proxy.registerItem(Item.getItemFromBlock(this), 0, "inventory");		
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileEntityAltar();
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return bounds;
    }
	
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
    
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	TileEntityAltar te = (TileEntityAltar) worldIn.getTileEntity(pos);
    	ItemStack heldItem = playerIn.getHeldItemMainhand();
    	
        if (te.display == null)
        {
            if (Ref.altarListTier1(heldItem, worldIn) != null || ItemStack.areItemsEqual(heldItem, new ItemStack(VoidItems.VOIDORB)) ||
            		ItemStack.areItemsEqual(new ItemStack(heldItem.getItem()), new ItemStack(VoidItems.AWAKENEDVOIDORB))) {
            	te.setDisplay(heldItem.copy());
            	te.getDisplay().setCount(1);
                	te.setDisplayItem(playerIn, heldItem);
            		heldItem.shrink(1);
                	return true;
            }
        }
        else {
        	IDisplay.removeItem(playerIn, te);
        	return true;
        }
        
        return false;
    }
    
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (!worldIn.isRemote)Triggers.VOIDALTAR.trigger((EntityPlayerMP) placer);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
    
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
    	TileEntityAltar te = (TileEntityAltar) world.getTileEntity(pos);
	    if(te.rift) {te.riftBreak();te.destroyTE = true;}
        if (te.destroyTE) return true; //If it will harvest, delay deletion of the block until after getDrops
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }
    
	    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	    {
		    if (!worldIn.isRemote) {
				if (worldIn.getBlockState(pos.add(0, 2, 0)).getBlock() == VoidBlocks.VOIDREND) {
					worldIn.destroyBlock(pos.add(0, 2, 0), false);
				}
				TileEntityAltar te = (TileEntityAltar) worldIn.getTileEntity(pos);
				if (te.rift) {
					te.riftBreak();
					te.destroyTE = true;
				}
				IDisplay.blockBreak(te);
			}
	    }
    
}
