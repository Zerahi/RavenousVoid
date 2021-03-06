package com.zerahi.ravvoid.register;

import java.util.ArrayList;
import java.util.List;

import com.zerahi.ravvoid.blocks.BlockGen;
import com.zerahi.ravvoid.blocks.ChaoticNode;
import com.zerahi.ravvoid.blocks.Conduit;
import com.zerahi.ravvoid.blocks.Crystallizer;
import com.zerahi.ravvoid.blocks.PileBlock;
import com.zerahi.ravvoid.blocks.PureShardBlock;
import com.zerahi.ravvoid.blocks.ShardBlock;
import com.zerahi.ravvoid.blocks.VoidAltar;
import com.zerahi.ravvoid.blocks.VoidNode;
import com.zerahi.ravvoid.blocks.VoidOre;
import com.zerahi.ravvoid.blocks.VoidRend;
import com.zerahi.ravvoid.blocks.VoidRift;
import com.zerahi.ravvoid.blocks.VoidTear;
import com.zerahi.ravvoid.entity.mob.EntityShade;
import com.zerahi.ravvoid.entity.mob.EntityVoidBeast;
import com.zerahi.ravvoid.objects.AltarListItem;
import com.zerahi.ravvoid.objects.VoidEntity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class VoidBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final List<AltarListItem> AltarList = new ArrayList<AltarListItem>();
	
	
	
	public static final Block VOIDORE = new VoidOre("voidore", Material.ROCK);
	public static final Block PILEBLOCK = new PileBlock("pileblock", Material.GROUND);
	public static final Block PILEBLOCKACTIVE = new PileBlock("pileblockactive", Material.GROUND).setLightLevel(0.3f);
	public static final Block SHARDBLOCK = new ShardBlock("shardblock", Material.GLASS);
	public static final Block VOIDALTAR = new VoidAltar("voidaltar", Material.PISTON);
	public static final Block CRYSTALLIZER = new Crystallizer("crystallizer", Material.PISTON);
	public static final Block PURESHARDBLOCK = new PureShardBlock("pureshardblock", Material.GLASS);
	public static final Block CONDUIT = new Conduit("conduit", Material.PISTON);
	public static final Block VOIDREND = new VoidRend("voidrend", Material.GROUND);
	public static final Block VOIDTEAR = new VoidTear("voidtear", Material.GROUND);
	
	public static final Block[] VOIDRIFT = {
		new VoidRift("voidrift", Material.GROUND, 1),
		new VoidRift("voidrift", Material.GROUND, 2),
		new VoidRift("voidrift", Material.GROUND, 3),
		new VoidRift("voidrift", Material.GROUND, 4),
		new VoidRift("voidrift", Material.GROUND, 5),
		new VoidRift("voidrift", Material.GROUND, 6),
		new VoidRift("voidrift", Material.GROUND, 7),
		new VoidRift("voidrift", Material.GROUND, 8),
		new VoidRift("voidrift", Material.GROUND, 9),
	};
	public static final Block ASH = new BlockGen("ash", Material.GROUND).setHardness(0.5F);
	public static final Block CONTAINEDENTROPY = new BlockGen("containedentropy", Material.ROCK).setHardness(50.0F).setResistance(2000.0F);
	public static final Block CONTAINEDENTROPYBRICK = new BlockGen("containedentropybrick", Material.ROCK).setHardness(50.0F).setResistance(2000.0F);
	//public static final Block CONTAINEDENTROPYSLAB = new BlockGenSlab("containedentropybrick", Material.ROCK).setHardness(50.0F).setResistance(2000.0F);
	//public static final Block CONTAINEDENTROPYSTEP = new ContainedEntropyStep("containedentropystep", Material.ROCK).setHardness(50.0F).setResistance(2000.0F);
	public static final Block CHAOTICNODE = new ChaoticNode("chaoticnode", Material.ROCK);
	public static final Block VOIDNODE = new VoidNode("voidnode", Material.ROCK);
	public static final Block VOIDSTONE = new BlockGen("voidstone", Material.ROCK).setHardness(1.5F).setResistance(10.0F);
	
	public static void RegisterAltarList() {
		AltarList.add(new AltarListItem(Items.ROTTEN_FLESH, EntityVoidBeast.class));
		AltarList.add(new AltarListItem(Items.BONE, EntityShade.class));
	}
	
	public static Entity CheckAltarList(Item display, World world) {
		for(AltarListItem a:AltarList) {
			if (a.Ingrediant == display) {
				try {
					for (VoidEntity e: VoidEntities.Mobs) {
						if (a.Mob == e.MobClass) return e.NewMob(world);
					}
				} catch (IllegalArgumentException | SecurityException e) {
				}
				}
			}
		return null;
	}
}
