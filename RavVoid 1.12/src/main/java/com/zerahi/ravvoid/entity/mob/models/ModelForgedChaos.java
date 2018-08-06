package com.zerahi.ravvoid.entity.mob.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

// Cubik Studio 2.8.445 Beta JAVA exporter
// Designed by Zerahi with Cubik Studio - https://cubik.studio

public class ModelForgedChaos extends ModelBase {

    //fields
    ModelRenderer e1_Legleft;
    ModelRenderer e2_Legright;
    ModelRenderer e3_Torsolower;
    ModelRenderer e4_Torsoupper;
    ModelRenderer e5_Head;
    ModelRenderer e6_Armleft;
    ModelRenderer e7_Armright;
    ModelRenderer e8_Sholderleft;
    ModelRenderer e9_Sholderright;

    public ModelForgedChaos()
    {
        textureWidth = 128;
        textureHeight = 64;

        this.e1_Legleft = new ModelRenderer(this, 0, 8);
        this.e1_Legleft.addBox(-3F, 0F, -3F, 6, 16, 6);
        this.e1_Legleft.setRotationPoint(4,8,-1);
        this.e1_Legleft.setTextureSize(128, 64);
        this.e1_Legleft.mirror = false;
        setRotation(this.e1_Legleft, 0F, 0F, 0F);
        this.e2_Legright = new ModelRenderer(this, 44, 42);
        this.e2_Legright.addBox(-3F, 0F, -3F, 6, 16, 6);
        this.e2_Legright.setRotationPoint(-4,8,-1);
        this.e2_Legright.setTextureSize(128, 64);
        this.e2_Legright.mirror = false;
        setRotation(this.e2_Legright, 0F, 0F, 0F);
        this.e3_Torsolower = new ModelRenderer(this, 0, 30);
        this.e3_Torsolower.addBox(-6F, 0F, -3.5F, 12, 8, 7);
        this.e3_Torsolower.setRotationPoint(0F,0F,0F);
        this.e3_Torsolower.setTextureSize(128, 64);
        this.e3_Torsolower.mirror = false;
        setRotation(this.e3_Torsolower, 0F, 0F, 0F);
        this.e4_Torsoupper = new ModelRenderer(this, 0, 45);
        this.e4_Torsoupper.addBox(-6.5F, 0F, -4.5F, 13, 10, 9);
        this.e4_Torsoupper.setRotationPoint(0.0F,-10,0.0F);
        this.e4_Torsoupper.setTextureSize(128, 64);
        this.e4_Torsoupper.mirror = false;
        setRotation(this.e4_Torsoupper, 0F, 0F, 0F);
        this.e5_Head = new ModelRenderer(this, 44, 7);
        this.e5_Head.addBox(-3F, -6F, -3F, 6, 6, 6);
        this.e5_Head.setRotationPoint(0F, -10F, 0F);
        this.e5_Head.setTextureSize(128, 64);
        this.e5_Head.mirror = false;
        setRotation(this.e5_Head, 0F, 0F, 0F);
        this.e6_Armleft = new ModelRenderer(this, 44, 19);
        this.e6_Armleft.addBox(-2.5F, 0F, -2.5F, 5, 17, 6);
        this.e6_Armleft.setRotationPoint(9F, -8F, 0F);
        this.e6_Armleft.setTextureSize(128, 64);
        this.e6_Armleft.mirror = false;
        setRotation(this.e6_Armleft, 6.195919F, 0F, 6.195919F);
        this.e7_Armright = new ModelRenderer(this, 68, 41);
        this.e7_Armright.addBox(-2.5F, 0F, -2.5F, 5, 17, 6);
        this.e7_Armright.setRotationPoint(-9F, -8F, 0F);
        this.e7_Armright.setTextureSize(128, 64);
        this.e7_Armright.mirror = false;
        setRotation(this.e7_Armright, 6.195919F, 0F, 0.08726646F);
        this.e8_Sholderleft = new ModelRenderer(this, 90, 51);
        this.e8_Sholderleft.addBox(0F, -5F, -4F, 8, 5, 8);
        this.e8_Sholderleft.setRotationPoint(5.5F, -7.5F, 0F);
        this.e8_Sholderleft.setTextureSize(128, 64);
        this.e8_Sholderleft.mirror = false;
        setRotation(this.e8_Sholderleft, 0F, 0F, 0F);
        this.e9_Sholderright = new ModelRenderer(this, 68, 28);
        this.e9_Sholderright.addBox(-8F, -5F, -4F, 8, 5, 8);
        this.e9_Sholderright.setRotationPoint(-5.5F, -7.5F, 0F);
        this.e9_Sholderright.setTextureSize(128, 64);
        this.e9_Sholderright.mirror = false;
        setRotation(this.e9_Sholderright, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        this.e1_Legleft.render(f5);
        this.e2_Legright.render(f5);
        this.e3_Torsolower.render(f5);
        this.e4_Torsoupper.render(f5);
        this.e5_Head.render(f5);
        this.e6_Armleft.render(f5);
        this.e7_Armright.render(f5);
        this.e8_Sholderleft.render(f5);
        this.e9_Sholderright.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
     
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
        this.e5_Head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.e5_Head.rotateAngleX = headPitch * 0.017453292F;
        this.e1_Legleft.rotateAngleX = -1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.e2_Legright.rotateAngleX = 1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.e1_Legleft.rotateAngleY = 0.0F;
        this.e2_Legright.rotateAngleY = 0.0F;
    }
 

public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
{
        this.e7_Armright.rotateAngleX = (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
        this.e6_Armleft.rotateAngleX = (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
}

	private float triangleWave(float p_78172_1_, float p_78172_2_)
	{
	    return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
	}
}