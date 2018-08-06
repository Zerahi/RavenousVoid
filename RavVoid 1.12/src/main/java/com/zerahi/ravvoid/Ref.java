package com.zerahi.ravvoid;

import com.zerahi.ravvoid.entity.mob.EntityShade;
import com.zerahi.ravvoid.entity.mob.EntityVoidBeast;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class Ref {
	public static final String MODID = "rv";
	public static final String NAME = "Ravenous Void";
	public static final String Version = "1.12.2";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	
	public static final String CLIENT_PROXY_CLASS = "com.zerahi.ravvoid.utils.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.zerahi.ravvoid.utils.proxy.CommonProxy";
	
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("rv");
	
	public static Entity altarListTier1(ItemStack item, World world) {
			
        if (ItemStack.areItemsEqual(item, new ItemStack(Items.ROTTEN_FLESH))){return new EntityVoidBeast(world);}
        else if (ItemStack.areItemsEqual(item, new ItemStack(Items.BONE)))  {return new EntityShade(world);}
        else {
        	return null;
        }
	}
	

	public static final ResourceLocation VOIDBEASTLOOT = register("voidbeast");
    public static final ResourceLocation SHADELOOT = register("shade");
	public static final ResourceLocation FORGEDCHAOSLOOT = register("forgedchaos");
	public static final ResourceLocation DEVOURERLOOT = register("devourer");
	
	private static ResourceLocation register(String id)
    {
        return LootTableList.register(new ResourceLocation(MODID, id));
    }
}
