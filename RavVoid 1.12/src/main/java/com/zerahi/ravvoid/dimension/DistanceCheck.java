package com.zerahi.ravvoid.dimension;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;

public class DistanceCheck {
	private static File file = new File(DimensionManager.getCurrentSaveRootDirectory(), "\\Void\\structures.dat");
	private static NBTTagCompound nbt;
	private static int minDistance = 50;
	private static List<info> infoList = new ArrayList<info> ();
	
	
	public static void read() {
		try {
			FileInputStream inst = new FileInputStream(file);
			DataInputStream instream = new DataInputStream(inst);
			nbt = CompressedStreamTools.readCompressed(instream);
			instream.close();
			inst.close();

			int index = nbt.getInteger("index");
			for (int i = 0; i < index; i++) {
				String tmpTitle = nbt.getString("title"+ String.valueOf(i));
				int[] ary = nbt.getIntArray("pos"+ String.valueOf(i));
				int tmpdst = nbt.getInteger("distance"+ String.valueOf(i));
				infoList.add(new info(tmpTitle, new BlockPos(ary[0], 0, ary[1]), tmpdst));
			}
		} catch (IOException e) {
		}
	}
	
	public static void write() {
		int index = 0;
		for (info inf : infoList) {
			nbt.setString("title" + String.valueOf(index), inf.title);
			int[] ary = new int[2];
			ary[0] = inf.pos.getX();
			ary[1] = inf.pos.getZ();
			nbt.setIntArray("pos" + String.valueOf(index), ary);
			nbt.setInteger("distance" + String.valueOf(index), inf.distance);
			index++;
		}
		nbt.setInteger("index", index);
		try {
			OutputStream stream = new FileOutputStream(file);
			CompressedStreamTools.writeCompressed(nbt, stream);
			stream.close();
		} catch (IOException e) {
		}
	}
	
	public static Boolean check(String title, BlockPos pos, int distance) {
		nbt = new NBTTagCompound();
		if (!file.exists()) {
			infoList.add(new info(title, pos, distance));
			write();
			return true;
		} else {
			if (infoList.size() == 0) {
				read();
			}
			for (info inf : infoList) {
				BlockPos position1 = new BlockPos(pos.getX(), 0, pos.getZ());
				BlockPos position2 = new BlockPos(inf.pos.getX(), 0, inf.pos.getZ());
				double tmpdist = position1.getDistance(position2.getX(), position2.getY(), position2.getZ());
				if (tmpdist < minDistance) return false;
				if (title == inf.title) {
					if (tmpdist < distance) return false;
				} else {
					if (tmpdist < inf.distance) return false;
				}
			}
			infoList.add(new info(title, pos, distance));
			write();
			return true;
		}
	}
}

class info {
	public String title;
	public BlockPos pos;
	public int distance;
	
	public info(String titleIn, BlockPos posIn, int distanceIn) {
		this.title = titleIn;
		this.pos = posIn;
		this.distance = distanceIn;
	}
	
}
