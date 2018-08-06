package com.zerahi.ravvoid.items;

import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.register.BookInit;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;
import com.zerahi.ravvoid.utils.interfaces.IUseItems;
import com.zerahi.ravvoid.utils.proxy.ClientProxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class VoidJournal extends Item implements IRegisterModels, IUseItems {
	
	public VoidJournal(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(VoidTabs.tabVoid);
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
    /*@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	if (stack.hasTagCompound()) {
    	    tooltip.add("Power: " + this.getPower(stack));
    	}
    }*/

	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack item = player.getHeldItem(hand);
		if (hand == EnumHand.MAIN_HAND || (hand == EnumHand.OFF_HAND && !(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IUseItems))) { 
			if (!world.isRemote)BookInit.checkAdvancements(((EntityPlayerMP)player));
						
			if (world.isRemote) {
				ClientProxy.openGui();
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
	}
}
