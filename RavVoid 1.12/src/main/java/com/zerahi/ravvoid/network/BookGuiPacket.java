package com.zerahi.ravvoid.network;

import com.zerahi.ravvoid.register.BookInit;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BookGuiPacket implements IMessage {
	public BookGuiPacket() {}
	
	private NBTTagCompound data;
    public BookGuiPacket(NBTTagCompound data){
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

    public static class Handler implements IMessageHandler<BookGuiPacket, IMessage>{

		@Override
        @SideOnly(Side.CLIENT)
		public IMessage onMessage(BookGuiPacket message, MessageContext ctx) {
			BookInit.checkAdvancements(message.data);
			return null;
		}
    }
}