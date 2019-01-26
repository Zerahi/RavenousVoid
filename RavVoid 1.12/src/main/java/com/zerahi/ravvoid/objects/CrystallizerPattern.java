package com.zerahi.ravvoid.objects;

import com.zerahi.ravvoid.register.VoidItems;

import net.minecraft.item.Item;

public class CrystallizerPattern {
	public Item UpgradeItem;
	public Item Catalist;
	public Binders Binder;
	public Item NewItem;
	public static enum Binders {VoidEssence, ChaoticEssence;
		
		public static Binders index(int index) {
			switch (index) {
			case 1: return Binders.VoidEssence;
			case 2: return Binders.ChaoticEssence;
			default : return null;
			}
		}
		
		public static int index(Binders bin) {
			switch (bin) {
			case VoidEssence : return 0;
			case ChaoticEssence : return 1;
			default : return -1;
			}
		}
		
		public static Item item(int index) {
			switch (index) {
			case 0: return VoidItems.VOIDESSENCE;
			case 1: return VoidItems.CHAOTICESSENCE;
			default : return null;
			}
		}

		public static Binders item(Item item) {
			if (item == VoidItems.VOIDESSENCE) return Binders.VoidEssence;
			if (item == VoidItems.CHAOTICESSENCE) return Binders.ChaoticEssence;
			return null;
		}

		public static Item item(Binders bin) {
			
			switch (bin) {
			case VoidEssence : return VoidItems.VOIDESSENCE;
			case ChaoticEssence : return VoidItems.CHAOTICESSENCE;
			default : return null;
			}
		}
	}
	
	public CrystallizerPattern(Item upgradeItem, Item newItem, Binders binder, Item catalist) {
		this.UpgradeItem = upgradeItem;
		this.Binder = binder;
		this.Catalist = catalist;
		this.NewItem = newItem;
	}
}
