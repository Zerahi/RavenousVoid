package com.zerahi.ravvoid.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityCrystallizer;
import com.zerahi.ravvoid.register.Triggers;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IDisplay;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
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


public class Crystallizer extends Block implements ITileEntityProvider, IRegisterModels {

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(0.1875d, 0.0d, 0.1875d, 0.8125d, 0.75d, 0.8125d);
	protected static final AxisAlignedBB stand = new AxisAlignedBB(0.1875d, 0.0d, 0.1875d, 0.8125d, 0.4375d, 0.8125d);
	protected static final AxisAlignedBB west = new AxisAlignedBB(0.21875d, 0.4375d, 0.1875d, 0.25d, 0.75d, 0.8125d);
	protected static final AxisAlignedBB north = new AxisAlignedBB(0.1875d, 0.4375d, 0.75d, 0.8125d, 0.75d, 0.78125d);
	protected static final AxisAlignedBB east = new AxisAlignedBB(0.75d, 0.4375d, 0.1875d, 0.78125d, 0.75d, 0.8125d);
	protected static final AxisAlignedBB south = new AxisAlignedBB(0.1875d, 0.4375d, 0.21875d, 0.8125d, 0.75d, 0.25);
	@SuppressWarnings("rawtypes")
	public static final IProperty liquid = PropertyInteger.create("liquid", 0, 3);
	
	public Crystallizer(String name, Material materialIn) {
		super(materialIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(VoidTabs.tabVoid);
		setHardness(0.5F);
		setResistance(0.5F);
		
		VoidBlocks.BLOCKS.add(this);
		VoidItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}

	public void registerModels() {
		VoidMod.proxy.registerItem(Item.getItemFromBlock(this), 0, "inventory");		
	}
	//Block State setup
	@Override protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] {liquid}); }
	
	@SuppressWarnings("unchecked")
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
	    return getDefaultState().withProperty(liquid, meta);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (!worldIn.isRemote)Triggers.CRYSTALLIZER.trigger((EntityPlayerMP) placer);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getMetaFromState(IBlockState state)
	{
	    return ((Integer)state.getValue(liquid));
	}
	//Setup Finished
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileEntityCrystallizer();
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return bounds;
    }
	
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, stand);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, west);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, north);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, east);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, south);
    }
	
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity ent) {

		TileEntityCrystallizer tile = (TileEntityCrystallizer) world.getTileEntity(pos);
		if(ent instanceof EntityItem && tile.getDisplay() == null && !world.isRemote && ((Integer) world.getBlockState(pos).getValue(liquid)).intValue() > 0) {
			tile.newItem((EntityItem) ent);
		}
	}
    
	@SuppressWarnings("unchecked")
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	Integer fill = ((Integer) state.getValue(liquid)).intValue();
    	ItemStack heldItem = playerIn.getHeldItemMainhand();
    	
    	if (ItemStack.areItemsEqual(heldItem, new ItemStack(Items.WATER_BUCKET))  && fill == 0)
        {
            if (!worldIn.isRemote)
            {
                if (!playerIn.capabilities.isCreativeMode)
                {
                    playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                }
                worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(Crystallizer.liquid, Integer.valueOf(1)));
            }
            return true;
        }
        else if (ItemStack.areItemsEqual(heldItem, new ItemStack(Items.BUCKET)) && fill == 1)
        {
            if (!worldIn.isRemote)
            {
                if (!playerIn.capabilities.isCreativeMode)
                {
                    heldItem.shrink(1);

                    if (heldItem.getCount() == 1)
                    {
                        playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
                    }
                    else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET)))
                    {
                        playerIn.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                    }
                }
    			worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(Crystallizer.liquid, Integer.valueOf(0)));
            }
            return true;
        }
        else return false;
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        IDisplay.blockBreak(te);
        super.breakBlock(worldIn, pos, state);
    }    
}
