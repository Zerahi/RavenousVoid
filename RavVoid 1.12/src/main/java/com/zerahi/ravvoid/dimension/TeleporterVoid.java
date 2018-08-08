
package com.zerahi.ravvoid.dimension;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class TeleporterVoid extends Teleporter
{
	private final WorldServer worldServerInstance;
	private final Long2ObjectMap<Teleporter.PortalPosition> destinationCoordinateCache = new Long2ObjectOpenHashMap<PortalPosition>(4096);
	private final Block portal;
	private final IBlockState frame;

	public TeleporterVoid(WorldServer worldIn, Block portal, IBlockState frame)
	{
		super(worldIn);
		worldServerInstance = worldIn;
		this.portal = portal;
		this.frame = frame;
	}

	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw)
	{
		if (worldServerInstance.provider.getDimension() != 1)
		{
			if (entityIn instanceof EntityPlayerMP && !((EntityPlayerMP)entityIn).capabilities.isCreativeMode)
				ReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entityIn, true, "invulnerableDimensionChange", "field_184851_cj");

				placeInExistingPortal(entityIn, rotationYaw);
		}
		else
		{
			int i = MathHelper.floor(entityIn.posX);
			int j = MathHelper.floor(entityIn.posY) - 1;
			int k = MathHelper.floor(entityIn.posZ);
			int l = 1;
			int i1 = 0;

			for (int j1 = -2; j1 <= 2; ++j1)
				for (int k1 = -2; k1 <= 2; ++k1)
					for (int l1 = -1; l1 < 3; ++l1)
					{
						int i2 = i + k1 * l + j1 * i1;
						int j2 = j + l1;
						int k2 = k + k1 * i1 - j1 * l;
						boolean flag = l1 < 0;
						worldServerInstance.setBlockState(new BlockPos(i2+2, j2, k2), flag ? frame : Blocks.AIR.getDefaultState());
					}

			entityIn.setLocationAndAngles(i, j, k, entityIn.rotationYaw, 0.0F);
			entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
		}
	}

	@Override
	public boolean placeInExistingPortal(Entity entityIn, float p_180620_2_) {
		double d0 = -1.0D;
		int i = MathHelper.floor(entityIn.posX);
		int j = MathHelper.floor(entityIn.posZ);
		boolean flag1 = true;
		Object object = BlockPos.ORIGIN;
		long k = ChunkPos.asLong(i, j);

		if (destinationCoordinateCache.containsKey(k)) {
			Teleporter.PortalPosition portalposition = destinationCoordinateCache.get(k);
			d0 = 0.0D;
			object = portalposition;
			portalposition.lastUpdateTime = worldServerInstance.getTotalWorldTime();
			flag1 = false;
		}
		else {
			BlockPos blockpos4 = new BlockPos(entityIn);

			for (int l = -128; l <= 128; ++l) {
				BlockPos blockpos1;

				for (int i1 = -128; i1 <= 128; ++i1)
					for (BlockPos blockpos = blockpos4.add(l, worldServerInstance.getActualHeight() - 1 - blockpos4.getY(), i1); blockpos.getY() >= 0; blockpos = blockpos1) {
						blockpos1 = blockpos.down();

						if (worldServerInstance.getBlockState(blockpos).getBlock() == portal) {
							while (worldServerInstance.getBlockState(blockpos1 = blockpos.down()).getBlock() == portal)
								blockpos = blockpos1;

							double d1 = blockpos.distanceSq(blockpos4);

							if (d0 < 0.0D || d1 < d0) {
								d0 = d1;
								object = blockpos;
							}
						}
					}
			}
		}

		if (d0 >= 0.0D) {
			if (flag1)
				destinationCoordinateCache.put(k, new Teleporter.PortalPosition((BlockPos) object, worldServerInstance.getTotalWorldTime()));

			double d4 = ((BlockPos) object).getX() + 0.5D;
			double d5 = ((BlockPos) object).getY() + 0.5D;
			double d6 = ((BlockPos) object).getZ() + 0.5D;
			EnumFacing enumfacing = null;

			if (worldServerInstance.getBlockState(((BlockPos) object).west()).getBlock() == portal)
				enumfacing = EnumFacing.NORTH;

			if (worldServerInstance.getBlockState(((BlockPos) object).east()).getBlock() == portal)
				enumfacing = EnumFacing.SOUTH;

			if (worldServerInstance.getBlockState(((BlockPos) object).north()).getBlock() == portal)
				enumfacing = EnumFacing.EAST;

			if (worldServerInstance.getBlockState(((BlockPos) object).south()).getBlock() == portal)
				enumfacing = EnumFacing.WEST;

			EnumFacing enumfacing1 = EnumFacing.getHorizontal(MathHelper.floor(entityIn.rotationYaw * 4.0F / 360.0F + 0.5D) & 3);

			if (enumfacing != null) {
				EnumFacing enumfacing2 = enumfacing.rotateYCCW();
				BlockPos blockpos2 = ((BlockPos) object).offset(enumfacing);
				boolean flag2 = func_180265_a(blockpos2);
				boolean flag3 = func_180265_a(blockpos2.offset(enumfacing2));

				if (flag3 && flag2) {
					object = ((BlockPos) object).offset(enumfacing2);
					enumfacing = enumfacing.getOpposite();
					enumfacing2 = enumfacing2.getOpposite();
					BlockPos blockpos3 = ((BlockPos) object).offset(enumfacing);
					flag2 = func_180265_a(blockpos3);
					flag3 = func_180265_a(blockpos3.offset(enumfacing2));
				}

				float f6 = 0.5F;
				float f1 = 0.5F;

				if (!flag3 && flag2)
					f6 = 1.0F;
				else if (flag3 && !flag2)
					f6 = 0.0F;
				else if (flag3)
					f1 = 0.0F;

				d4 = ((BlockPos) object).getX() + 0.5D;
				d5 = ((BlockPos) object).getY() + 0.5D;
				d6 = ((BlockPos) object).getZ() + 0.5D;
				d4 += enumfacing2.getFrontOffsetX() * f6 + enumfacing.getFrontOffsetX() * f1;
				d6 += enumfacing2.getFrontOffsetZ() * f6 + enumfacing.getFrontOffsetZ() * f1;
				float f2 = 0.0F;
				float f3 = 0.0F;
				float f4 = 0.0F;
				float f5 = 0.0F;

				if (enumfacing == enumfacing1) {
					f2 = 1.0F;
					f3 = 1.0F;
				}
				else if (enumfacing == enumfacing1.getOpposite()) {
					f2 = -1.0F;
					f3 = -1.0F;
				}
				else if (enumfacing == enumfacing1.rotateY()) {
					f4 = 1.0F;
					f5 = -1.0F;
				}
				else {
					f4 = -1.0F;
					f5 = 1.0F;
				}

				double d2 = entityIn.motionX;
				double d3 = entityIn.motionZ;
				entityIn.motionX = d2 * f2 + d3 * f5;
				entityIn.motionZ = d2 * f4 + d3 * f3;
				entityIn.rotationYaw = p_180620_2_ - enumfacing1.getHorizontalIndex() * 90 + enumfacing.getHorizontalIndex() * 90;
			} else
				entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;

			entityIn.setLocationAndAngles(d4, d5, d6, entityIn.rotationYaw, entityIn.rotationPitch);
			return true;
		} else
			return false;
	}

	private boolean func_180265_a(BlockPos p_180265_1_) {
		return !worldServerInstance.isAirBlock(p_180265_1_) || !worldServerInstance.isAirBlock(p_180265_1_.up());
	}
}
