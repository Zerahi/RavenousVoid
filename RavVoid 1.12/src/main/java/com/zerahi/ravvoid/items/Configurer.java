package com.zerahi.ravvoid.items;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.item.Item;

public class Configurer extends Item implements IRegisterModels {
	public Configurer(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		
		VoidItems.ITEMS.add(this);
	}


	public void registerModels() 
	{
		VoidMod.proxy.registerItem(this, 0, "inventory");
	}
}