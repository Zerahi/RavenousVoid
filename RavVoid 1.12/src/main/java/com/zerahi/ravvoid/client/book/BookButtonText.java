package com.zerahi.ravvoid.client.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class BookButtonText extends GuiButton {
	public int index;	
	
	public BookButtonText(int buttonId, int x, int y, String buttonText, int indexIn) {
		super(buttonId, x, y, 80, 10, buttonText);
		this.index = indexIn;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible)
        {
            FontRenderer fontRenderer = new FontRenderer(mc.gameSettings, BookGui.font, mc.getTextureManager(), true);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            @SuppressWarnings("unused")
			int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 131072;
            
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 4390912;
            }
            
            this.drawString(fontRenderer, this.displayString, this.x + 20, this.y + (this.height - 8) / 2, j);
        }
	}
}
