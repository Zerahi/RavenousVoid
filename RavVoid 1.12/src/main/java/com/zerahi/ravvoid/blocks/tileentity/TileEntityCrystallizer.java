package com.zerahi.ravvoid.blocks.tileentity;

import java.util.Random;

import com.zerahi.ravvoid.blocks.Crystallizer;
import com.zerahi.ravvoid.objects.CrystallizerPattern;
import com.zerahi.ravvoid.objects.CrystallizerPattern.Binders;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.utils.interfaces.IDisplay;
import com.zerahi.ravvoid.utils.interfaces.IUpdate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCrystallizer extends TileEntity implements ITickable, IDisplay, IUpdate {
	public Integer binderCount = 0;
	private Integer delay = 0;
	private Integer delaypart = 0;
	public ItemStack output;
	public ItemStack display;
	public EntityItem entity;
	private boolean particalesActive;
	private static enum Liquids {None, Water, VoidLiquid, PureLiquid;
		private static Liquids index(int index) {
			switch (index) {
			case 0 : return Liquids.None;
			case 1 : return Liquids.Water;
			case 2 : return Liquids.VoidLiquid;
			case 3 : return Liquids.PureLiquid;
			default : return null;
			}
		}
		
		private static int index(Liquids liq) {
			switch (liq) {
			case None : return 0;
			case Water : return 1;
			case VoidLiquid : return 2;
			case PureLiquid : return 3;
			default : return -1;
			}
		}
	}
	private int PatternIndex = -1;
	

	//nbt setup
			@Override
		   	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
				return (oldState.getBlock() != newState.getBlock());
		    }
	
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
				if (compound.hasKey("bindercount")) {
					this.binderCount = compound.getInteger("bindercount");
				} else this.binderCount = 0;
				if (compound.hasKey("binder")) {
					this.PatternIndex = compound.getInteger("pattern");
				} else this.PatternIndex = -1;		
			}

			@Override
			public NBTTagCompound writeToNBT(NBTTagCompound compound) {
				compound = super.writeToNBT(compound);
				if (this.output != null)compound.setTag("output", this.output.serializeNBT());
				if (this.display != null)compound.setTag("input", this.display.serializeNBT());
		        if (this.binderCount != null)compound.setInteger("bindercount", this.binderCount);
		        if (this.PatternIndex != -1)compound.setInteger("pattern", this.PatternIndex);
				
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
		if (this.world.isRemote && this.particalesActive) particles();
		
		if (this.getDisplay() != null) {
			if (!this.particalesActive) IDisplay.particlesToggle(this, this.particalesActive = true, false);
			if (this.delaypart == 0)this.delaypart = 1;
			
			if (this.delay >= 600) {
				Entity spawn = new EntityItem(this.world, this.pos.getX()+.5, this.pos.getY()+.5, this.pos.getZ()+.5, this.output.copy());
				this.output = null;
				if (!world.isRemote) this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(Crystallizer.liquid, Liquids.index(Liquids.None)));
				this.delaypart = 0;
				this.delay = 0;
				this.binderCount = 0;
				this.PatternIndex = -1;
				if (!world.isRemote) this.world.spawnEntity(spawn);
				IDisplay.removeItem(null, this);
				this.change();
			}
			else {this.delay++;}
		} else if (this.particalesActive) IDisplay.particlesToggle(this, this.particalesActive = false, false);
	}
	
	@SuppressWarnings("unchecked")
	public void newItem(EntityItem ent) {
		Liquids Liquid = Liquids.index(((Integer) this.world.getBlockState(this.pos).getValue(Crystallizer.liquid)));		
		ItemStack stack = ent.getItem().copy();
		
		
		if (Liquid == Liquids.Water){
			if (this.PatternIndex == -1) {
				Binders binder = Binders.item(stack.getItem());
				for (CrystallizerPattern p: VoidItems.CRYSLIST) {
					if (binder == p.Binder) {
						this.PatternIndex = VoidItems.CRYSLIST.indexOf(p);
						this.change();
						break;
					}
				}
			}
			if(this.PatternIndex != -1) {
			if (stack.getItem() == Binders.item(VoidItems.CRYSLIST.get(this.PatternIndex).Binder)){
				ent.setDead();
				if (stack.getCount() >= (4 - this.binderCount)){
					stack.setCount(stack.getCount() - (4 - this.binderCount));
					Entity spawn = new EntityItem(this.world, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
					if (!this.world.isRemote) this.world.spawnEntity(spawn);
					this.binderCount = 4;
					this.change();
				} else {
					this.binderCount += stack.getCount();
					this.change();
				}
				if (this.binderCount == 4) {
					if (!this.world.isRemote) {this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(Crystallizer.liquid, Liquids.index(Liquids.VoidLiquid)));
					this.binderCount = 0;
					this.change();
					}
				}
			}
			}
		} else if (Liquid == Liquids.VoidLiquid && this.PatternIndex != -1) {
			for (CrystallizerPattern p : VoidItems.CRYSLIST) {
				if (p.Catalist == stack.getItem() && VoidItems.CRYSLIST.get(this.PatternIndex).Binder == p.Binder) {
					this.PatternIndex = VoidItems.CRYSLIST.indexOf(p);
					ent.setDead();
					if (stack.getCount() > 1) {
						stack.shrink(1);
						Entity spawn = new EntityItem(this.world, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
						if (!this.world.isRemote)this.world.spawnEntity(spawn);
					}
					this.change();
					if (!world.isRemote) this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(Crystallizer.liquid, Liquids.index(Liquids.PureLiquid)));
					break;
				}
			}
		} else if (Liquid == Liquids.PureLiquid && this.display == null && this.PatternIndex != -1) {
			for (CrystallizerPattern p : VoidItems.CRYSLIST) {
				if (p.UpgradeItem == stack.getItem() && VoidItems.CRYSLIST.get(this.PatternIndex).Binder == p.Binder && VoidItems.CRYSLIST.get(this.PatternIndex).Catalist == p.Catalist) {
					ent.setDead();
					if (stack.getCount() > 1) {
						stack.shrink(1);
						Entity spawn = new EntityItem(this.world, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
						if (!this.world.isRemote)this.world.spawnEntity(spawn);
					}
					this.output= new ItemStack(VoidItems.CRYSLIST.get(this.PatternIndex).NewItem);
					setDisplay(stack.copy());
					IDisplay.display(this);
					this.change();
					break;
				}
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
