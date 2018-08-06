package com.zerahi.ravvoid.entity.mob.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelShade extends ModelBase
{
  //fields
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
  
  public ModelShade()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      head = new ModelRenderer(this, 0, 0);
      head.addBox(-3.5F, -8F, -3.5F, 7, 7, 7);
      head.setRotationPoint(0F, 1F, 0F);
      head.setTextureSize(64, 32);
      setRotation(head, 0F, 0F, 0F);
      body = new ModelRenderer(this, 16, 16);
      body.addBox(-4F, 0F, -2F, 8, 12, 4);
      body.setRotationPoint(0F, 1F, 0F);
      body.setTextureSize(64, 32);
      setRotation(body, 0F, 0F, 0F);
      rightarm = new ModelRenderer(this, 40, 16);
      rightarm.addBox(-2F, -2F, -1.5F, 3, 9, 3);
      rightarm.setRotationPoint(-6F, 2F, 0F);
      rightarm.setTextureSize(64, 32);
      setRotation(rightarm, 0F, 0F, 0F);
      leftarm = new ModelRenderer(this, 40, 16);
      leftarm.addBox(-1F, -2F, -1.5F, 3, 9, 3);
      leftarm.setRotationPoint(6F, 2F, 0F);
      leftarm.setTextureSize(64, 32);
      setRotation(leftarm, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
  {
    super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
    head.render(scale);
    body.render(scale);
    rightarm.render(scale);
    leftarm.render(scale);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
  {
	  super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
      float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
      float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
      this.rightarm.rotateAngleZ = 0.0F;
      this.leftarm.rotateAngleZ = 0.0F;
      this.rightarm.rotateAngleY = -(0.1F - f * 0.6F);
      this.leftarm.rotateAngleY = 0.1F - f * 0.6F;
      float f2 = -(float)Math.PI / (10F);
      this.rightarm.rotateAngleX = f2;
      this.leftarm.rotateAngleX = f2;
      this.rightarm.rotateAngleX += f * 1.2F - f1 * 0.4F;
      this.leftarm.rotateAngleX += f * 1.2F - f1 * 0.4F;
      this.rightarm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.leftarm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.rightarm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.3F;
      this.leftarm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.3F;
      
  }

}
