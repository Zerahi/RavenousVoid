package com.zerahi.ravvoid.network;

import com.zerahi.ravvoid.utils.interfaces.IDisplay;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ParticlePacket implements IMessage {
	public ParticlePacket() {}
	
	private NBTTagCompound data;
    public ParticlePacket(NBTTagCompound data){
        this.data = data;
    }
    
    @Override
    public void fromBytes(ByteBuf buf){
        PacketBuffer buffer = new PacketBuffer(buf);
        try{
            this.data = buffer.readCompoundTag();
        }
        catch(Exception e){}
    }

    @Override
    public void toBytes(ByteBuf buf){
        PacketBuffer buffer = new PacketBuffer(buf);

        buffer.writeCompoundTag(this.data);
    }

    public static class Handler implements IMessageHandler<ParticlePacket, IMessage>{

		@Override
        @SideOnly(Side.CLIENT)
		public IMessage onMessage(ParticlePacket message, MessageContext ctx) {
			
			int[] ar = message.data.getIntArray("pos");
			BlockPos pos = new BlockPos(ar[0], ar[1], ar[2]);
			TileEntity te = Minecraft.getMinecraft().world.getTileEntity(pos);
			boolean state = message.data.getBoolean("state");
			IDisplay.particlesToggle(te, state, true);
			return null;
		}
    }
}