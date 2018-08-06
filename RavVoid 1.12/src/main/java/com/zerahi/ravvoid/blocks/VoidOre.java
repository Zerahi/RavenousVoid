package com.zerahi.ravvoid.blocks;

import java.util.Random;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.register.Triggers;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VoidOre extends Block implements IRegisterModels {

	public VoidOre(String name, Material materialIn) {
		super(materialIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(VoidTabs.tabVoid);
		setHardness(5.0F);
		setResistance(5.0F);
		setHarvestLevel("pickaxe", 2);
		
		VoidBlocks.BLOCKS.add(this);
		VoidItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return VoidItems.VOIDSHARD;
		
	}

	@Override
	public void registerModels() {
		VoidMod.proxy.registerItem(Item.getItemFromBlock(this), 0, "inventory");		
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,boolean willHarvest) {
		if (!world.isRemote) {
			Triggers.VOIDORE.trigger((EntityPlayerMP) player);
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}
}
