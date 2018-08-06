package com.zerahi.ravvoid.entity.mob;

import javax.annotation.Nullable;

import com.zerahi.ravvoid.Ref;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShade extends EntityMob
{
	
    public EntityShade(World world)
    {
        super(world);
        this.setSize(0.6F, 1.95F);
        ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
    }
    

	protected void initEntityAI(){
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }
    

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    }
    

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }


    protected SoundEvent getHurtSound()
    {
        return SoundEvents.ENTITY_BLAZE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_BLAZE_DEATH;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float partialTicks)
    {
        return 15728880;
    }
    
    public void onLivingUpdate()
    {
        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.4D;
        }

        super.onLivingUpdate();
    }

    protected void updateAITasks()
    {

        EntityLivingBase entitylivingbase = this.getAttackTarget();

        if (entitylivingbase != null && entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() > this.posY + (double)this.getEyeHeight())
        {
            this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            this.isAirBorne = true;
        }
        
        if (entitylivingbase != null && this.onGround) {
        	this.jumpHelper.doJump();
            this.isAirBorne = true;
        }
        
        if (entitylivingbase != null && !this.onGround)
        {
        	double x = this.posX - entitylivingbase.posX;
            double y = this.posY - entitylivingbase.posY;
            double z = this.posZ - entitylivingbase.posZ;
            
            double dist = x * x + y * y + z * z;
            dist = (double)MathHelper.sqrt(dist);
            
            
        	if (dist > 2) {
        		this.motionZ = z / dist * -0.2d;
        		this.motionX = x / dist * -0.2d;
        		this.faceEntity(entitylivingbase, getHorizontalFaceSpeed(), getVerticalFaceSpeed());
        		this.setMoveForward(0.01F);
        	}
        }

        super.updateAITasks();
    }

    public void fall(float distance, float damageMultiplier)
    {
    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return Ref.SHADELOOT;
    }
    
    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag)
        {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }
}
