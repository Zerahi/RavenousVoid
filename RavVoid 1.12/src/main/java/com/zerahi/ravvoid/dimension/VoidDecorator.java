package com.zerahi.ravvoid.dimension;

import java.util.Random;

import com.zerahi.ravvoid.blocks.ShardBlock;
import com.zerahi.ravvoid.register.VoidBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

public class VoidDecorator extends BiomeDecorator
{
    public boolean decorating;
    public BlockPos chunkPos;
    public ChunkGeneratorSettings chunkProviderSettings;
    /** The gravel generator. */
    public WorldGenerator gravelGen = new WorldGenSand(Blocks.GRAVEL, 6);
    /** The dirt generator. */
    public WorldGenerator dirtGen;
    public WorldGenerator gravelOreGen;
    public WorldGenerator graniteGen;
    public WorldGenerator dioriteGen;
    public WorldGenerator andesiteGen;
    public WorldGenerator coalGen;
    public WorldGenerator ironGen;
    /** Field that holds gold WorldGenMinable */
    public WorldGenerator goldGen;
    public WorldGenerator redstoneGen;
    public WorldGenerator diamondGen;
    /** Field that holds Lapis WorldGenMinable */
    public WorldGenerator lapisGen;
    public WorldGenFlowers flowerGen = new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlower.EnumFlowerType.DANDELION);
    /** Field that holds mushroomBrown WorldGenFlowers */
    public WorldGenerator mushroomBrownGen = new WorldGenBush(Blocks.BROWN_MUSHROOM);
    /** Field that holds mushroomRed WorldGenFlowers */
    public WorldGenerator mushroomRedGen = new WorldGenBush(Blocks.RED_MUSHROOM);
    /** Field that holds big mushroom generator */
    public WorldGenerator bigMushroomGen = new WorldGenBigMushroom();
    /** Field that holds WorldGenReed */
    public WorldGenerator reedGen = new WorldGenReed();
    /** Field that holds WorldGenCactus */
    public WorldGenerator cactusGen = new WorldGenCactus();
    /** The water lily generation! */
    public WorldGenerator waterlilyGen = new WorldGenWaterlily();
    /** Amount of waterlilys per chunk. */
    public int waterlilyPerChunk;
    /** The number of trees to attempt to generate per chunk. Up to 10 in forests, none in deserts. */
    public int treesPerChunk;
    public float extraTreeChance = 0.1F;
    /**
     * The number of yellow flower patches to generate per chunk. The game generates much less than this number, since
     * it attempts to generate them at a random altitude.
     */
    public int flowersPerChunk = 2;
    /** The amount of tall grass to generate per chunk. */
    public int grassPerChunk = 1;
    /** The number of dead bushes to generate per chunk. Used in deserts and swamps. */
    public int deadBushPerChunk;
    /**
     * The number of extra mushroom patches per chunk. It generates 1/4 this number in brown mushroom patches, and 1/8
     * this number in red mushroom patches. These mushrooms go beyond the default base number of mushrooms.
     */
    public int mushroomsPerChunk;
    /** The number of reeds to generate per chunk. Reeds won't generate if the randomly selected placement is unsuitable. */
    public int reedsPerChunk;
    /** The number of cactus plants to generate per chunk. Cacti only work on sand. */
    public int cactiPerChunk;
    /** The number of gravel patches to generate per chunk. */
    public int gravelPatchesPerChunk = 1;
    /** The number of sand patches to generate per chunk. Sand patches only generate when part of it is underwater. */
    public int sandPatchesPerChunk = 3;
    /** The number of clay patches to generate per chunk. Only generates when part of it is underwater. */
    public int clayPerChunk = 1;
    /** Amount of big mushrooms per chunk */
    public int bigMushroomsPerChunk;
    /** True if decorator should generate surface lava & water */
    public boolean generateFalls = true;
	private Block shard;
	public int shardsPerChunk = 1;

    @Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
        if (this.decorating)
        {
            throw new RuntimeException("Already decorating");
        }
        else
        {
            this.chunkProviderSettings = ChunkGeneratorSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;
            this.dirtGen = new WorldGenMinable(Blocks.DIRT.getDefaultState(), this.chunkProviderSettings.dirtSize);
            this.ironGen = new WorldGenMinable(VoidBlocks.VOIDORE.getDefaultState(), 8, BlockMatcher.forBlock(VoidBlocks.VOIDSTONE));
    		this.gravelOreGen = new WorldGenMinable(VoidBlocks.ASH.getDefaultState(), 30, BlockMatcher.forBlock(VoidBlocks.VOIDSTONE));
    		this.shard = VoidBlocks.SHARDBLOCK;
            this.graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
            this.dioriteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
            this.andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
            this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize);
            this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
            this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize);
            this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize);
            this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize);
            this.genDecorations(biome, worldIn, random);
            this.decorating = false;
        }
    }
    
	@SuppressWarnings("deprecation")
	@Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(worldIn, random, chunkPos));
        this.generateOres(worldIn, random);
        Random random2 = new Random();
        
        if (random.nextInt(10) == random2.nextInt(10)) {
			int xPos = random.nextInt(16) + 8;
			int zPos = random.nextInt(16) + 8;
			new SpikeGen().generate(worldIn, random, worldIn.getHeight(this.chunkPos.add(xPos, 30, zPos)));
		}
        
        int r = random.nextInt(100);
		int xPos = random.nextInt(16) + 8;
		int zPos = random.nextInt(16) + 8;
        if (r < 30) {
			new VoidTearGen().generate(worldIn, random, worldIn.getHeight(this.chunkPos.add(xPos, 30, zPos)));
			
		} else if (r >29 && r <=40) {
			new FortressGen(0).generate(worldIn, random, worldIn.getHeight(this.chunkPos.add(xPos, 30, zPos)));
		}
        
        
		for (int l2 = 0; l2 < this.shardsPerChunk ; ++l2)
        {        	
           xPos = random.nextInt(16) + 8;
           zPos = random.nextInt(16) + 8;
            int j14 = worldIn.getHeight(this.chunkPos.add(xPos, 0, zPos)).getY() + 32;

            if (j14 > 0)
            {
                int k17 = random.nextInt(j14);
                BlockPos blockpos1 = this.chunkPos.add(xPos, k17, zPos);
                
                for (int i = 0; i < 17; ++i)
                {
                    BlockPos blockpos = blockpos1.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

                    if (worldIn.isAirBlock(blockpos) && shard.canPlaceBlockAt(worldIn, blockpos1) && worldIn.isBlockFullCube(blockpos.add(0, -1, 0)))
                    {
            			int x = random.nextInt(3);
            			if (x == 0) {
            				worldIn.setBlockState(blockpos, shard.getDefaultState().withProperty(ShardBlock.FACING, EnumFacing.NORTH));
            			} else if (x == 1) {
            				worldIn.setBlockState(blockpos, shard.getDefaultState().withProperty(ShardBlock.FACING, EnumFacing.EAST));
            			}else if (x == 2) {
            				worldIn.setBlockState(blockpos, shard.getDefaultState().withProperty(ShardBlock.FACING, EnumFacing.WEST));
            			}else if (x == 3) {
            				worldIn.setBlockState(blockpos, shard.getDefaultState().withProperty(ShardBlock.FACING, EnumFacing.SOUTH));
            			}
                    }
                }
            }
        }
        
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post(worldIn, random, chunkPos));
    }

    /**
     * Generates ores in the current chunk
     */
    @Override
    protected void generateOres(World worldIn, Random random)
    {
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, random, chunkPos));
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dirtGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIRT))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, gravelOreGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRAVEL))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.gravelCount, this.gravelOreGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dioriteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIORITE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, graniteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRANITE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, andesiteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.ANDESITE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, coalGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, ironGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, goldGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, redstoneGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, diamondGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, lapisGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS))
        this.genStandardOre2(worldIn, random, this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, random, chunkPos));
    }

    /**
     * Standard ore generation helper. Vanilla uses this to generate most ores.
     * The main difference between this and {@link #genStandardOre2} is that this takes min and max heights, while
     * genStandardOre2 takes center and spread.
     */
    @Override
    protected void genStandardOre1(World worldIn, Random random, int blockCount, WorldGenerator generator, int minHeight, int maxHeight)
    {
        if (maxHeight < minHeight)
        {
            int i = minHeight;
            minHeight = maxHeight;
            maxHeight = i;
        }
        else if (maxHeight == minHeight)
        {
            if (minHeight < 255)
            {
                ++maxHeight;
            }
            else
            {
                --minHeight;
            }
        }

        for (int j = 0; j < blockCount; ++j)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }

    /**
     * Standard ore generation helper. Vanilla uses this to generate Lapis Lazuli.
     * The main difference between this and {@link #genStandardOre1} is that this takes takes center and spread, while
     * genStandardOre1 takes min and max heights.
     */
    @Override
    protected void genStandardOre2(World worldIn, Random random, int blockCount, WorldGenerator generator, int centerHeight, int spread)
    {
        for (int i = 0; i < blockCount; ++i)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + centerHeight - spread, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }
}