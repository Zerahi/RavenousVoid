package com.zerahi.ravvoid.blocks.tileentity;

import java.util.Random;

import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.utils.interfaces.IDisplay;
import com.zerahi.ravvoid.utils.interfaces.IPower;
import com.zerahi.ravvoid.utils.interfaces.IUpdate;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;


public class TileEntityConduit extends TileEntity implements ITickable, IDisplay, IUpdate{

	public ItemStack display = null;
	public EntityItem entity;
	public int delay;
	public int renderdelay;
	public int upkeepdelay;
	public boolean particalesActive;

	//nbt setup
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(this.pos, 0, tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
        readFromNBT(packet.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (new ItemStack(compound.getCompoundTag("display")).getCount() != 0) {
			this.display = new ItemStack(compound.getCompoundTag("display"));
		} else this.display = null;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		if (this.display != null)compound.setTag("display", this.display.serializeNBT());
		return compound;
	}
	
	@Override
	public void change() {this.markDirty();}
	//nbt end
	
	public void update() {
		//Display check
		IDisplay.check(this);
		
		//particle check
		if (world.isRemote && this.particalesActive) particles();

		if (this.display != null){
			IPower item = ((IPower)this.display.getItem());
			if (this.upkeepdelay >= 60 && item.getPower(this.display) < item.getMaxPower()) {
				int powered = powered ();
				if (powered > 0) {
					if (item.getPower(this.display) + (10 * powered) <= item.getMaxPower()) {
						item.powerHelper(this.display, +(10 * powered));
					}
					else {
						item.setPower(this.display, item.getMaxPower());
					}
					if (!particalesActive) IDisplay.particlesToggle(this, this.particalesActive = true, false);
					this.change();
				this.upkeepdelay = 0;
				} else if (particalesActive) IDisplay.particlesToggle(this, this.particalesActive = false, false);
			} else if (item.getPower(this.display) < item.getMaxPower()) {
				this.upkeepdelay++;
			} else if (this.particalesActive) IDisplay.particlesToggle(this, this.particalesActive = false, false);
		} else if (this.particalesActive) IDisplay.particlesToggle(this, this.particalesActive = false, false);
	}
	
	public int powered() {
		BlockPos spos = this.pos.add(-5, -5, -5);
		int x;
		int y;
		int z;
		int cnt = 0;
		int cnt2 = 0;
		int ammount = 4;

		
		for(x = 0; x<=11; x++) {
			for(y = 0; y<=11; y++) {
				for(z = 0; z<=11; z++) {
					if (this.world.getBlockState(spos.add(x, y, z)).getBlock() == VoidBlocks.PURESHARDBLOCK) {cnt++;}
					if (this.world.getBlockState(spos.add(x, y, z)).getBlock() == VoidBlocks.SHARDBLOCK) {cnt2++;}
					if (cnt >= ammount) {break;}
					if (cnt2 >= ammount) {break;}
				}
			}
		}
		
		if (cnt2 >= ammount) return 2;
		if (cnt + cnt2 >= ammount) return 1;
		return 0;
	}

	@Override
	public EntityItem getEntity() {
		return this.entity;
	}

	@Override
	public void setEntity(EntityItem entityIn) {
		this.entity = entityIn;		
	}

	@Override
	public ItemStack getDisplay() {
		return this.display;
	}

	@Override
	public void setDisplay(ItemStack displayIn) {
		this.display = displayIn;
	}

	@Override
	public void particles() {
		//Particle Spawn
		if (this.delay < 20) {this.delay++;}
		else {
			Random rand = new Random();
			double d0 = (double) this.pos.getX() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.4D;
			double d1 = (double) ((float) this.pos.getY() + 1F);
			double d2 = (double) this.pos.getZ() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.4D;
			float f = 15.0F;
			float f1 = f * 0.6F + 0.4F;
			float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
			float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
			this.world.spawnParticle(EnumParticleTypes.DRIP_LAVA, d0, d1, d2, (double) f1, (double) f2, (double) f3,new int[0]);
			this.delay = 0;
		}
	}

	@Override
	public void setParticle(boolean state) {this.particalesActive = state;}
}
