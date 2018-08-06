package com.zerahi.ravvoid.client.book;

import net.minecraft.item.Item;

public class BookEntry {
	public String title;
	public int index;
	public Boolean active;
	public String section;
	public Item icon;
	public String text;
	public Item[] Item = new Item[9];
	public String itemText;
	public String advancement;
	public Boolean parent;
	public boolean grid;
	
	public BookEntry(String titleIn, int indexIn, Boolean activeIn, String sectionIn, Item iconIn, String advancementIn, boolean parentIn, boolean gridIn) {
		this.title = titleIn;
		this.index = indexIn;
		this.active = activeIn;
		this.section = sectionIn;
		this.icon = iconIn;
		this.advancement = advancementIn;
		this.parent = parentIn;
		this.grid = gridIn;
	}
}
