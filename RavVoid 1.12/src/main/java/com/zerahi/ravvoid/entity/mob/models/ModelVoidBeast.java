package com.zerahi.ravvoid.entity.mob.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVoidBeast extends ModelBase {

	ModelRenderer Head;
    ModelRenderer Chest;
    ModelRenderer Body;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Spike_1;
    ModelRenderer Spike_2;
    ModelRenderer Spike_3;
    ModelRenderer Spike_4;
    ModelRenderer Spike_5;
  
  public ModelVoidBeast()
  {
    textureWidth = 64;
    textureHeight = 32;
    
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.addBox(-2.5F, -2.5F, -6F, 5, 5, 6);
    this.Head.setRotationPoint(0F, 10.5F, -6.5F);
    this.Head.setTextureSize(64, 32);
    this.Head.mirror = true;
    setRotation(this.Head, 0F, 0F, 0F);
    this.Chest = new ModelRenderer(this, 22, 0);
    this.Chest.addBox(-6F, -3.5F, -3F, 12, 7, 9);
    this.Chest.setRotationPoint(0F, 12F, -3F);
    this.Chest.setTextureSize(64, 32);
    this.Chest.mirror = true;
    setRotation(this.Chest, 1.570796F, 0F, 0F);
    this.Body = new ModelRenderer(this, 36, 16);
    this.Body.addBox(-4F, 0F, -3F, 8, 10, 6);
    this.Body.setRotationPoint(0F, 11F, 0.4666667F);
    this.Body.setTextureSize(64, 32);
    this.Body.mirror = true;
    setRotation(this.Body, 1.570796F, 0F, 0F);
    this.Leg1 = new ModelRenderer(this, 22, 16);
    this.Leg1.addBox(-1.533333F, 0F, -1F, 3, 13, 3);
    this.Leg1.setRotationPoint(5.5F, 11F, -4F);
    this.Leg1.setTextureSize(64, 32);
    this.Leg1.mirror = true;
    setRotation(this.Leg1, 0F, 0F, 0F);
    this.Leg2 = new ModelRenderer(this, 22, 16);
    this.Leg2.addBox(-1.533333F, 0F, -1F, 3, 13, 3);
    this.Leg2.setRotationPoint(-5.466667F, 11F, -4F);
    this.Leg2.setTextureSize(64, 32);
    this.Leg2.mirror = true;
    setRotation(this.Leg2, 0F, 0F, 0F);
    this.Leg3 = new ModelRenderer(this, 0, 16);
    this.Leg3.addBox(-1F, 0F, -1F, 2, 13, 3);
    this.Leg3.setRotationPoint(3.966667F, 11.06667F, 7F);
    this.Leg3.setTextureSize(64, 32);
    this.Leg3.mirror = true;
    setRotation(this.Leg3, 0.1047198F, 0F, -0.0371786F);
    this.Leg4 = new ModelRenderer(this, 0, 16);
    this.Leg4.addBox(-1F, 0F, -1F, 2, 13, 3);
    this.Leg4.setRotationPoint(-4F, 11.13333F, 7F);
    this.Leg4.setTextureSize(64, 32);
    this.Leg4.mirror = true;
    setRotation(this.Leg4, 0.1047198F, 0F, 0.0371755F);
    this.Spike_1 = new ModelRenderer(this, 18, 11);
    this.Spike_1.addBox(-0.5F, -4F, 0.5F, 1, 4, 1);
    this.Spike_1.setRotationPoint(4F, 6.466667F, -4F);
    this.Spike_1.setTextureSize(64, 32);
    this.Spike_1.mirror = true;
    setRotation(this.Spike_1, -0.1115358F, -0.0371786F, 0.1487144F);
    this.Spike_2 = new ModelRenderer(this, 18, 11);
    this.Spike_2.addBox(-0.5F, -4F, -0.5F, 1, 4, 1);
    this.Spike_2.setRotationPoint(-4F, 6.5F, -3F);
    this.Spike_2.setTextureSize(64, 32);
    this.Spike_2.mirror = true;
    setRotation(this.Spike_2, -0.111544F, 0.0371755F, -0.1487195F);
    this.Spike_3 = new ModelRenderer(this, 18, 11);
    this.Spike_3.addBox(-0.5F, -5F, -0.5F, 1, 5, 1);
    this.Spike_3.setRotationPoint(0F, 8.5F, 3F);
    this.Spike_3.setTextureSize(64, 32);
    this.Spike_3.mirror = true;
    setRotation(this.Spike_3, -0.1487144F, 0F, 0F);
    this.Spike_4 = new ModelRenderer(this, 18, 11);
    this.Spike_4.addBox(-0.5F, -4F, -0.5F, 1, 4, 1);
    this.Spike_4.setRotationPoint(0F, 8.5F, 7F);
    this.Spike_4.setTextureSize(64, 32);
    this.Spike_4.mirror = true;
    setRotation(this.Spike_4, -0.1487144F, 0F, 0F);
    this.Spike_5 = new ModelRenderer(this, 18, 11);
    this.Spike_5.addBox(-0.5F, -4F, -0.5F, 1, 4, 1);
    this.Spike_5.setRotationPoint(0F, 6.5F, -5F);
    this.Spike_5.setTextureSize(64, 32);
    this.Spike_5.mirror = true;
    setRotation(this.Spike_5, -0.1487144F, 0F, 0F);
  }
  
  public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
  {
    super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
    this.Head.render(scale);
    this.Chest.render(scale);
    this.Body.render(scale);
    this.Leg1.render(scale);
    this.Leg2.render(scale);
    this.Leg3.render(scale);
    this.Leg4.render(scale);
    this.Spike_1.render(scale);
    this.Spike_2.render(scale);
    this.Spike_3.render(scale);
    this.Spike_4.render(scale);
    this.Spike_5.render(scale);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
  {
	  this.Head.rotateAngleX = headPitch * 0.017453292F;
      this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
      this.Leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.4F) * 1.4F * limbSwingAmount;
      this.Leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.4F + (float)Math.PI) * 1.4F * limbSwingAmount;
      this.Leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.4F + (float)Math.PI) * 1.4F * limbSwingAmount;
      this.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.4F) * 1.4F * limbSwingAmount;
  }

}