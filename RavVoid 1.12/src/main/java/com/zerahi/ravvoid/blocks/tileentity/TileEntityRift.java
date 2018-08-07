package com.zerahi.ravvoid.blocks.tileentity;

import com.zerahi.ravvoid.blocks.VoidRift;
import com.zerahi.ravvoid.register.VoidBlocks;
import com.zerahi.ravvoid.utils.interfaces.IUpdate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityRift extends TileEntity implements IUpdate {
		int Spot;
		EnumFacing Dir;
		
		public TileEntityRift() {}

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
		
		@SuppressWarnings("unchecked")
		@Override
		public void readFromNBT(NBTTagCompound compound) {
			super.readFromNBT(compound);
			if (compound.hasKey("spot")) this.Spot = compound.getInteger("spot");
			if (compound.hasKey("dir")) this.Dir = EnumFacing.byName(compound.getString("dir"));
			if (this.Spot != 0 && this.Dir != null)this.world.setBlockState(this.pos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, this.Spot).withProperty(VoidRift.FACING, this.Dir.getOpposite()));
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound compound) {
			compound = super.writeToNBT(compound);
			if (this.Spot != 0)compound.setInteger("spot", this.Spot);
			if (this.Dir != null)compound.setString("dir", this.Dir.toString());
			return compound;
		}
		
		@Override
		public void change() {this.markDirty();}
		//nbt end
}
