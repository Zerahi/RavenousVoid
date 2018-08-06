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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class VoidBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();

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
public static final Block VOIDRIFT = new VoidRift("voidrift", Material.GROUND);
public static final Block ASH = new BlockGen("ash", Material.GROUND).setHardness(0.5F);
public static final Block CONTAINEDENTROPY = new BlockGen("containedentropy", Material.ROCK).setHardness(50.0F).setResistance(2000.0F);
public static final Block CONTAINEDENTROPYBRICK = new BlockGen("containedentropybrick", Material.ROCK).setHardness(50.0F).setResistance(2000.0F);
//public static final Block CONTAINEDENTROPYSLAB = new BlockGenSlab("containedentropybrick", Material.ROCK).setHardness(50.0F).setResistance(2000.0F);
//public static final Block CONTAINEDENTROPYSTEP = new ContainedEntropyStep("containedentropystep", Material.ROCK).setHardness(50.0F).setResistance(2000.0F);
public static final Block CHAOTICNODE = new ChaoticNode("chaoticnode", Material.ROCK);
public static final Block VOIDNODE = new VoidNode("voidnode", Material.ROCK);
public static final Block VOIDSTONE = new BlockGen("voidstone", Material.ROCK).setHardness(1.5F).setResistance(10.0F);
}