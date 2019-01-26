package com.zerahi.ravvoid.utils.proxy;

import com.zerahi.ravvoid.client.RenderChaosBolt;
import com.zerahi.ravvoid.client.book.BookGui;
import com.zerahi.ravvoid.entity.mob.EntityDevourer;
import com.zerahi.ravvoid.entity.mob.EntityForgedChaos;
import com.zerahi.ravvoid.entity.mob.EntityShade;
import com.zerahi.ravvoid.entity.mob.EntityVoidBeast;
import com.zerahi.ravvoid.entity.projectile.EntityChaosBolt;
import com.zerahi.ravvoid.register.RegisterEntityRenders;
import com.zerahi.ravvoid.register.VoidEntities;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		RenderingRegistry.registerEntityRenderingHandler(EntityChaosBolt.class , new IRenderFactory() {
			@Override
			public Render<?> createRenderFor(RenderManager manager) {
				return new RenderChaosBolt(manager, 1);
			}
		});
		VoidEntities.RegisterRenders();
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {	
		super.postInit(event);
	}
	
	@Override
	public void registerItem(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}


	@Override
	public void registerVariant(Item item, int meta, String nameadd, String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName()+nameadd, id));
	}

	public static void openGui() {
		Minecraft.getMinecraft().displayGuiScreen(new BookGui());
	}

	public static void stateMap(Block block) {
		ModelLoader.setCustomStateMapper(block, (new StateMap.Builder()).build());
	}
}
