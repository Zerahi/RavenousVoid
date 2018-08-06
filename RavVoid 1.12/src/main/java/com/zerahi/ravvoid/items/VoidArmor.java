package com.zerahi.ravvoid.items;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;


public class VoidArmor extends ItemArmor implements IRegisterModels {

	public VoidArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setCreativeTab(VoidTabs.tabVoid);
		setUnlocalizedName(name);
		setRegistryName(name);
		
		VoidItems.ITEMS.add(this);
	}

	public void registerModels() 
	{
		VoidMod.proxy.registerItem(this, 0, "inventory");
	}

}
