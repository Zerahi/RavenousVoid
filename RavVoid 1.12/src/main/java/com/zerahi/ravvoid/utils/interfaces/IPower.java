package com.zerahi.ravvoid.utils.interfaces;

import net.minecraft.item.ItemStack;

public interface IPower {
	public int getMaxPower();
        
    public default void powerHelper(ItemStack item, int value) {
    	if (item.getTagCompound().getInteger("power") + value <= this.getMaxPower()) {
    		item.getTagCompound().setInteger("power", item.getTagCompound().getInteger("power") + value);
    	} else {
    		item.getTagCompound().setInteger("power", this.getMaxPower());
    	}
    }

	public default int getPower(ItemStack item) {
		return item.getTagCompound().getInteger("power");
	}
	public default void setPower(ItemStack item, int value) {
		item.getTagCompound().setInteger("power", value);
	}
	
	public default boolean getActive(ItemStack item) {
		return item.getTagCompound().getBoolean("active");
	}
	
	public default void setActive(ItemStack item, boolean state) {
		item.getTagCompound().setBoolean("active", state);
	}
}
