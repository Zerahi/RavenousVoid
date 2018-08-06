package com.zerahi.ravvoid.items;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.item.Item;

public class ItemGen extends Item implements IRegisterModels {
	public ItemGen(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(VoidTabs.tabVoid);
		
		VoidItems.ITEMS.add(this);
	}


	public void registerModels() 
	{
		VoidMod.proxy.registerItem(this, 0, "inventory");
	}
	
	
}