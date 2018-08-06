package com.zerahi.ravvoid.blocks;

import java.util.Random;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityVoidNode;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VoidNode extends Block implements ITileEntityProvider,IRegisterModels {

	public VoidNode(String name, Material materialIn) {
		super(materialIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setLightLevel(0.3f);
		setHardness(1.5F);
		setResistance(10.0F);
		
		VoidBlocks.BLOCKS.add(this);
		VoidItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileEntityVoidNode();
	}
	
	@Override
	public void registerModels() {
		VoidMod.proxy.registerItem(Item.getItemFromBlock(this), 0, "inventory");		
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return null;
	}
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	ItemStack heldItem = playerIn.getHeldItemMainhand();
    	ItemStack heldoff = playerIn.getHeldItemOffhand();
    	
    	if (ItemStack.areItemsEqual(heldItem, new ItemStack(VoidItems.CONFIGURER)))
        {
            TileEntity te = worldIn.getTileEntity(pos);
            ((TileEntityVoidNode) te).shiftSpawned(playerIn);
            return true;
        } else if (ItemStack.areItemsEqual(heldoff, new ItemStack(VoidItems.CONFIGURER))) {
        	TileEntity te = worldIn.getTileEntity(pos);
            ((TileEntityVoidNode) te).changeMode(playerIn);
            return true;
        }
		return false;
    }
}
