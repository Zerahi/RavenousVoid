package com.zerahi.ravvoid.utils.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	public void init(FMLInitializationEvent event) {	
		
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}

	public void registerItem(Item item, int meta, String id) {}
	public void registerVariant(Item item, int meta, String nameadd, String id) {}
}
