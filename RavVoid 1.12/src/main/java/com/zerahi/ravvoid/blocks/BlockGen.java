package com.zerahi.ravvoid.blocks;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockGen extends Block implements IRegisterModels {

	public BlockGen(String name, Material materialIn) {
		super(materialIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(VoidTabs.tabVoid);
		
		VoidBlocks.BLOCKS.add(this);
		VoidItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}

	@Override
	public void registerModels() {
		VoidMod.proxy.registerItem(Item.getItemFromBlock(this), 0, "inventory");		
	}
}
