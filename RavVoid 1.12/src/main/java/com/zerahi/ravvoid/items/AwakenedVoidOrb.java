package com.zerahi.ravvoid.items;

import java.util.List;

import javax.annotation.Nullable;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.register.Triggers;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IPower;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;
import com.zerahi.ravvoid.utils.interfaces.IUseItems;
import com.zerahi.ravvoid.utils.interfaces.IVoidOrbs;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AwakenedVoidOrb extends Item implements IRegisterModels, IUseItems, IPower, IVoidOrbs {
	public int maxPower;
	
	public AwakenedVoidOrb(String name, int maxPowerIn) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(VoidTabs.tabVoid);
		setMaxStackSize(1);
        this.setMaxDamage(0);
		this.maxPower = maxPowerIn;
		
		VoidItems.ITEMS.add(this);
	}
	
	public int getMaxPower() {
		return this.maxPower;
	}
	
	public void registerModels() 
	{
		VoidMod.proxy.registerItem(this, 0, "inventory");
	}
	
	public Integer DELAY =0;
    
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }
    
    
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
    	
    	 return false;
    }
    
    public double getLight(World world, BlockPos pos) {
		  return world.getLight(pos, true) * .625;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	if (stack.hasTagCompound()) {
    	    tooltip.add("Power: " + this.getPower(stack));
    	}
    }
    
    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
    	if (!stack.hasTagCompound()) {
    		return 1;
    	}
    	else {
        return ((float)this.maxPower - (float)this.getPower(stack))/(float)this.maxPower;
    	}
    }
    
    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
public void onUpdate(ItemStack item, World world, Entity player, int itemSlot, boolean isSelected) {
	if (!item.hasTagCompound()) {
		item.setTagCompound(new NBTTagCompound());
		    this.setPower(item, 0);
		    this.setActive(item, false);
	}
	
	if (this.DELAY >= 100 && this.getPower(item) < this.maxPower) {

		if (this.getLight(world, player.getPosition()) <= 5) this.powerHelper(item, (int) (5-this.getLight(world, player.getPosition())));

		if (this.getActive(item) && itemSlot <= 10 && this.getPower(item) - 3 > 0) {

			this.powerHelper(item, -3);
			((EntityLivingBase) player).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400, -1));
		}
		else {
			this.setActive(item, false);
			((EntityLivingBase) player).removePotionEffect(MobEffects.NIGHT_VISION);
		}
		
		this.DELAY = 1;
		((EntityPlayer)player).inventory.markDirty();
	}
else if (this.DELAY < 100) this.DELAY++;
}


public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
{
	
	if (this.getActive(item)) {
		((EntityLivingBase) player).removePotionEffect(MobEffects.NIGHT_VISION);
		this.setActive(item, false);
	}
	return true;
}

public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
{
	ItemStack item = player.getHeldItem(hand);
	if (hand == EnumHand.MAIN_HAND || (hand == EnumHand.OFF_HAND && !(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IUseItems))) { 
		if (this.getPower(item) >= 100 && !this.getActive(item)) {
			if (!world.isRemote) Triggers.SIGHT.trigger((EntityPlayerMP)player);
			this.setActive(item, true);
			this.powerHelper(item, -100);
			((EntityLivingBase) player).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400, -1));
			((EntityPlayer)player).inventory.markDirty();
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
		} else if (item.getTagCompound().getBoolean("active")) {
			item.getTagCompound().setBoolean("active", false);
			player.removePotionEffect(MobEffects.NIGHT_VISION);
			((EntityPlayer)player).inventory.markDirty();
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
		}
	}
	return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
}
}
