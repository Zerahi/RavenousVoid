package com.zerahi.ravvoid.blocks.tileentity;

import java.util.Random;

import com.zerahi.ravvoid.Ref;
import com.zerahi.ravvoid.blocks.VoidRift;
import com.zerahi.ravvoid.register.DimensionRegistry;
import com.zerahi.ravvoid.register.Triggers;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.utils.ChunkLoader;
import com.zerahi.ravvoid.utils.interfaces.IDisplay;
import com.zerahi.ravvoid.utils.interfaces.IPower;
import com.zerahi.ravvoid.utils.interfaces.IUpdate;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class TileEntityAltar extends TileEntity implements ITickable, IDisplay, IUpdate  {

	private WorldServer voidWorld;
	
	public boolean portdestroy = false;
		private boolean portcreate = false;
		public ItemStack display = null;
		public EntityItem entity;
		private int counter;
		private int rcounter;
		private int mcounter;
		private int delay;
		private int changeDelay;
		public boolean rift;
		public EnumFacing direction = null;
		private BlockPos OutPortal = BlockPos.ORIGIN;
		private Ticket ticket;
		private ChunkPos forcedChunk;
		private String placer;

		public boolean destroyTE = false;
				
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
			this.rift = compound.getBoolean("rift");
			this.destroyTE = compound.getBoolean("dte");
			if (compound.hasKey("outportal")) {
				int[] cord = compound.getIntArray("outportal");
				this.OutPortal = this.OutPortal.add(cord[0], cord[1], cord[2]);
			} else this.OutPortal = BlockPos.ORIGIN;
			if (compound.hasKey("create")) {
				int[] create = compound.getIntArray("create");;
				if (create[0] == 0) this.portcreate = false; else this.portcreate = true;
				if (create[2] == 0) this.portdestroy = false; else this.portdestroy = true;
				compound.setIntArray("create", create);
			}
			this.direction = EnumFacing.byName(compound.getString("direction"));
			if (compound.hasKey("placer")) this.placer = compound.getString("placer");
			
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound compound) {
			compound = super.writeToNBT(compound);
			if (this.display != null)compound.setTag("display", this.display.serializeNBT());
			compound.setBoolean("rift", this.rift);
			compound.setBoolean("dte", this.destroyTE);
			if (this.OutPortal != BlockPos.ORIGIN ) {
				int[] cord = {this.OutPortal.getX(),this.OutPortal.getY(),this.OutPortal.getZ()};
				compound.setIntArray("outportal", cord);
			}
			if (this.portcreate || this.portdestroy) {
				int[] create = {0,0};
				if (this.portcreate) create[0] = 1; else create[0] = 0;
				if (this.portdestroy) create[1] = 1; else create[1] = 0;
				compound.setIntArray("create", create);
			}
			if (this.direction != null)compound.setString("direction", this.direction.toString());
			if (this.placer != null)compound.setString("placer", this.placer);
			return compound;
		}


		@Override
		public void change() {this.markDirty();}
		//nbt end
		
		@SuppressWarnings("unchecked")
		@Override
		public void update() {			
			if(this.display !=null) {
				
			//Void Rend spawn
			if((ItemStack.areItemsEqual(this.display, new ItemStack(VoidItems.VOIDORB)) ||
					ItemStack.areItemsEqual(new ItemStack(this.display.getItem()), new ItemStack(VoidItems.AWAKENEDVOIDORB)))
					&&  this.world.getBlockState(this.pos.up()).getBlock() == Blocks.AIR && world.getBlockState(pos.add(0, 2, 0)).getBlock()
					== Blocks.AIR) {

				if (powered(1)) {
					if (this.rcounter < 500) {
						this.rcounter++;
						if (this.delay == 0) {this.delay = 1;}
					}
					else {
							this.world.setBlockState(this.pos.add(0, 2, 0), VoidBlocks.VOIDREND.getDefaultState());
							if (!world.isRemote && this.world.getPlayerEntityByName(this.placer) != null) Triggers.VOIDREND.trigger((EntityPlayerMP) this.world.getPlayerEntityByName(this.placer));
						}
				}
			} else {if (this.rcounter != 0) {this.rcounter = 0; this.delay = 0;}}

			//Void Rift spawn
			if(!this.world.isRemote && !this.rift && ItemStack.areItemsEqual(new ItemStack(this.display.getItem()), new ItemStack(VoidItems.AWAKENEDVOIDORB)) && world.getBlockState(pos.add(0, 2, 0)).getBlock() == VoidBlocks.VOIDREND) {

				if (((IPower)this.display.getItem()).getPower(this.display) >= 200 && powered(2)) {

					int count = 0;
					BlockPos spos;
					BlockPos cpos;
					if (this.direction == EnumFacing.NORTH) {
						spos = this.pos.add(-1,0,-3);
						for(int y = 0; y<=2; y++) {
							for(int x = 0; x<=2; x++) {
								cpos = spos.add(x,y,0);
								if (this.world.getBlockState(cpos).getBlock().isReplaceable(world, cpos)) {count++;}
							}
						}
					} else if (this.direction == EnumFacing.SOUTH) {
						
						spos = this.pos.add(-1,0,3);
						for(int y = 0; y<=2; y++) {
							for(int x = 0; x<=2; x++) {
								cpos = spos.add(x,y,0);
								if (this.world.getBlockState(cpos).getBlock().isReplaceable(world, cpos)) {count++;}
							}
						}
					} else if (this.direction == EnumFacing.EAST) {

						spos = this.pos.add(3,0,-1);
						for(int y = 0; y<=2; y++) {
							for(int z = 0; z<=2; z++) {
								cpos = spos.add(0,y,z);
								if (this.world.getBlockState(cpos).getBlock().isReplaceable(world, cpos)) {count++;}
							}
						}
					} else if (this.direction == EnumFacing.WEST) {
						spos = this.pos.add(-3,0,-1);
						for(int y = 0; y<=2; y++) {
							for(int z = 0; z<=2; z++) {
								cpos = spos.add(0,y,z);
								if (this.world.getBlockState(cpos).getBlock().isReplaceable(world, cpos)) {count++;}
							}
						}
					}

					
					if(count == 9) {
						if (this.counter < 1000) {
							this.counter++;
							if (this.delay == 0) {this.delay = 1;}
						} else {
							
							((IPower)this.display.getItem()).powerHelper(this.display, -200);
							this.rift = true;
							this.delay = 0;
							this.counter = 0;
							
							int Spot = 0;
							if (this.direction == EnumFacing.NORTH) {
								spos = this.pos.add(-1,0,-3);
								for(int y = 0; y<=2; y++) {
									for(int x = 0; x<=2; x++) {
										cpos = spos.add(x,y,0);
										Spot++;
										this.world.destroyBlock(cpos, true);
										this.world.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, Spot).withProperty(VoidRift.FACING, this.direction.getOpposite()));
										TileEntityRift te = (TileEntityRift) this.world.getTileEntity(cpos);
										te.Dir = this.direction;
										te.Spot = Spot;
										te.change();
									}
								}
							} else if (this.direction == EnumFacing.SOUTH) {
								
								spos = this.pos.add(1,0,3);
								for(int y = 0; y<=2; y++) {
									for(int x = 0; x>=-2; x--) {
										cpos = spos.add(x,y,0);
										Spot++;
										this.world.destroyBlock(cpos, true);
										this.world.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, Spot).withProperty(VoidRift.FACING, this.direction.getOpposite()));
										TileEntityRift te = (TileEntityRift) this.world.getTileEntity(cpos);
										te.Dir = this.direction;
										te.Spot = Spot;
										te.change();
									}
								}
							} else if (this.direction == EnumFacing.EAST) {
								
								spos = this.pos.add(3,0,-1);
								for(int y = 0; y<=2; y++) {
									for(int z = 0; z<=2; z++) {
										cpos = spos.add(0,y,z);
										Spot++;
										this.world.destroyBlock(cpos, true);
										this.world.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, Spot).withProperty(VoidRift.FACING, this.direction.getOpposite()));
										TileEntityRift te = (TileEntityRift) this.world.getTileEntity(cpos);
										te.Dir = this.direction;
										te.Spot = Spot;
										te.change();
									}
								}
							} else if (this.direction == EnumFacing.WEST) {
								spos = this.pos.add(-3,0,1);
								for(int y = 0; y<=2; y++) {
									for(int z = 0; z>=-2; z--) {
										cpos = spos.add(0,y,z);
										Spot++;
										this.world.destroyBlock(cpos, true);
										this.world.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, Spot).withProperty(VoidRift.FACING, this.direction.getOpposite()));
										TileEntityRift te = (TileEntityRift) this.world.getTileEntity(cpos);
										te.Dir = this.direction;
										te.Spot = Spot;
										te.change();
									}
								}
							}
							this.portcreate = true;
							if (!world.isRemote && this.world.getPlayerEntityByName(this.placer) != null) Triggers.VOIDRIFT.trigger((EntityPlayerMP) this.world.getPlayerEntityByName(this.placer));
							this.change();							
							}							
							count = 0;
						}
					}
				}
			else if (this.rift && ItemStack.areItemsEqual(new ItemStack(this.display.getItem()), new ItemStack(VoidItems.AWAKENEDVOIDORB)) && this.world.getBlockState(this.pos.add(0, 2, 0)).getBlock() == VoidBlocks.VOIDREND) {
				if (this.counter >= 300) {
					if (((IPower)this.display.getItem()).getPower(this.display) - 1>= 0 && powered(2)) {
						((IPower)this.display.getItem()).powerHelper(this.display, -1);
						this.counter = 0;
					} else this.riftBreak();						
				} else this.counter++;
			} else {if (this.counter != 0) {this.counter = 0; this.delay = 0;}}
			

