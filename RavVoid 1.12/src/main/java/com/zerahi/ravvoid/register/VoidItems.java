package com.zerahi.ravvoid.register;

import java.util.ArrayList;
import java.util.List;

import com.zerahi.ravvoid.items.AwakenedVoidOrb;
import com.zerahi.ravvoid.items.Configurer;
import com.zerahi.ravvoid.items.FragmentPile;
import com.zerahi.ravvoid.items.ItemGen;
import com.zerahi.ravvoid.items.PureVoidShard;
import com.zerahi.ravvoid.items.TemplateMaker;
import com.zerahi.ravvoid.items.UnboundChaos;
import com.zerahi.ravvoid.items.VoidArmor;
import com.zerahi.ravvoid.items.VoidJournal;
import com.zerahi.ravvoid.items.VoidShard;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class VoidItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item VOIDSHARD = new VoidShard("voidshard");
	public static final Item VOIDORB = new ItemGen("voidorb").setMaxStackSize(1);
	public static final Item VOIDFRAGMENTS = new ItemGen("voidfragments");
	public static final Item VOIDESSENCE = new ItemGen("voidessence");
	public static final Item FRAGMENTPILE = new FragmentPile("fragmentpile");
	public static final Item BEASTHIDE = new ItemGen("beasthide");
	public static final Item SPIRIT = new ItemGen("spirit");
	public static final Item PUREVOIDSHARD = new PureVoidShard("purevoidshard");
	public static final Item AWAKENEDVOIDORB = new AwakenedVoidOrb("awakenedvoidorb", 1000);
	public static final Item UNBOUNDCHAOS = new UnboundChaos("unboundchaos");
	public static final Item VOIDJOURNAL = new VoidJournal("voidjournal");
	public static final Item DEVOURERSCALES = new ItemGen("devourerscales");
	public static final Item INFUSEDSCALES = new ItemGen("infusedscales");
	public static final Item CHAOTICESSEMCE = new ItemGen("chaoticessence");
	public static final Item CHAOTICHEART = new ItemGen("chaoticheart");
	public static final Item RAVENOUSVOIDORB = new AwakenedVoidOrb("ravenousvoidorb", 2500);
	

	public static final Item TEMPLATEMAKER = new TemplateMaker("templatemaker"); //TODO move to its own mod
	public static final Item CONFIGURER = new Configurer("configurer"); //TODO move to its own mod
	
	
	public static ArmorMaterial HIDE = EnumHelper.addArmorMaterial ("hide", "rv:hide", 18, new int[] {3,6,5,2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	public static ArmorMaterial SCALE = EnumHelper.addArmorMaterial ("scale", "rv:scale", 50, new int[] {4,7,9,4}, 15, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2.0F);
	public static final Item HIDEHELM = new VoidArmor("hidehelm", HIDE, 1, EntityEquipmentSlot.HEAD);
	public static final Item HIDECHEST = new VoidArmor("hidechest", HIDE, 1, EntityEquipmentSlot.CHEST);
	public static final Item HIDELEGS = new VoidArmor("hidelegs", HIDE, 2, EntityEquipmentSlot.LEGS);
	public static final Item HIDEFEET = new VoidArmor("hidefeet", HIDE, 1, EntityEquipmentSlot.FEET);
	public static final Item SCALESHELM = new VoidArmor("scalehelm", SCALE, 1, EntityEquipmentSlot.HEAD);
	public static final Item SCALESCHEST = new VoidArmor("scalechest", SCALE, 1, EntityEquipmentSlot.CHEST);
	public static final Item SCALESLEGS = new VoidArmor("scalelegs", SCALE, 2, EntityEquipmentSlot.LEGS);
	public static final Item SCALESFEET = new VoidArmor("scalefeet", SCALE, 1, EntityEquipmentSlot.FEET);
}