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

public class TileEntityChaoticNode extends TileEntity implements ITickable {
	private boolean findlocation = false;
	private Ticket ticket;
	private ChunkPos forcedChunk;
	private BlockPos spos;
	private Entity entity;
	private World sworld;
	private int dimensionID;
	private int delay = -1;

	public void teleport(Entity entityIn, int dimensionID)
	{
		this.spos = entityIn.getPosition();
		this.entity = entityIn;
		this.dimensionID = dimensionID;
		if (DimensionManager.getWorld(this.dimensionID) == null) {
		DimensionManager.initDimension(dimensionID);}
		this.sworld = DimensionManager.getWorld(this.dimensionID);
		this.findlocation = true;
	}
	
	public void update() {
		if (this.findlocation && (this.delay == -1 || this.delay >= 60)) {
		 	if(ChunkLoader.forceLoad(this.sworld, this.spos.getX(), this.spos.getZ(), this.ticket, this.forcedChunk)){
		 		if (!this.world.isRemote)Triggers.TEAR.trigger((EntityPlayerMP) this.entity);
				BlockPos tpos = new BlockPos(this.spos.getX(), this.sworld.getHeight(), this.spos.getZ());
				while (this.sworld.isAirBlock(tpos)){tpos = tpos.down();}
				tpos = tpos.up();
				this.entity.setLocationAndAngles(tpos.getX(), tpos.getY(), tpos.getZ(), this.entity.rotationYaw, 0.0F);
				if (this.entity.isEntityAlive()) {
					this.sworld.updateEntityWithOptionalForce(this.entity, false);
	            }
				PlayerListProxy i = new PlayerListProxy(this.entity.getServer());
				i.transferPlayerToDimension((EntityPlayerMP) this.entity, this.dimensionID, null);
				this.entity.setLocationAndAngles(tpos.getX(), tpos.getY(), tpos.getZ(), this.entity.rotationYaw, 0.0F);
				this.entity.motionX = this.entity.motionY = this.entity.motionZ = 0.0D;
				this.delay = -1;
				this.findlocation = false;
				ChunkLoader.unforceLoad(this.sworld, this.spos.getX(), this.spos.getZ(), this.ticket, this.forcedChunk);
			}
		 } else delay++;
	}
}
