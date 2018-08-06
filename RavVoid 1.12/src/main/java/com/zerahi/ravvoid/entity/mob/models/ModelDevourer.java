package com.zerahi.ravvoid.entity.mob.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Cubik Studio 2.8.445 Beta JAVA exporter
// Designed by Zerahi with Cubik Studio - https://cubik.studio

public class ModelDevourer extends ModelBase {

    //fields
    ModelRenderer e1_Footleft;
    ModelRenderer e2_Footright;
    ModelRenderer e3_Legleftlower;
    ModelRenderer e4_Legrightlower;
    ModelRenderer e5_Legleftupper;
    ModelRenderer e6_Legrightupper;
    ModelRenderer e7_Torsoupper;
    ModelRenderer e8_Shoulderright;
    ModelRenderer e9_Torsolower;
    ModelRenderer e10_Head;
    ModelRenderer e11_Shoulderleft;
    ModelRenderer e12_Armrightupper;
    ModelRenderer e13_Armleftupper;
    ModelRenderer e14_Armrightlower;
    ModelRenderer e15_Armleftlower;

    public ModelDevourer()
    {
        textureWidth = 128;
        textureHeight = 64;

        e1_Footleft = new ModelRenderer(this, 0, 32);
        e1_Footleft.addBox(-4F, 11.5F, 0F, 4, 3, 8);
        e1_Footleft.setRotationPoint(4F, 9.5F, 3F);
        e1_Footleft.setTextureSize(128, 64);
        e1_Footleft.mirror = false;
        setRotation(e1_Footleft, 0F, 3.141593F, 0F);
        e2_Footright = new ModelRenderer(this, 0, 21);
        e2_Footright.addBox(0F, 11.5F, 0F, 4, 3, 8);
        e2_Footright.setRotationPoint(-4F, 9.5F, 3F);
        e2_Footright.setTextureSize(128, 64);
        e2_Footright.mirror = false;
        setRotation(e2_Footright, 0F, 3.141593F, 0F);
        e3_Legleftlower = new ModelRenderer(this, 60, 31);
        e3_Legleftlower.addBox(-3.5F, -2.5F, 4.5F, 3, 13, 4);
        e3_Legleftlower.setRotationPoint(4F, 9.5F, 3F);
        e3_Legleftlower.setTextureSize(128, 64);
        e3_Legleftlower.mirror = false;
        setRotation(e3_Legleftlower, 5.934119F, 3.141593F, 0F);
        e4_Legrightlower = new ModelRenderer(this, 60, 14);
        e4_Legrightlower.addBox(0.5F, -2.5F, 4.5F, 3, 13, 4);
        e4_Legrightlower.setRotationPoint(-4F, 9.5F, 3F);
        e4_Legrightlower.setTextureSize(128, 64);
        e4_Legrightlower.mirror = false;
        setRotation(e4_Legrightlower, 5.934119F, 3.141593F, 0F);
        e5_Legleftupper = new ModelRenderer(this, 26, 33);
        e5_Legleftupper.addBox(-4F, -3F, -2F, 4, 5, 11);
        e5_Legleftupper.setRotationPoint(4F, 9.5F, 3F);
        e5_Legleftupper.setTextureSize(128, 64);
        e5_Legleftupper.mirror = false;
        setRotation(e5_Legleftupper, 5.934119F, 3.141593F, 0F);
        e6_Legrightupper = new ModelRenderer(this, 26, 17);
        e6_Legrightupper.addBox(0F, -3F, -2F, 4, 5, 11);
        e6_Legrightupper.setRotationPoint(-4F, 9.5F, 3F);
        e6_Legrightupper.setTextureSize(128, 64);
        e6_Legrightupper.mirror = false;
        setRotation(e6_Legrightupper, 5.934119F, 3.141593F, 0F);
        e7_Torsoupper = new ModelRenderer(this, 26, 49);
        e7_Torsoupper.addBox(-5F, -7F, -3F, 10, 8, 7);
        e7_Torsoupper.setRotationPoint(0F, 2F, 0F);
        e7_Torsoupper.setTextureSize(128, 64);
        e7_Torsoupper.mirror = false;
        setRotation(e7_Torsoupper, 5.934119F, 3.141593F, 0F);
        e8_Shoulderright = new ModelRenderer(this, 0, 11);
        e8_Shoulderright.addBox(0F, -4F, -3F, 6, 4, 6);
        e8_Shoulderright.setRotationPoint(-4.5F, -2F, -3F);
        e8_Shoulderright.setTextureSize(128, 64);
        e8_Shoulderright.mirror = false;
        setRotation(e8_Shoulderright, 6.021386F, 3.141593F, 0F);
        e9_Torsolower = new ModelRenderer(this, 0, 43);
        e9_Torsolower.addBox(-4F, -6.5F, -2.5F, 8, 16, 5);
        e9_Torsolower.setRotationPoint(0F, 2F, 0F);
        e9_Torsolower.setTextureSize(128, 64);
        e9_Torsolower.mirror = false;
        setRotation(e9_Torsolower, 5.934119F, 3.141593F, 0F);
        e10_Head = new ModelRenderer(this, 90, 51);
        e10_Head.addBox(-2.5F, -3.5F, -2F, 5, 4, 9);
        e10_Head.setRotationPoint(0F, -4F, -3.5F);
        e10_Head.setTextureSize(128, 64);
        e10_Head.mirror = false;
        setRotation(e10_Head, 6.021386F, 3.141593F, 0F);
        e11_Shoulderleft = new ModelRenderer(this, 0, 1);
        e11_Shoulderleft.addBox(-6F, -4F, -3F, 6, 4, 6);
        e11_Shoulderleft.setRotationPoint(4.5F, -2F, -3F);
        e11_Shoulderleft.setTextureSize(128, 64);
        e11_Shoulderleft.mirror = false;
        setRotation(e11_Shoulderleft, 6.021386F, 3.141593F, 0F);
        e12_Armrightupper = new ModelRenderer(this, 74, 36);
        e12_Armrightupper.addBox(-5F, -1F, -2, 4, 8, 4);
        e12_Armrightupper.setRotationPoint(-4.5F, -2F, -3F);
        e12_Armrightupper.setTextureSize(128, 64);
        e12_Armrightupper.mirror = false;
        setRotation(e12_Armrightupper, 0.3490658F, 0F, 0.1745329F);
        e13_Armleftupper = new ModelRenderer(this, 74, 24);
        e13_Armleftupper.addBox(1F, -1f, -2, 4, 8, 4);
        e13_Armleftupper.setRotationPoint(4.5F, -2F, -3F);
        e13_Armleftupper.setTextureSize(128, 64);
        e13_Armleftupper.mirror = false;
        setRotation(e13_Armleftupper, 0.3490658F, 0F, 6.108652F);
        e14_Armrightlower = new ModelRenderer(this, 26, 1);
        e14_Armrightlower.addBox(-4.5F, 2.5F, -10.5F, 3, 4, 12);
        e14_Armrightlower.setRotationPoint(-4.5F, -2F, -3F);
        e14_Armrightlower.setTextureSize(128, 64);
        e14_Armrightlower.mirror = false;
        setRotation(e14_Armrightlower, 0.3490658F, 0F, 0.1745329F);
        e15_Armleftlower = new ModelRenderer(this, 60, 48);
        e15_Armleftlower.addBox(1.5F, 2.5F, -10.5F, 3, 4, 12);
        e15_Armleftlower.setRotationPoint(4.5F, -2F, -3F);
        e15_Armleftlower.setTextureSize(128, 64);
        e15_Armleftlower.mirror = false;
        setRotation(e15_Armleftlower, 0.3490658F, 0F, 6.108652F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        e1_Footleft.render(f5);
        e2_Footright.render(f5);
        e3_Legleftlower.render(f5);
        e4_Legrightlower.render(f5);
        e5_Legleftupper.render(f5);
        e6_Legrightupper.render(f5);
        e7_Torsoupper.render(f5);
        e8_Shoulderright.render(f5);
        e9_Torsolower.render(f5);
        e10_Head.render(f5);
        e11_Shoulderleft.render(f5);
        e12_Armrightupper.render(f5);
        e13_Armleftupper.render(f5);
        e14_Armrightlower.render(f5);
        e15_Armleftlower.render(f5);
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
        this.e10_Head.rotateAngleY = (netHeadYaw -360) * (0.017453292F/2);
        this.e10_Head.rotateAngleX = headPitch * 0.017453292F;
        this.e1_Footleft.rotateAngleX = -1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount ;
        this.e3_Legleftlower.rotateAngleX = -1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount + 5.934119F;
        this.e5_Legleftupper.rotateAngleX = -1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount + 5.934119F;
        this.e2_Footright.rotateAngleX = 1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.e4_Legrightlower.rotateAngleX = 1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount + 5.934119F;
        this.e6_Legrightupper.rotateAngleX = 1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount + 5.934119F;
        
        this.e12_Armrightupper.rotateAngleX = 1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount + 0.3490658F;
        this.e14_Armrightlower.rotateAngleX = 1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount + 0.3490658F;
        this.e8_Shoulderright.rotateAngleX = 1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount + 6.021386F;
        this.e13_Armleftupper.rotateAngleX = - 1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount + 0.3490658F;
        this.e15_Armleftlower.rotateAngleX = - 1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount + 0.3490658F;
        this.e11_Shoulderleft.rotateAngleX = - 1F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount + 6.021386F;
    }
 
    private float triangleWave(float p_78172_1_, float p_78172_2_)
	{
	    return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
	}
}