//			//Void Rift Direction check
//			if (this.rift && this.check == false) {
//				BlockPos poss = this.pos;
//				BlockPos spos;
//				BlockPos cpos;
//				int s = 0;
//				if (this.world.getBlockState(poss.add(0,1,-3)).getBlock() == VoidBlocks.VOIDRIFT) {
//					
//					this.direction = EnumFacing.NORTH;
//					spos = this.pos.add(-1,0,-3);
//					for(int y = 0; y<=2; y++) {
//						for(int x = 0; x<=2; x++) {
//							cpos = spos.add(x,y,0);
//							s++;
//							this.world.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, s).withProperty(VoidRift.FACING, this.direction.getOpposite()));
//						}
//					}
//					
//				} else if (this.world.getBlockState(poss.add(0,1,3)).getBlock() == VoidBlocks.VOIDRIFT) {
//					
//					this.direction = EnumFacing.SOUTH;
//					spos = poss.add(1,0,3);
//					for(int y = 0; y<=2; y++) {
//						for(int x = 0; x>=-2; x--) {
//							cpos = spos.add(x,y,0);
//							s++;
//							this.world.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, s).withProperty(VoidRift.FACING, this.direction.getOpposite()));
//						}
//					}
//				} else if (this.world.getBlockState(poss.add(3,1,0)).getBlock() == VoidBlocks.VOIDRIFT) {
//	
//					this.direction = EnumFacing.EAST;
//					spos = poss.add(3,0,-1);
//					for(int y = 0; y<=2; y++) {
//						for(int z = 0; z<=2; z++) {
//							cpos = spos.add(0,y,z);
//							s++;
//							this.world.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, s).withProperty(VoidRift.FACING, this.direction.getOpposite()));
//						}
//					}
//					
//				} else if (this.world.getBlockState(poss.add(-3,1,0)).getBlock() == VoidBlocks.VOIDRIFT) {
//					
//					this.direction = EnumFacing.WEST;
//					spos = poss.add(-3,0,1);
//					for(int y = 0; y<=2; y++) {
//						for(int z = 0; z>=-2; z--) {
//							cpos = spos.add(0,y,z);
//							s++;
//							this.world.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, s).withProperty(VoidRift.FACING, this.direction.getOpposite()));
//						}
//					}
//				}
//				this.check = true;
//			}

			//Mob spawn
			if(this.display !=null && Ref.altarListTier1(this.display, this.world) != null && this.world.getBlockState(this.pos.up()).getBlock() == Blocks.AIR && this.world.getBlockState(this.pos.add(0, 2, 0)).getBlock() == VoidBlocks.VOIDREND) {
					
				if (this.mcounter < 200) {
						this.mcounter++;
						if (this.delay == 0) {this.delay = 1;}
				}	
				else {	
		
						
			            Entity spawn = null;
			            
			            if (ItemStack.areItemsEqual(this.display, new ItemStack(Items.ROTTEN_FLESH)))
							if (!this.world.isRemote && this.world.getPlayerEntityByName(this.placer) != null) Triggers.VOIDBEAST.trigger((EntityPlayerMP) this.world.getPlayerEntityByName(this.placer));
			            if (ItemStack.areItemsEqual(this.display, new ItemStack(Items.BONE)))
							if (!this.world.isRemote && this.world.getPlayerEntityByName(this.placer) != null) Triggers.SHADE.trigger((EntityPlayerMP) this.world.getPlayerEntityByName(this.placer));
			            
		                spawn = Ref.altarListTier1(this.display, this.world);
		                spawn.setWorld(this.world);
		                
		                EntityLiving entityliving = spawn instanceof EntityLiving ? (EntityLiving)spawn : null;
		                spawn.setLocationAndAngles(this.pos.getX()+.5, this.pos.getY()+1f, this.pos.getZ()+.5, this.world.rand.nextFloat() * 360.0F, 0.0F);


		                if(!this.world.isRemote)AnvilChunkLoader.spawnEntity(spawn, this.world);
	                        this.world.playEvent(2004, this.pos, 0);

	                        if (entityliving != null)
	                        {
	                            entityliving.spawnExplosionParticle();
	                        }
					this.delay = 0;
					this.mcounter = 0;
					IDisplay.removeItem(null, this);
				}
					 
			}
			else {
					if (this.mcounter != 0) {this.mcounter = 0; this.delay = 0;}
			}
			
			//Particle Spawn
			if (this.world.isRemote && this.delay < 60 && this.delay != 0) {this.delay++;}
			else if (this.world.isRemote && this.delay == 0) {}
			else if (this.world.isRemote) {
				
				Random rand = new Random();
				double d0 = (double)this.pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.4D;
				double d1 = (double)((float)this.pos.getY() + 1F);
				double d2 = (double)this.pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.4D;
				float f = 15.0F;
				float f1 = f * 0.6F + 0.4F;
		               	float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
		               	float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
		               	this.world.spawnParticle(EnumParticleTypes.DRIP_LAVA, d0, d1, d2, (double)f1, (double)f2, (double)f3, new int[0]);
		               	this.delay = 1;
			}
			
			//Makes sure disp is showing
			if (this.display != null) {
				if (this.entity == null) {this.setDisplayItem(null, null);}
				else if (this.entity.isDead) {this.setDisplayItem(null, null);
				this.change();}
			}
		} else {
			if (this.rift) {this.riftBreak();}
			if (this.delay != 0) {this.delay = 0;}
			if (this.counter != 0) {this.counter = 0;}
			if (this.rcounter != 0) {this.rcounter = 0;}
			if (this.mcounter != 0) {this.mcounter = 0;}
		}
		
		if(!this.world.isRemote && (this.portcreate || this.portdestroy)) {portalChange();}
	}
		
		@SuppressWarnings("unchecked")
		private void portalChange() {
			this.voidWorld = DimensionManager.getWorld(16);
			if (this.changeDelay == -1 || this.changeDelay >= 60) {
				if (ChunkLoader.forceLoad(this.voidWorld, this.pos.getX(), this.pos.getZ(), ticket, forcedChunk)) {
					if (this.portcreate) {
						BlockPos jpos = this.pos.add(0, -this.pos.getY(), 0);
						BlockPos tpos = jpos;
						this.voidWorld = DimensionManager.getWorld(DimensionRegistry.dimensionId);
						@SuppressWarnings("unused")
						boolean chk = true;
						for(int i=256; i > 2; i--) {
							tpos = jpos.add(0, i, 0);
							if (this.voidWorld.isAirBlock(tpos) && this.voidWorld.isBlockFullCube(tpos.add(0, -1, 0))) {
								chk = true;
								for(int x = -1; x<=1; x++) {
									for(int z = -1; z<=1; z++) {
										if(!this.voidWorld.isAirBlock(tpos.add(x, -1, z)) && ((this.voidWorld.isAirBlock(tpos.add(x, 0, z)) ||
												this.voidWorld.getBlockState(tpos.add(x, 0, z)) == VoidBlocks.ASH.getDefaultState() || 
														this.voidWorld.getBlockState(tpos.add(x, 0, z)) == VoidBlocks.VOIDSTONE.getDefaultState()))) {}
										else chk = false;
										}
									}
								if (chk = true) {
									this.OutPortal = tpos;
									break;
								}
								}
							}
						int Spot = 0;
						BlockPos spos = tpos.add(1, 0, 0);
						for (int y = 0; y <= 2; y++) {
							for (int x2 = 0; x2 >= -2; x2--) {
								BlockPos cpos = spos.add(x2, y, 0);
								Spot++;
								this.voidWorld.destroyBlock(cpos, true);
								this.voidWorld.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, Spot).withProperty(VoidRift.FACING, EnumFacing.NORTH));
							}
						}
						this.portcreate = false;
						this.changeDelay = -1;
						this.change();
						ChunkLoader.unforceLoad(world, pos.getX(), pos.getZ(), ticket, forcedChunk);
					}
					if (this.portdestroy) {
						if (this.OutPortal != BlockPos.ORIGIN) {
							BlockPos spos = this.OutPortal.add(-1, 0, 0);
							for (int y = 0; y <= 2; y++) {
								for (int x = 0; x <= 2; x++) {
									BlockPos cpos = spos.add(x, y, 0);
									this.voidWorld.destroyBlock(cpos, false);
								}
							} 
						}
						this.OutPortal = BlockPos.ORIGIN;
						this.portdestroy = false;
						this.changeDelay = -1;
						this.change();
						ChunkLoader.unforceLoad(world, pos.getX(), pos.getZ(), ticket, forcedChunk);
						if (this.destroyTE)this.world.destroyBlock(this.pos, true);
					}
				}
			} else this.changeDelay++;
		}
		
	public void riftBreak() {
			if (this.rift = true) {			
			if (this.direction == EnumFacing.NORTH) {
				BlockPos spos = this.pos.add(-1,0,-3);
				for(int y = 0; y<=2; y++) {
					for(int x = 0; x<=2; x++) {
						BlockPos cpos = spos.add(x,y,0);
						this.world.destroyBlock(cpos, false);
					}
				}
			} else if (this.direction == EnumFacing.SOUTH) {
				
				BlockPos spos = this.pos.add(-1,0,3);
				for(int y = 0; y<=2; y++) {
					for(int x = 0; x<=2; x++) {
						BlockPos cpos = spos.add(x,y,0);
						this.world.destroyBlock(cpos, false);
					}
				}
			} else if (this.direction == EnumFacing.EAST) {
				
				BlockPos spos = this.pos.add(3,0,-1);
				for(int y = 0; y<=2; y++) {
					for(int z = 0; z<=2; z++) {
						BlockPos cpos = spos.add(0,y,z);
						this.world.destroyBlock(cpos, false);
					}
				}
			} else if (this.direction == EnumFacing.WEST) {
				BlockPos spos = this.pos.add(-3,0,-1);
				for(int y = 0; y<=2; y++) {
					for(int z = 0; z<=2; z++) {
						BlockPos cpos = spos.add(0,y,z);
						this.world.destroyBlock(cpos, false);
					}
				}
			}
			this.portdestroy = true;
			this.rift = false;
			this.change();
			}
		}

	public boolean powered(Integer tier) {
		Block source = null;
		BlockPos spos = this.pos.add(-5, -5, -5);
		int x;
		int y;
		int z;
		int cnt = 0;
		int ammount = 4;

		if (tier == 1) {source = VoidBlocks.SHARDBLOCK;}
		if (tier == 2) {source = VoidBlocks.PURESHARDBLOCK;}
		
		for(x = 0; x<=11; x++) {
			for(y = 0; y<=11; y++) {
				for(z = 0; z<=11; z++) {
					if (this.world.getBlockState(spos.add(x, y, z)).getBlock() == source) {cnt++;}
					else if (tier == 1 && this.world.getBlockState(spos.add(x, y, z)).getBlock() == VoidBlocks.PURESHARDBLOCK) {cnt++;}
					if (cnt >= ammount) {break;}
				}
			}
		}
		
		if (cnt >= ammount) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setDisplayItem(EntityPlayer playerIn, ItemStack heldItem) {
		if (this.display !=null) {
			if (playerIn != null && ItemStack.areItemsEqual(new ItemStack(heldItem.getItem()), new ItemStack(VoidItems.AWAKENEDVOIDORB)))this.direction = playerIn.getHorizontalFacing();
			if (playerIn != null) this.placer = playerIn.getDisplayNameString();
			IDisplay.display(this);
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
}
