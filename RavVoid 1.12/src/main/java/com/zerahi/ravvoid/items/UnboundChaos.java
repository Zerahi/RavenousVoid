package com.zerahi.ravvoid.items;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.zerahi.ravvoid.VoidMod;
import com.zerahi.ravvoid.entity.projectile.EntityChaosBolt;
import com.zerahi.ravvoid.register.VoidItems;
import com.zerahi.ravvoid.register.VoidTabs;
import com.zerahi.ravvoid.utils.interfaces.IPower;
import com.zerahi.ravvoid.utils.interfaces.IRegisterModels;
import com.zerahi.ravvoid.utils.interfaces.IUseItems;
import com.zerahi.ravvoid.utils.interfaces.IVoidOrbs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UnboundChaos extends Item implements IRegisterModels, IUseItems, IPower {
	private final float attackDamage;
	private int maxPower;
	
	public UnboundChaos(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
        this.maxStackSize = 1;
        this.attackDamage = 14.0F;
        this.maxPower = 500;
        this.setMaxDamage(0);
		setCreativeTab(VoidTabs.tabVoid);
		
		VoidItems.ITEMS.add(this);
	}
	
    public float getAttackDamage()
    {
        return this.attackDamage;
    }
	
	
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();

        if (block == Blocks.WEB)
        {
            return 15.0F;
        }
        else
        {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }
	
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        return true;
    }
    
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
    	
    	 return false;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
    
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return blockIn.getBlock() == Blocks.WEB;
    }

    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	if (stack.hasTagCompound()) {
    	    tooltip.add("Power: " + this.getPower(stack));
    	}
    }
    
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    	if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			    this.setPower(stack, 0);
				((EntityPlayer)entityIn).inventory.markDirty();
		}
    }

	
	public void registerModels() 
	{
		VoidMod.proxy.registerItem(this, 0, "inventory");
	}
	
    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
    	if (!stack.hasTagCompound()) {
    		return 1;
    	}
    	else {
        return ((float)this.maxPower - (float)this.getPower(stack))/(float)this.maxPower;
    	}
    }
    
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {	
    	ItemStack item = player.getHeldItem(hand);
    	ItemStack mainItem = player.getHeldItem(EnumHand.MAIN_HAND);
    	ItemStack offItem = player.getHeldItem(EnumHand.OFF_HAND);
    	
     	if (hand == EnumHand.MAIN_HAND || (hand == EnumHand.OFF_HAND && !(mainItem.getItem() instanceof IUseItems))) {
     		if (this.getPower(item) >= 50) {
    	        this.powerHelper(item, -50);
    	    	EntityChaosBolt projectile = new EntityChaosBolt(world, player) {
    				@Override
    				protected ItemStack getArrowStack() {
    					return null;
    				}
    			};
    	        projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 2.0F);
    	        world.spawnEntity(projectile);
    			((EntityPlayer)player).inventory.markDirty();
    			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
    		} else if (offItem.getItem() instanceof IVoidOrbs && ((IPower)offItem.getItem()).getPower(offItem) >= 50) {
    			((IPower)offItem.getItem()).powerHelper(offItem, -50);
    	    	EntityChaosBolt projectile = new EntityChaosBolt(world, player) {
    				@Override
    				protected ItemStack getArrowStack() {
    					return null;
    				}
    			};
    			projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 2.0F);
    	        world.spawnEntity(projectile);
    			((EntityPlayer)player).inventory.markDirty();
    			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
    		}
     	}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
    }
    
    @SuppressWarnings("deprecation")
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }

	@Override
	public int getMaxPower() {
		return this.maxPower;
	}
}