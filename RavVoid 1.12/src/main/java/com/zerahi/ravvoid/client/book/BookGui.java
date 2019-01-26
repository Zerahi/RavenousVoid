package com.zerahi.ravvoid.client.book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zerahi.ravvoid.Ref;
import com.zerahi.ravvoid.register.BookInit;
import com.zerahi.ravvoid.register.VoidItems;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BookGui extends GuiScreen {
	public final static ResourceLocation texture = new ResourceLocation(Ref.MODID, "textures/gui/bookpage.png");
	public final static ResourceLocation textureitems = new ResourceLocation(Ref.MODID, "textures/gui/bookpageitems.png");
	public final static ResourceLocation textureinfo = new ResourceLocation(Ref.MODID, "textures/gui/bookpageinfo.png");
	public final static ResourceLocation font = new ResourceLocation(Ref.MODID, "textures/font/ascii.png");
	private int pageWidth = 178;
	private int pageHeight = 201;
	private int centerx;
	private int centery;
	private int currentSection = -1;
	private int currentEntry = -1;
	private int currentPages = 0;
	private int currentPage = 0;
	private int itemsPage = -1;
	private boolean grid;
	public static List<String> rows = new ArrayList<String>();
	
	int btnIDnext = 0;
	int btnIDback = 1;
	int btnIDout = 2;
	int btnIDtable = 3;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {		

		this.fontRenderer = new FontRenderer(mc.gameSettings, font, mc.getTextureManager(), true);
		mc.renderEngine.bindTexture(texture);
		if(itemsPage == currentPage) {
			if (grid) {
				mc.renderEngine.bindTexture(textureitems);
			} else {
				mc.renderEngine.bindTexture(textureinfo);
			}
		}
		drawDefaultBackground();
		drawTexturedModalRect(centerx, centery, 0, 0, pageWidth, pageHeight);
		drawHorizontalLine(0, 200, 20, 000000);
		mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(VoidItems.AWAKENEDVOIDORB), centerx +1, centery +1);
		if (currentSection == -1) {
			buttonList.clear();
			addDefaultButtons();
			int inactiveSkip = 0;
			for (BookSection section : BookInit.Sections){
				if (section.active) {
					mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(section.icon), centerx + 2,
							centery + 18 + (15 * (BookInit.Sections.indexOf(section)-inactiveSkip)));
					buttonList.add(new BookButtonText(6 + (BookInit.Sections.indexOf(section)-inactiveSkip), centerx, centery
							+ 20 + (15 * (BookInit.Sections.indexOf(section)-inactiveSkip)), "- " + section.title, section.index));
				} else inactiveSkip ++;
			}
		} else if (currentEntry == -1) {
			buttonList.clear();
			addDefaultButtons();
			int count = 0;
			for (BookEntry entry : BookInit.Entrys){
				if (entry.active) {
					if (entry.section == BookInit.Sections.get(currentSection).title) {
						mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(entry.icon), centerx + 2,
								centery + 18 + (15 * (count)));
						buttonList.add(new BookButtonText(6 + (count), centerx, centery
								+ 20 + (15 * (count)), "- " + entry.title, entry.index));
						count++;
					}
				}
			}
		} else {
			buttonList.clear();
			addDefaultButtons();
			if (rows.size() == 0) getRows(BookInit.Entrys.get(currentEntry).text);
			
			while (rows.size() > (17 * (currentPages + 1))) currentPages++;
			
			for (int i = (17 * currentPage); i < 17 * (currentPage +1); i++) {
				if (i < rows.size()) {
					drawString(fontRenderer, rows.get(i), centerx +5, centery + 18 + (10 * i - (170*currentPage)), 131072);
				}
			}
			
			if (rows.size() - (17 * (currentPages)) > 10) {
				currentPages++;
				itemsPage = currentPages;
			} else itemsPage = currentPages;
			
			if (currentPage == itemsPage) {
				for (int i = 0; i <= 8; i++) {
					grid = BookInit.Entrys.get(currentEntry).grid;
					if (BookInit.Entrys.get(currentEntry).Item[i] != null) {
						drawString(this.fontRenderer, BookInit.Entrys.get(currentEntry).itemText, (centerx + (pageWidth/2))
								- (fontRenderer.getStringWidth(BookInit.Entrys.get(currentEntry).itemText)/2), centery + (pageHeight/2) + 18, 131072);
						if (i <= 0 || i <=2) {
							mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(BookInit.Entrys.get(currentEntry).Item[i]), 
									(centerx - 10 + (pageWidth/2)) + (20 * (i-1)), centery + (pageHeight/2) + 30);
						} else if (i <= 3 || i <=5) {
							mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(BookInit.Entrys.get(currentEntry).Item[i]), 
									(centerx - 10 + (pageWidth/2)) + (20 * (i-4)), centery + (pageHeight/2) + 50);
						} else if (i <= 6 || i <=8) {
							mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(BookInit.Entrys.get(currentEntry).Item[i]), 
									(centerx - 10 + (pageWidth/2)) + (20 * (i-7)), centery + (pageHeight/2) + 70);
						}
					}
				}
			}
			
			if (currentPages > 0) {
				if (currentPage > 0) {
					buttonList.add(new BookButton(2, centerx +2, centery + pageHeight - 15, 15, 12, pageWidth +15 , 0));
				}
				if (currentPage < currentPages) {
					buttonList.add(new BookButton(1, centerx + pageWidth - 17, centery + pageHeight - 15, 15, 12, pageWidth, 0));
				}
			}
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private void getRows(String text) {
		String tmprow = "";
		while (fontRenderer.getStringWidth(text) > pageWidth - 35) {
			tmprow = "*";
			while (tmprow != "") {
				if (tmprow == "*") tmprow = "";
				int j = text.indexOf(" ");
				if (j != -1) {
				if (fontRenderer.getStringWidth(tmprow + j) < pageWidth - 35) {
					tmprow = tmprow + text.substring(0, j) + " ";
					text = text.substring(j+1);
				} else {
					rows.add(tmprow);
					tmprow = "";
				}
				} else {
					rows.add(tmprow);
					tmprow = "";
				}
			}
		}
		if (text != "") rows.add(text);
	}

	private void addDefaultButtons() {
		buttonList.add(new BookButtonText(0, centerx, centery + 3, "Void Journal", -1));
		buttonList.add(new BookButton(3, centerx + this.pageWidth -16, centery +4, 12, 12, this.pageWidth, 28));
		buttonList.add(new BookButton(4, centerx + this.pageWidth -100, centery +2, 12, 15, this.pageWidth +17, 13));
		buttonList.add(new BookButton(5, centerx + this.pageWidth -50, centery +2, 14, 15, this.pageWidth, 13));
	}
	
	@Override
	public void initGui() {
		centerx = (width/ 2) - (pageWidth/2);
		centery = (height/ 2) - (pageHeight/2);
		clear();
		addDefaultButtons();
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.id == 0 ||button.id == 4) {
			this.currentSection = -1;
			this.currentEntry = -1;
			clear();
		} else if (button.id == 1) {
			currentPage++;
		} else if (button.id == 2) {
			currentPage--;
		} else if (button.id == 3) {
			mc.player.closeScreen();
		} else if (button.id == 5) {
			if (this.currentEntry == -1 && this.currentSection > -1) {
				this.currentSection = -1;
			} else if (this.currentEntry > -1) {
				this.currentEntry = -1;
				clear();
			}
		} else if (button.id >= 6) {
			if (this.currentSection == -1) {
				this.currentSection = ((BookButtonText)button).index;
				clear();
			} else if (this.currentEntry == -1) {
				this.currentEntry = ((BookButtonText)button).index;
				clear();
			}
		}
	}
	
	private void clear() {
		this.currentPages = 0;
		this.currentPage = 0;
		this.itemsPage = -1;
		BookGui.rows.clear();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton == 1) {
			if (this.currentEntry == -1 && this.currentSection > -1) {
				this.currentSection = -1;
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			} else if (this.currentEntry > -1) {
				this.currentEntry = -1;
				clear();
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}