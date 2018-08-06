package com.zerahi.ravvoid.register;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class VoidTabs {
	
	public static final CreativeTabs tabVoid = new CreativeTabs("tabVoid") {
		
		@Override
		public ItemStack getTabIconItem() {
			
			return new ItemStack(VoidItems.AWAKENEDVOIDORB);
		}
	};
}
