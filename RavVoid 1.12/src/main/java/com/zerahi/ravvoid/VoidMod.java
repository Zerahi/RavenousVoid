package com.zerahi.ravvoid;

import com.zerahi.ravvoid.register.Register;
import com.zerahi.ravvoid.utils.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Ref.MODID, name = Ref.NAME, version = Ref.Version, acceptedMinecraftVersions = Ref.ACCEPTED_VERSIONS)
public class VoidMod {
	
	@Instance
	public static VoidMod instance;
	
	
	@SidedProxy(clientSide = Ref.CLIENT_PROXY_CLASS, serverSide = Ref.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Register.preInit();
		proxy.preInit(event);
	}
	
	@EventHandler
		public void init(FMLInitializationEvent event) {
		Register.Init();
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		proxy.postInit(event);
	}
}