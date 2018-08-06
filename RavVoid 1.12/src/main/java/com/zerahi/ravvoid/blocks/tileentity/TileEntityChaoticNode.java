package com.zerahi.ravvoid.blocks.tileentity;

import com.zerahi.ravvoid.utils.ChunkLoader;
import com.zerahi.ravvoid.utils.PlayerListProxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class TileEntityChaoticNode extends TileEntity implements ITickable {
	private boolean findlocation = false;
	private Ticket ticket;
	private ChunkPos forcedChunk;
	private BlockPos pos;
	private Entity entity;
	private World world;
	private int dimensionID;
	private int delay = -1;

	public void teleport(Entity entityIn, World worldIn, int dimensionID)
	{
		this.pos = entityIn.getPosition();
		this.entity = entityIn;
		this.world = worldIn;
		this.dimensionID = dimensionID;
		this.findlocation = true;
	}
	
	public void update() {
		if (this.findlocation && (this.delay == -1 || this.delay >= 60)) {
		 	if(ChunkLoader.forceLoad(this.world, this.pos.getX(), this.pos.getZ(), this.ticket, this.forcedChunk)){
				BlockPos tpos = new BlockPos(this.pos.getX(), this.world.getHeight(), this.pos.getZ());
				while (this.world.isAirBlock(tpos)){tpos = tpos.down();}
				tpos = tpos.up();
				this.entity.setLocationAndAngles(tpos.getX(), tpos.getY(), tpos.getZ(), this.entity.rotationYaw, 0.0F);
				if (this.entity.isEntityAlive()) {
					this.world.updateEntityWithOptionalForce(this.entity, false);
	            }
				PlayerListProxy i = new PlayerListProxy(this.entity.getServer());
				i.transferPlayerToDimension((EntityPlayerMP) this.entity, this.dimensionID, null);
				this.entity.setLocationAndAngles(tpos.getX(), tpos.getY(), tpos.getZ(), this.entity.rotationYaw, 0.0F);
				this.entity.motionX = this.entity.motionY = this.entity.motionZ = 0.0D;
				this.delay = -1;
				this.findlocation = false;
				ChunkLoader.unforceLoad(this.world, this.pos.getX(), this.pos.getZ(), this.ticket, this.forcedChunk);
			}
		 } else delay++;
	}
}
