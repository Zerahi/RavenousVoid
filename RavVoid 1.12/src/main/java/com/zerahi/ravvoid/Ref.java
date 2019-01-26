package com.zerahi.ravvoid;


import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class Ref {
	public static final String MODID = "rv";
	public static final String NAME = "Ravenous Void";
	public static final String Version = "0.0.4";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	
	public static final String CLIENT_PROXY_CLASS = "com.zerahi.ravvoid.utils.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.zerahi.ravvoid.utils.proxy.CommonProxy";
	
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("rv");
}