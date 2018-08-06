package com.zerahi.ravvoid.utils.interfaces;

import com.zerahi.ravvoid.entity.EntityItemProxy;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IDisplay {

	public EntityItem getEntity();
	public void setEntity(EntityItem entityIn);
	public ItemStack getDisplay();
	public void setDisplay(ItemStack displayIn);
	
	public static void display(TileEntity te) {
		IDisplay td = (IDisplay) te;
		EntityItem entity = td.getEntity();
		ItemStack display = td.getDisplay();
		World world = te.getWorld();
		BlockPos pos = te.getPos();
		if (entity == null) entity = new EntityItemProxy(world, pos.getX(), pos.getY(), pos.getZ(), display.copy());
		entity.setNoDespawn();
		entity.setPositionAndRotation(pos.getX()+.5, pos.getY()+1d, pos.getZ()+.5, 0, 1);
		if (!world.isRemote)world.spawnEntity(entity);
		td.setEntity(entity);
		((IUpdate) te).change();
	}
	
	public static void removeItem(EntityPlayer playerIn, TileEntity te) {
		IDisplay td = (IDisplay) te;
		EntityItem entity = td.getEntity();
		ItemStack display = td.getDisplay();

		World world = te.getWorld();
		
		if (!world.isRemote && playerIn != null)playerIn.entityDropItem(display, 1);
		if (entity != null) {entity.setDead();}
		td.setDisplay(null);
		td.setEntity(null);
		((IUpdate) te).change();
	}
	
	public static void blockBreak(TileEntity te) {
		IDisplay td = (IDisplay) te;
		EntityItem entity = td.getEntity();
		ItemStack display = td.getDisplay();
		World world = te.getWorld();
		BlockPos pos = te.getPos();
        if(td.getDisplay() != null) {
    		EntityItem item = new EntityItem(world,pos.getX()+.5, pos.getY()+1d, pos.getZ()+.5, display.copy());
    		if (!world.isRemote)world.spawnEntity(item);
        }
        if(entity != null)entity.setDead();
		((IUpdate) te).change();
	}
}
