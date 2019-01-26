package com.zerahi.ravvoid.objects;

import net.minecraft.item.Item;

@SuppressWarnings("rawtypes")
public class AltarListItem {
	public Item Ingrediant;
	public Class Mob;
	
	public AltarListItem(Item Item, Class MobIn) {
		this.Ingrediant = Item;
		this.Mob = MobIn;
	}
}
