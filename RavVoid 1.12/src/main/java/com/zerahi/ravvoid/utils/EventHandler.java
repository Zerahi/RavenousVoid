package com.zerahi.ravvoid.utils;

import com.zerahi.ravvoid.Ref;
import com.zerahi.ravvoid.register.Triggers;
import com.zerahi.ravvoid.register.VoidItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class EventHandler {
	@SubscribeEvent
	public static void pickupItem(EntityItemPickupEvent event) {
		
        if (ItemStack.areItemsEqual(event.getItem().getItem(),new ItemStack(VoidItems.VOIDJOURNAL))) {
        	Triggers.RAVVOID.trigger((EntityPlayerMP) event.getEntityPlayer());
		} else if (ItemStack.areItemsEqual(event.getItem().getItem(),new ItemStack(VoidItems.VOIDFRAGMENTS))) {
			Triggers.VOIDFRAGMENTS.trigger((EntityPlayerMP) event.getEntityPlayer());
			ResourceLocation[] rec = new ResourceLocation[1];
			rec[0] = new ResourceLocation(Ref.MODID, "fragmentpile");
			event.getEntityPlayer().unlockRecipes(rec);
		} else if (ItemStack.areItemsEqual(event.getItem().getItem(),new ItemStack(VoidItems.VOIDORB))) {
			Triggers.DARKNESS.trigger((EntityPlayerMP) event.getEntityPlayer());
			Triggers.VOIDORB.trigger((EntityPlayerMP) event.getEntityPlayer());
			ResourceLocation[] rec = new ResourceLocation[1];
			rec[0] = new ResourceLocation(Ref.MODID, "voidaltar");
			event.getEntityPlayer().unlockRecipes(rec);
		} else if (ItemStack.areItemsEqual(event.getItem().getItem(),new ItemStack(VoidItems.BEASTHIDE))) {
			ResourceLocation[] rec = new ResourceLocation[4];
			rec[0] = new ResourceLocation(Ref.MODID, "hidehelm");
			rec[1] = new ResourceLocation(Ref.MODID, "hidechest");
			rec[2] = new ResourceLocation(Ref.MODID, "hidelegs");
			rec[3] = new ResourceLocation(Ref.MODID, "hidefeet");
			event.getEntityPlayer().unlockRecipes(rec);
		} else if (ItemStack.areItemsEqual(event.getItem().getItem(),new ItemStack(VoidItems.VOIDESSENCE))) {
			ResourceLocation[] rec = new ResourceLocation[1];
			rec[0] = new ResourceLocation(Ref.MODID, "crystallizer");
			event.getEntityPlayer().unlockRecipes(rec);
		}else if (ItemStack.areItemsEqual(event.getItem().getItem(),new ItemStack(VoidItems.PUREVOIDSHARD))) {
			Triggers.PUREVOIDSHARD.trigger((EntityPlayerMP) event.getEntityPlayer());
			ResourceLocation[] rec = new ResourceLocation[1];
			rec[0] = new ResourceLocation(Ref.MODID, "conduit");
			event.getEntityPlayer().unlockRecipes(rec);
		}else if (ItemStack.areItemsEqual(event.getItem().getItem(),new ItemStack(VoidItems.AWAKENEDVOIDORB))) {
			Triggers.AWAKENEDVOIDORB.trigger((EntityPlayerMP) event.getEntityPlayer());
		}
	 }
	
    @SubscribeEvent
    public static void onPlayerJoin(PlayerLoggedInEvent event) {
        NBTTagCompound data = event.player.getEntityData();
        NBTTagCompound persistent;
        if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            data.setTag(EntityPlayer.PERSISTED_NBT_TAG, (persistent = new NBTTagCompound()));
        } else {
            persistent = data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        }

        if (!persistent.hasKey("ravvoid.firstjoined") && !event.player.getEntityWorld().isRemote) {
            persistent.setBoolean("ravvoid.firstjoined", true);
            event.player.inventory.addItemStackToInventory(new ItemStack(VoidItems.VOIDJOURNAL));
			ResourceLocation[] rec = new ResourceLocation[1];
			rec[0] = new ResourceLocation(Ref.MODID, "voidjournal");
			event.player.unlockRecipes(rec);
        }
    }
}
