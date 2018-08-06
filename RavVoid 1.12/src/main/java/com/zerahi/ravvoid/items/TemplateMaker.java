package com.zerahi.ravvoid.items;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Nullable;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TemplateMaker extends Item implements IRegisterModels {
	Template temp = new Template();
	String tempname = "newtemplate";
	NBTTagCompound nbt = new NBTTagCompound();
	PlacementSettings place;
	
	public TemplateMaker(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(1);
		
		VoidItems.ITEMS.add(this);
	}
	
	public void registerModels() 
	{
		VoidMod.proxy.registerItem(this, 0, "inventory");
	}
	    
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
    	
    	return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	if (stack.hasTagCompound()) {
    	    tooltip.add("Click: " + stack.getTagCompound().getInteger("click"));
    	    tooltip.add("Cordinates: " + stack.getTagCompound().getIntArray("pos"));
    	    tooltip.add("Status: " + stack.getTagCompound().getString("status"));
    	}
    }

    
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack item = player.getHeldItemMainhand();
		if (!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
			item.getTagCompound().setInteger("click", 1);
			int[] nArray = new int[6];
			nArray = new int[]{0, 0, 0, 0, 0, 0};
			int[] oArray = new int[3];
			oArray = new int[]{0, 0, 0};
			item.getTagCompound().setIntArray("pos", nArray);
			item.getTagCompound().setIntArray("opos", oArray);
			item.getTagCompound().setString("status", "First Click");
			if(!worldIn.isRemote) player.sendMessage(new TextComponentString("First Click"));
			this.place = new PlacementSettings();
			this.temp.setAuthor("Zerahi");
		}
	
	

		int[] nArray = item.getTagCompound().getIntArray("pos");
		int[] oArray = item.getTagCompound().getIntArray("opos");
			int x = (item.getTagCompound().getInteger("click"));
			if (x == 1) {
					nArray[x-1] = pos.getX();
					nArray[x] = pos.getY();
					nArray[x+1] = pos.getZ();
					oArray[x-1] = pos.getX();
					oArray[x] = pos.getY();
					oArray[x+1] = pos.getZ();
					item.getTagCompound().setIntArray("pos", nArray);
					item.getTagCompound().setIntArray("opos", oArray);
		    		item.getTagCompound().setInteger("click", x + 1);
	    		return EnumActionResult.SUCCESS;
			} else if (x == 2) {
					nArray[x+1] = nArray[x-2] - pos.getX();
					nArray[x+2] = nArray[x-1] - pos.getY();
					nArray[x+3] = nArray[x] - pos.getZ();
					for (int i = 0; i < 3; i++) {
						nArray[i] = 0;
						if (nArray[3+i] < 0) {
							nArray[3+i] = nArray[3+i] * -1;
							}
						else {oArray[i] = oArray[i] - nArray[3+i];}
						nArray[3+i]++;
					}
					item.getTagCompound().setIntArray("pos", nArray);
					item.getTagCompound().setIntArray("opos", oArray);
					item.getTagCompound().setString("status", "Second Click");
					if(!worldIn.isRemote) player.sendMessage(new TextComponentString("Second Click"));
		    		item.getTagCompound().setInteger("click", x + 1);
	    		return EnumActionResult.SUCCESS;
			} else if (x == 3) {
		    		BlockPos pos1 = new BlockPos(oArray[0], oArray[1], oArray[2]);
		    		BlockPos pos2 = new BlockPos(nArray[3], nArray[4], nArray[5]);
		    		this.temp = new Template();
		    		this.temp.setAuthor(player.getDisplayNameString());
		    		this.temp.takeBlocksFromWorld(worldIn, pos1, pos2, false, null);
		    		this.temp.writeToNBT(this.nbt);
		    		this.place.setRotation(Rotation.NONE);
		    		item.getTagCompound().setString("status", "Copied");
					if(!worldIn.isRemote) player.sendMessage(new TextComponentString("Copied"));
		    		item.getTagCompound().setInteger("click", x + 1);
		    		return EnumActionResult.SUCCESS;
			} else if (x == 4 && !player.isSneaking()){
				this.temp.addBlocksToWorld(worldIn, pos.up(), this.place);
				item.getTagCompound().setString("status", "Created");
				return EnumActionResult.SUCCESS;

			} else if (x == 4 && player.isSneaking() && !worldIn.isRemote){
				try {
					File directory = new File(".\\structures");
					if (! directory.exists()){
				        directory.mkdir();}
					OutputStream stream = new FileOutputStream(".\\structures\\output.nbt");
					CompressedStreamTools.writeCompressed(this.nbt, stream);
					stream.close();
				} catch (IOException e) {
				}
				if(!worldIn.isRemote) player.sendMessage(new TextComponentString("Saved"));
				return EnumActionResult.SUCCESS;
			} else {return EnumActionResult.FAIL;}
	}
}
