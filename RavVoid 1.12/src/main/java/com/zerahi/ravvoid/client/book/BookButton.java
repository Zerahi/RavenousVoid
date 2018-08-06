package com.zerahi.ravvoid.client.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class BookButton extends GuiButton {
	int xOff;
	int yOff;
		
	public BookButton(int buttonId, int x, int y, int widthIn, int heightIn, int xOffset, int yOffset) {
		super(buttonId, x, y, widthIn, heightIn, "");
		this.xOff = xOffset;
		this.yOff = yOffset;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible)
        {
            mc.getTextureManager().bindTexture(BookGui.texture);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            @SuppressWarnings("unused")
			int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            if (this.hovered)
            {
                GlStateManager.color(0.5F, 1.0F, 0.0F, 1.0F);
            }
            this.drawTexturedModalRect(this.x, this.y, this.xOff, this.yOff, this.width, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
        }
	}
}
