package com.zerahi.ravvoid.register;

import com.zerahi.ravvoid.Ref;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityAltar;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityChaoticNode;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityConduit;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityCrystallizer;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityPile;
import com.zerahi.ravvoid.blocks.tileentity.TileEntityVoidNode;
import com.zerahi.ravvoid.network.BookGuiPacket;
import com.zerahi.ravvoid.utils.EventHandler;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Ref.MODID)
public class Register {
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(VoidItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(VoidBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void RegisterModel(ModelRegistryEvent event) {
		for(Item item : VoidItems.ITEMS) {
			if(item instanceof IRegisterModels) {
			((IRegisterModels)item).registerModels();
			
			}
		}
		for(Block block : VoidBlocks.BLOCKS) {
			if(block instanceof IRegisterModels) {
				((IRegisterModels)block).registerModels();
			}
		}
	}
	
	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event){
		
		event.getRegistry().register(VoidBiomeRegistery.VOIDBIOME);
		BiomeDictionary.addTypes(VoidBiomeRegistery.VOIDBIOME, Type.DEAD, Type.SPOOKY, Type.VOID);
	}

	public static void preInit() {
		GameRegistry.registerTileEntity(TileEntityPile.class, "tileentitypile");
		GameRegistry.registerTileEntity(TileEntityAltar.class, "tileentityaltar");
		GameRegistry.registerTileEntity(TileEntityCrystallizer.class, "tileentitycrystalizer");
		GameRegistry.registerTileEntity(TileEntityConduit.class, "tileentityconduit");
		GameRegistry.registerTileEntity(TileEntityChaoticNode.class, "tileentitychaoticnode");
		GameRegistry.registerTileEntity(TileEntityVoidNode.class, "tileentityvoidnode");
		DimensionRegistry.mainRegistry();
		VoidEntities.registerEntities();
		MinecraftForge.EVENT_BUS.register(EventHandler.class);
		Ref.INSTANCE.registerMessage(BookGuiPacket.Handler.class, BookGuiPacket.class, 0, Side.CLIENT);
	}
	
	public static void Init() {
		GameRegistry.registerWorldGenerator(new VoidWorldGenerator(), 0);
		Triggers.init();
		BookInit.init();
	}
}
