package com.zerahi.ravvoid.blocks.tileentity;


import com.zerahi.ravvoid.register.VoidEntities;
import com.zerahi.ravvoid.utils.interfaces.IUpdate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

public class TileEntityVoidNode extends TileEntity implements ITickable, IUpdate{
	private int spawned;
	private int mode;
	private int delay;
	private boolean boss;
	private long bossDelay;
	private Entity bossEnt;
	
	public TileEntityVoidNode() {
		this.spawned = -1;
		this.mode = 0;
		this.delay = 0;
		this.boss = false;
		this.bossDelay = -1;
	}
	
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
				if (compound.hasKey("spawned")) this.spawned = compound.getInteger("spawned");
				if (compound.hasKey("mode")) this.mode = compound.getInteger("mode");
				if (compound.hasKey("bossdelay")) this.bossDelay = compound.getLong("bossdelay");
				if (compound.hasKey("boss")) this.boss = compound.getBoolean("boss");
			}

			@Override
			public NBTTagCompound writeToNBT(NBTTagCompound compound) {
				compound = super.writeToNBT(compound);
				if (this.spawned != -1) compound.setInteger("spawned", this.spawned);
				if (this.mode != -1) compound.setInteger("mode", this.mode);
				compound.setLong("bossdelay", this.bossDelay);
				compound.setBoolean("boss", this.boss);
				return compound;
			}
			
			@Override
			public void change() {this.markDirty();}
			

			public void shiftSpawned(EntityPlayer playerIn) {
				Entity ent = VoidEntities.Mobs.get(this.spawned).NewMob(this.world);
				if (ent == null) {
					this.spawned = 0;
					ent = VoidEntities.Mobs.get(this.spawned).NewMob(this.world);;
				} else this.spawned++;
				if(!this.world.isRemote) playerIn.sendMessage(new TextComponentString(String.valueOf(this.spawned) + " " + ent.getName()));
				this.change();
			}
						
			public void changeMode(EntityPlayer playerIn) {
				if (this.mode == 0) this.mode = 1; else mode = 0;
				//TODO if mode is 1 may want to make block indestrucatable
				if(!this.world.isRemote) playerIn.sendMessage(new TextComponentString(String.valueOf(this.mode)));
				this.change();
			}

			@Override
			public void update() {		
			if (this.mode == 0) {
					if (this.delay >= 500 && this.spawned > -1) {		
						if(nearby(10) == true && !this.world.isRemote && 
								this.world.getClosestPlayer(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 30, false) != null) {
				            Entity spawn = VoidEntities.Mobs.get(this.spawned).NewMob(this.world);
			                if (spawn != null) {
								spawn.setWorld(this.world);
								EntityLiving entityliving = spawn instanceof EntityLiving ? (EntityLiving) spawn
										: null;
								spawn.setLocationAndAngles(this.pos.getX() + .5, this.pos.getY() + 1f,
										this.pos.getZ() + .5, this.world.rand.nextFloat() * 360.0F, 0.0F);
								AnvilChunkLoader.spawnEntity(spawn, this.world);
								this.world.playEvent(2004, this.pos, 0);
								if (entityliving != null) {
									entityliving.spawnExplosionParticle();
								}
							}
						}
						this.delay = 0;
					} else if (this.delay < 500 && this.spawned > -1) this.delay++;
				} else {
					if (this.delay >= 1000 && this.spawned > -1 ) {
 						if (this.boss == false) {
							if(nearby(30) && !this.world.isRemote) {
					            this.bossEnt = VoidEntities.Mobs.get(this.spawned).NewMob(this.world);
				                if (this.bossEnt != null) {
									this.bossEnt.setWorld(this.world);
									EntityLiving entityliving = this.bossEnt instanceof EntityLiving ? (EntityLiving) this.bossEnt
											: null;
									this.bossEnt.setLocationAndAngles(this.pos.getX() + .5, this.pos.getY() + 1f,
											this.pos.getZ() + .5, this.world.rand.nextFloat() * 360.0F, 0.0F);
									AnvilChunkLoader.spawnEntity(this.bossEnt, this.world);
									this.world.playEvent(2004, this.pos, 0);
									if (entityliving != null) {
										entityliving.spawnExplosionParticle();
									}
									this.boss = true;
									this.change();
								}
							}
							this.delay = 0;
						} else {
 							if(this.bossEnt != null) {
								if(nearby(30) && (this.bossDelay > -1)) {
									if (this.world.getTotalWorldTime() - this.bossDelay >= 10000) {
										this.boss = false;
										this.bossDelay = -1;
										this.delay = 999;
										this.change();
									}
								} else if (this.world.getClosestPlayer(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 50, false) != null) {
									this.bossDelay = this.world.getTotalWorldTime();
									this.delay = 0;
									this.change();
								} else {
									this.bossEnt = null;
									this.boss = false;
									this.delay = 999;
									this.change();
								}
							} else if (nearby(30)) {
								this.boss = false;
								this.change();
								this.delay = 999;
							}
						}
					} else if (this.delay < 2000 && this.spawned > -1) this.delay++;
				}
			}

			@SuppressWarnings("unchecked")
			private boolean nearby(int range) {
				AxisAlignedBB dist = new AxisAlignedBB(pos.getX() - range,pos.getY() - range,pos.getZ() - range,pos.getX() + range,pos.getY() + range,pos.getZ() + range);
				if (mode == 0) {
					if (world.getEntitiesWithinAABB(VoidEntities.Mobs.get(this.spawned).MobClass, dist).size() <4) {
						return true;
					}
				} else if (mode == 1) {
 					if (world.getEntitiesWithinAABB(VoidEntities.Mobs.get(this.spawned).MobClass, dist).size() == 0) {
						return true;
					}
				}
				return false;
			}
}
