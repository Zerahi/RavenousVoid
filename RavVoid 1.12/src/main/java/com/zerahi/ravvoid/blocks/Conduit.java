package com.zerahi.ravvoid.blocks;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityConduit;
import com.zerahi.ravvoid.register.Triggers;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IDisplay;
import com.zerahi.ravvoid.utils.interfaces.IPower;
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

public class Conduit extends Block implements ITileEntityProvider, IRegisterModels {

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(00.28125d, 0.0d, 0.28125d, 0.71875d, 0.9375d, 0.71875d);
	
	public Conduit(String name, Material materialIn) {
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

	public void registerModels() {
		VoidMod.proxy.registerItem(Item.getItemFromBlock(this), 0, "inventory");		
	}
    
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (!worldIn.isRemote)Triggers.CONDUIT.trigger((EntityPlayerMP) placer);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileEntityConduit();
	}
	
	@Override
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
	
	
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isSideSolid(pos.down(), EnumFacing.UP, true);
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	TileEntityConduit te = (TileEntityConduit) worldIn.getTileEntity(pos);
    	ItemStack heldItem = playerIn.getHeldItemMainhand();
    	
        if (te.display == null)
        {
            if (heldItem.getItem() instanceof IPower && heldItem.hasTagCompound()) {
            	if (heldItem.getTagCompound().hasKey("active")) heldItem.getTagCompound().setBoolean("active", false);
            	te.setDisplay(heldItem.copy());
            	te.getDisplay().setCount(1);
        		heldItem.shrink(1);
                	IDisplay.display(te);
                	return true;
            }
        }
        else {
        	IDisplay.removeItem(playerIn, te);
        	return true;
        }
        
        return false;
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        IDisplay.blockBreak(te);
        super.breakBlock(worldIn, pos, state);
    }
    
}
