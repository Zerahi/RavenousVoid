package com.zerahi.ravvoid.blocks.tileentity;

import java.util.Random;

import com.zerahi.ravvoid.blocks.Crystallizer;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.utils.interfaces.IDisplay;
import com.zerahi.ravvoid.utils.interfaces.IUpdate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

public class TileEntityCrystallizer extends TileEntity implements ITickable, IDisplay, IUpdate {

	public Integer essence;
	public boolean active;
	private Integer delay = 0;
	private Integer delaypart = 0;
	public ItemStack output;
	public ItemStack display;
	public EntityItem entity;
	private boolean particalesActive;

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
				if (new ItemStack(compound.getCompoundTag("input")).getCount() != 0) {
					this.display = new ItemStack(compound.getCompoundTag("input"));
				} else this.display = null;
				if (new ItemStack(compound.getCompoundTag("output")).getCount() != 0) {
					this.output = new ItemStack(compound.getCompoundTag("output"));
				} else this.output = null;
				if (compound.hasKey("essence")) {
					this.essence = compound.getInteger("essence");
				} else this.essence = 0;
				if (compound.hasKey("active")) {
					this.essence = compound.getInteger("active");
				} else this.active = false;				
			}

			@Override
			public NBTTagCompound writeToNBT(NBTTagCompound compound) {
				compound = super.writeToNBT(compound);
				if (this.output != null)compound.setTag("output", this.output.serializeNBT());
				if (this.display != null)compound.setTag("input", this.display.serializeNBT());
		        if (this.essence != null)compound.setInteger("essence", this.essence);
		        compound.setBoolean("active", this.active);
				
				return compound;
			}
			
			@Override
			public void change() {this.markDirty();}
			//nbt end
			
	@SuppressWarnings("unchecked")
	@Override
	public void update() {
		//Display check
		IDisplay.check(this);
		
		//particle check
		if (world.isRemote && this.particalesActive) particles();
		
		if (this.active) {
			if (!particalesActive) IDisplay.particlesToggle(this, this.particalesActive = true, false);
			if (this.delaypart == 0)this.delaypart = 1;
			
			if (this.delay >= 600) {
				Entity spawn = new EntityItem(world, this.pos.getX()+.5, this.pos.getY()+.5, this.pos.getZ()+.5, this.output.copy());
				this.active = false;
				this.output = null;
				if (!world.isRemote) world.setBlockState(this.pos, world.getBlockState(this.pos).withProperty(Crystallizer.liquid, Integer.valueOf(0)));
				this.delaypart = 0;
				if (!world.isRemote) world.spawnEntity(spawn);
				IDisplay.removeItem(null, this);
			}
			else {this.delay++;}
		} else if (particalesActive) IDisplay.particlesToggle(this, this.particalesActive = false, false);
	}

	@SuppressWarnings("unchecked")
	public void crystalyzerList(EntityItem ent) {
		//Display check
		if (world.isRemote)IDisplay.check(this);
		//particle check
		if (world.isRemote && this.particalesActive) particles();
		
		
		if (this.essence == null) this.essence = 0;
		int liquid = ((Integer) world.getBlockState(this.pos).getValue(Crystallizer.liquid));
		if (liquid > 1){
			if (liquid == 2) this.essence = 4;
		}
		ItemStack stack = ent.getItem().copy();

		if(ItemStack.areItemsEqual(stack, new ItemStack(VoidItems.VOIDESSENCE)) && this.display == null) {
			if (this.essence<4 && ((Integer) world.getBlockState(this.pos).getValue(Crystallizer.liquid)) == 1) {
				ent.setDead();
				if (stack.getCount() >= (4 - this.essence)) {
					stack.setCount(stack.getCount() - (4 - this.essence));
					Entity spawn = new EntityItem(world, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
					if (!this.world.isRemote) this.world.spawnEntity(spawn);
					this.essence = 4;
				}
				else {
					this.essence += stack.getCount();
				}
				this.change();
				if (this.essence == 4) {if (!world.isRemote) {this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(Crystallizer.liquid, 2));}}
			}
		}
		else if (ItemStack.areItemsEqual(stack, new ItemStack(VoidItems.SPIRIT)) && this.display == null) {
			if (this.essence == 4) {
				ent.setDead();
				if (stack.getCount() > 1) {
					stack.shrink(1);
					Entity spawn = new EntityItem(this.world, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
					if (!this.world.isRemote)this.world.spawnEntity(spawn);
				}
				this.essence = 0;
				this.change();
				if (!world.isRemote) this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(Crystallizer.liquid, 3));
			}
		}
		else if (ItemStack.areItemsEqual(stack, new ItemStack(VoidItems.VOIDFRAGMENTS)) && this.display == null) {
			if (((Integer) this.world.getBlockState(this.pos).getValue(Crystallizer.liquid)).intValue() == 3) {
				ent.setDead();
				if (stack.getCount() > 1) {
					stack.shrink(1);;
					Entity spawn = new EntityItem(this.world, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
					if (!this.world.isRemote)this.world.spawnEntity(spawn);
				}
				this.active=true;
				this.output= new ItemStack(VoidItems.VOIDSHARD);
				this.display = stack.copy();
				IDisplay.display(this);
			}
		}else if (ItemStack.areItemsEqual(stack, new ItemStack(VoidItems.VOIDSHARD)) && this.display == null) {
			if (((Integer) this.world.getBlockState(this.pos).getValue(Crystallizer.liquid)).intValue() == 3) {
				ent.setDead();
				if (stack.getCount() > 1) {
					stack.shrink(1);
					Entity spawn = new EntityItem(this.world, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
					if (!this.world.isRemote)this.world.spawnEntity(spawn);
				}
				this.active=true;
				this.output= new ItemStack(VoidItems.PUREVOIDSHARD);
				this.display = stack.copy();
				IDisplay.display(this);
				this.change();
			}
		}else if (ItemStack.areItemsEqual(stack, new ItemStack(VoidItems.VOIDORB)) && this.display == null) {
			if (((Integer) this.world.getBlockState(this.pos).getValue(Crystallizer.liquid)).intValue() == 3) {
				ent.setDead();
				if (stack.getCount() > 1) {
					stack.shrink(1);
					Entity spawn = new EntityItem(this.world, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
					if (!this.world.isRemote)this.world.spawnEntity(spawn);
				}
				this.active=true;
				this.output= new ItemStack(VoidItems.AWAKENEDVOIDORB);
				this.display = stack.copy();
				IDisplay.display(this);
				this.change();
			}
		}
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
