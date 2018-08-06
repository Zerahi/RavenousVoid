package com.zerahi.ravvoid.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityItemProxy extends EntityItem {

	public EntityItemProxy(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.makeFakeItem();
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
    {
            return false;
    }

}
