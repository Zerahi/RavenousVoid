package com.zerahi.ravvoid.blocks.tileentity;

import com.zerahi.ravvoid.register.Triggers;
import com.zerahi.ravvoid.utils.ChunkLoader;
import com.zerahi.ravvoid.utils.PlayerListProxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class TileEntityVoidRift extends TileEntity implements ITickable {
	private boolean findlocation = false;
	private Ticket ticket;
	private ChunkPos forcedChunk;
	private BlockPos pos;
	private Entity entity;
	private World sworld;
	private int dimensionID;
	private int delay = -1;
	private BlockPos dpos;

	public void teleport(Entity entityIn, BlockPos dposIn, int dimensionID)
	{
		this.pos = entityIn.getPosition();
		this.dpos = dposIn;
		this.dimensionID = dimensionID;
		if (DimensionManager.getWorld(this.dimensionID) == null) {
		DimensionManager.initDimension(dimensionID);
		}
		this.sworld = DimensionManager.getWorld(this.dimensionID);
		this.entity = entityIn;
		this.findlocation = true;
	}
	
	public void update() {
		if (this.findlocation && (this.delay == -1 || this.delay >= 60)) {
		 	if(ChunkLoader.forceLoad(this.sworld, this.dpos.getX(), this.dpos.getZ(), this.ticket, this.forcedChunk)){
		 		if (!this.world.isRemote)Triggers.THROUGH.trigger((EntityPlayerMP) this.entity);
				BlockPos tpos = new BlockPos(this.dpos.getX(), this.sworld.getHeight(), this.dpos.getZ());
				while (this.sworld.isAirBlock(tpos)){tpos = tpos.down();}
				tpos = tpos.up();
				this.entity.setLocationAndAngles(((double)tpos.getX())+.5 , tpos.getY(), ((double)tpos.getZ())+.5, this.entity.rotationYaw, 0.0F);
				if (this.entity.isEntityAlive()) {
					this.sworld.updateEntityWithOptionalForce(this.entity, false);
	            }
				PlayerListProxy i = new PlayerListProxy(this.entity.getServer());
				i.transferPlayerToDimension((EntityPlayerMP) this.entity, this.dimensionID, null);
				this.entity.setLocationAndAngles(((double)tpos.getX())+.5 , tpos.getY(), ((double)tpos.getZ())+.5, this.entity.rotationYaw, 0.0F);
				this.entity.motionX = this.entity.motionY = this.entity.motionZ = 0.0D;
				this.delay = -1;
				this.findlocation = false;
				ChunkLoader.unforceLoad(this.sworld, this.pos.getX(), this.pos.getZ(), this.ticket, this.forcedChunk);
			}
		 } else delay++;
	}
}
