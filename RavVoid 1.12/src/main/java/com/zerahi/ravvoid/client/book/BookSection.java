package com.zerahi.ravvoid.client.book;

import net.minecraft.item.Item;

public class BookSection {
	public String title;
	public Boolean active;
	public Item icon;
	public int index;
	
	public BookSection(String titleIn, Boolean activeIn, Item iconIn, int indexIn) {
		this.title = titleIn;
		this.active = activeIn;
		this.icon = iconIn;
		this.index = indexIn;
	}
}
