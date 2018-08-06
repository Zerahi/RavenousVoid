package com.zerahi.ravvoid.utils;

import java.util.List;

import com.zerahi.ravvoid.VoidMod;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class ChunkLoader {
	public static boolean forceLoad(World worldIn ,int x, int z, Ticket ticket, ChunkPos forcedChunk) { 
    int cx = x >> 4; 
    int cz = z >> 4; 
        if (ticket == null) { 
            ForgeChunkManager.setForcedChunkLoadingCallback(VoidMod.instance, new ForgeChunkManager.LoadingCallback() {
				@Override
				public void ticketsLoaded(List<Ticket> tickets, World worldIn) {} 
            });
            try {ticket = ForgeChunkManager.requestTicket(VoidMod.instance, worldIn, ForgeChunkManager.Type.NORMAL);}
            catch (Exception e) {} 
            if (ticket == null) { 
                // Chunk is not loaded and we can't get a ticket. 
                return false; 
            } 
        } 

        ChunkPos pair = new ChunkPos(cx, cz); 
        if (pair.equals(forcedChunk)) { 
            return true; 
        } 
        if (forcedChunk != null) { 
            ForgeChunkManager.unforceChunk(ticket, forcedChunk);
            ForgeChunkManager.releaseTicket(ticket);
        } 
        forcedChunk = pair; 
        ForgeChunkManager.forceChunk(ticket, forcedChunk); 
        return true; 
    } 
	

	public static void unforceLoad(World worldIn ,int x, int z, Ticket ticket, ChunkPos forcedChunk) {
        try {
			ForgeChunkManager.unforceChunk(ticket, forcedChunk);
			ForgeChunkManager.releaseTicket(ticket);
		} catch (Exception e) {}
	}
}
