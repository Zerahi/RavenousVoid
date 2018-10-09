package com.zerahi.ravvoid.register;

import java.util.ArrayList;
import java.util.List;

import com.zerahi.ravvoid.Ref;
import com.zerahi.ravvoid.client.book.BookEntry;
import com.zerahi.ravvoid.client.book.BookSection;
import com.zerahi.ravvoid.network.BookGuiPacket;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class BookInit {
	public static List<BookSection> Sections = new ArrayList<BookSection>(); 
	public static List<BookEntry> Entrys = new ArrayList<BookEntry>();
	public static List<String> Text = new ArrayList<String>();
	
	public static void init() {
		
		//Sections in book
		Sections.add(new BookSection("First Shard", true, VoidItems.VOIDSHARD, 0));
		Sections.add(new BookSection("Focus", false, VoidItems.VOIDORB, 1));
		Sections.add(new BookSection("Sight", false, VoidItems.AWAKENEDVOIDORB, 2));
		
		//Entries in sections
		Entrys.add(new BookEntry("Void Ore", 0, true, "First Shard", Item.getItemFromBlock(VoidBlocks.VOIDORE), "voidore", false, false));
		Entrys.add(new BookEntry("Void Shard", 1, false, "First Shard", VoidItems.VOIDSHARD, "voidshard", true, false));
		Entrys.add(new BookEntry("Void Fragments", 2, false, "First Shard", VoidItems.VOIDFRAGMENTS, "voidfragments", true, false));
		Entrys.add(new BookEntry("Fragment Pile", 3, false, "First Shard", VoidItems.FRAGMENTPILE, "fragmentpile", true, true));
		Entrys.add(new BookEntry("Darkness", 4, false, "First Shard", Item.getItemFromBlock(VoidBlocks.PILEBLOCK), "darkness", true, false));
		Entrys.add(new BookEntry("Void Orb", 5, false, "Focus", VoidItems.VOIDORB, "voidorb", false, true));
		Entrys.add(new BookEntry("Void Altar", 6, false, "Focus", Item.getItemFromBlock(VoidBlocks.VOIDALTAR), "voidaltar", false, false));
		Entrys.add(new BookEntry("Void Rend", 7, false, "Focus", Item.getItemFromBlock(VoidBlocks.VOIDREND), "voidrend", false, false));
		Entrys.add(new BookEntry("Void Beast", 8, false, "Focus", VoidItems.BEASTHIDE, "voidbeast", false, false));
		Entrys.add(new BookEntry("Spirit", 9, false, "Focus", VoidItems.SPIRIT, "shade", false, false));
		Entrys.add(new BookEntry("Crystallizer", 10, false, "Focus", Item.getItemFromBlock(VoidBlocks.CRYSTALLIZER), "crystallizer", true, true));
		Entrys.add(new BookEntry("Pure Void Shard", 11, false, "Focus", VoidItems.PUREVOIDSHARD, "purevoidshard", true, false));
		Entrys.add(new BookEntry("Awakened Void Orb", 12, false, "Focus", VoidItems.AWAKENEDVOIDORB, "awakenedvoidorb", true, false));
		Entrys.add(new BookEntry("Conduit", 13, false, "Focus", Item.getItemFromBlock(VoidBlocks.CONDUIT), "conduit", true, true));
		Entrys.add(new BookEntry("Sight", 14, false, "Sight", VoidItems.AWAKENEDVOIDORB, "sight", true, false));
		Entrys.add(new BookEntry("Void Rift", 15, false, "Sight", VoidItems.PUREVOIDSHARD, "voidrift", true, false));
		Entrys.add(new BookEntry("Through the Rift", 16, false, "Sight", Item.getItemFromBlock(VoidBlocks.ASH), "through", true, false));
		Entrys.add(new BookEntry("Instability", 17, false, "Sight", Item.getItemFromBlock(VoidBlocks.CHAOTICNODE), "tear", true, false));
		
		addText();
		//Text and items for entries
		for (BookEntry entry : Entrys){
			entry.text = Text.get(entry.index);
			addItems(entry, entry.index);
		}
	}
	
	private static void addItems(BookEntry entry, int index) {
		switch (index) {
		case 0: entry.Item[0] = Items.IRON_PICKAXE;
				entry.Item[4] = Item.getItemFromBlock(VoidBlocks.VOIDORE);
				entry.Item[5] = VoidItems.VOIDSHARD;
				entry.itemText = "Mine some Void Ore.";
				break;
		case 1: entry.Item[0] = VoidItems.VOIDSHARD;
				entry.Item[4] = Item.getItemFromBlock(VoidBlocks.SHARDBLOCK);
				entry.Item[7] = Item.getItemFromBlock(Blocks.STONE);
				entry.itemText = "Right-click a surface with a shard.";
				break;
		case 2: entry.Item[0] = Items.IRON_PICKAXE;
				entry.Item[4] = Item.getItemFromBlock(VoidBlocks.SHARDBLOCK);
				entry.Item[5] = VoidItems.VOIDFRAGMENTS;
				entry.itemText = "Break the shard to obtain fragments.";
				break;
		case 3: entry.Item[1] = VoidItems.VOIDFRAGMENTS;
				entry.Item[3] = VoidItems.VOIDFRAGMENTS;
				entry.Item[4] = Items.DIAMOND;
				entry.Item[5] = VoidItems.VOIDFRAGMENTS;
				entry.Item[7] = VoidItems.VOIDFRAGMENTS;
				entry.itemText = "Craft a Fragment Pile.";
				break;
		case 4: entry.Item[0] = VoidItems.FRAGMENTPILE;
				entry.Item[4] = Item.getItemFromBlock(VoidBlocks.PILEBLOCK);
				entry.Item[7] = Item.getItemFromBlock(Blocks.STONE);
				entry.itemText = "Right-Click on a surface in a dark place.";
				break;
		case 5: entry.Item[0] = VoidItems.VOIDFRAGMENTS;
		  		entry.Item[1] = VoidItems.FRAGMENTPILE;
		  		entry.Item[2] = VoidItems.VOIDFRAGMENTS;
		  		entry.Item[3] = VoidItems.VOIDSHARD;
				entry.Item[4] = Item.getItemFromBlock(Blocks.PLANKS);
				entry.Item[5] = VoidItems.VOIDSHARD;
				entry.Item[6] = Item.getItemFromBlock(Blocks.PLANKS);
				entry.Item[8] = Item.getItemFromBlock(Blocks.PLANKS);
				entry.itemText = "Craft and place a Void Altar.";
				break;
		case 6: entry.Item[0] = Item.getItemFromBlock(VoidBlocks.SHARDBLOCK);
		  		entry.Item[2] = Item.getItemFromBlock(VoidBlocks.SHARDBLOCK);
				entry.Item[4] = VoidItems.VOIDORB;
		  		entry.Item[6] = Item.getItemFromBlock(VoidBlocks.SHARDBLOCK);
				entry.Item[7] = Item.getItemFromBlock(VoidBlocks.VOIDALTAR);
		  		entry.Item[8] = Item.getItemFromBlock(VoidBlocks.SHARDBLOCK);
				entry.itemText = "Right-click the alter with your void orb.";
				break;
		case 7: entry.Item[1] = Item.getItemFromBlock(VoidBlocks.VOIDREND);
				entry.Item[4] = Items.BONE;
				entry.Item[7] = Item.getItemFromBlock(VoidBlocks.VOIDALTAR);
				entry.itemText = "Place a bone or rotten flesh on the altar";
				break;
		case 8: entry.Item[0] = VoidItems.VOIDESSENCE;
				entry.Item[2] = VoidItems.BEASTHIDE;
				entry.Item[4] = VoidItems.HIDEHELM;
				entry.Item[6] = VoidItems.HIDEFEET;
				entry.Item[7] = VoidItems.HIDECHEST;
				entry.Item[8] = VoidItems.HIDELEGS;
				entry.itemText = "Gather resources.";
				break;
		case 9: entry.Item[3] = VoidItems.VOIDESSENCE;
				entry.Item[5] = VoidItems.SPIRIT;
				entry.itemText = "Gather resources.";
				break;
		case 10: entry.Item[0] = VoidItems.VOIDSHARD;
		  		entry.Item[1] = Item.getItemFromBlock(Blocks.STONE);
		  		entry.Item[2] = VoidItems.VOIDSHARD;
		  		entry.Item[3] = Item.getItemFromBlock(Blocks.STONE);
				entry.Item[5] = Item.getItemFromBlock(Blocks.STONE);
				entry.Item[6] = Item.getItemFromBlock(Blocks.STONE);
				entry.Item[7] = Item.getItemFromBlock(Blocks.PLANKS);
				entry.Item[8] = Item.getItemFromBlock(Blocks.STONE);
				entry.itemText = "Craft and place a Crystallizer.";
				break;
		case 11: entry.Item[1] = VoidItems.VOIDSHARD;
		  		entry.Item[3] = Items.WATER_BUCKET;
		  		entry.Item[4] = VoidItems.VOIDESSENCE;
				entry.Item[5] = VoidItems.SPIRIT;
				entry.Item[7] = Item.getItemFromBlock(VoidBlocks.CRYSTALLIZER);
				entry.itemText = "Purify a Void Shard";
				break;
		case 12: entry.Item[1] = VoidItems.VOIDORB;
		  		entry.Item[3] = Items.WATER_BUCKET;
		  		entry.Item[4] = VoidItems.VOIDESSENCE;
				entry.Item[5] = VoidItems.SPIRIT;
				entry.Item[7] = Item.getItemFromBlock(VoidBlocks.CRYSTALLIZER);
				entry.itemText = "Awaken the Void Orb";
				break;
		case 13: entry.Item[1] = VoidItems.PUREVOIDSHARD;
		  		entry.Item[3] = VoidItems.VOIDSHARD;
		  		entry.Item[4] = VoidItems.VOIDFRAGMENTS;
				entry.Item[5] = VoidItems.VOIDSHARD;
				entry.Item[6] = VoidItems.VOIDSHARD;
				entry.Item[8] = VoidItems.VOIDSHARD;
				entry.itemText = "Craft and place a Conduit.";
				break;
		case 14: entry.Item[4] = VoidItems.AWAKENEDVOIDORB;
				entry.itemText = "Activate Awakened Void Orb.";
				break;
		case 15: entry.Item[0] = Item.getItemFromBlock(VoidBlocks.PURESHARDBLOCK);
		  		entry.Item[2] = Item.getItemFromBlock(VoidBlocks.PURESHARDBLOCK);
				entry.Item[4] = VoidItems.AWAKENEDVOIDORB;
		  		entry.Item[6] = Item.getItemFromBlock(VoidBlocks.PURESHARDBLOCK);
				entry.Item[7] = Item.getItemFromBlock(VoidBlocks.VOIDALTAR);
		  		entry.Item[8] = Item.getItemFromBlock(VoidBlocks.PURESHARDBLOCK);
				entry.itemText = "Place Awakened Void Orb on the Altar.";
		break;
		case 16: entry.Item[4] = VoidItems.AWAKENEDVOIDORB;
		entry.itemText = "Walk through the Void Rift.";
		break;
		case 17: entry.Item[4] = Item.getItemFromBlock(VoidBlocks.CHAOTICNODE);
		entry.itemText = "Pass through a void tear.";
		break;
		default: break;
		}
	}
	private static void addText() {
		//0
		Text.add("I have noticed several places where the stone contains crystals that give of a strange energy. I should attempt to "
				+ "gather some and investigate further.");
		//1
		Text.add("As I remove the surrounding stone, I find a large crystal that appears to have forced its way between rock layers. "
				+ "Crushing the stone in places around its edges. Random sparks of energy shoot from the shard and it has a faint "
				+ "glow. As I look deeply into its surface all I see is darkness. A deep void with shifting motion. A strange"
				+ " feeling washes over me as I stare transfixed. I feel a desperate need to find answers. The power these crystals"
				+ " emit when placed in the world must be able to use somehow.");
		//2
		Text.add("The crystalline structure is imperfect, only a trickle of energy is passing through. I need a way to increase the "
				+ "throughput. The shard itself it too large to work with. I need to shatter it.");
		//3
		Text.add("I can feel the power emanating from the fragments. The energy is wild and chaotic. I need more. A more stable "
				+ "structure than shards or fragments is required. Something I can use to focus the chaotic energies. First, something"
				+ " for the shards to bind to. A diamond should have the right crystal matrix to support growth.");
		//4
		Text.add("The fragments are being drawn around the diamond but nothing is changing. I need a catalyst to fuse them. My first "
				+ "thought was more shards but... I can feel... something else, behind the chaotic energy. A hunger held"
				+ " back. Maybe I was wrong. The shards chaotic energy might not be the only force at work here. Their energy is "
				+ "powerful, but it may be acting to contain something more. The darkness is drawing me more and more when i look into"
				+ " the shards. Maybe that is the secret, I need to find somewhere attuned to it.");
		//5
		Text.add("I have done it, the orb is still imprfect and leaking energy, but I can feel the differnce. With the shards I could see hints of"
				+ " what lay beyond, now i see shapes moving. The void calls to me. I find myself getting lost staring into the orb more"
				+ " and more. This object will work as a focus to connect me to this void relm. I can use the chaotic energies try and open a rend in reality. "
				+ "First i need a surface to work on that is attuned to the shard energies to channel them through the focus.");
		//6
		Text.add("I will need a constant stream of energy through the focus to open the rend. Four void shards placed nearby should work."
				+ " The rend should open above the altar so I need to leave some space. It will take some time for the process to work but"
				+ " once it does I should be able to step through. (4 shards with 5 blocks in any drection, 2 open spaces above altar.)");
		//7
		Text.add("The rend is finally open. I still fell the power washing over me. I don't know how long I sat transfixed on the darkness."
				+ " The rend seems stable enough to stay open, without the focus,  as long as the altar isn't moved. But the chaotic bolts"
				+ " shooting from it make it too dangerous to pass through. Only a creature with some resistance to that power could survive"
				+ " the journey. As I look into the Void Orb now that the rend is open I can see the creatures more clearly. some creatures"
				+ " wander in the void realm. Perhaps if I place something that would attract them on the Void Alter I can lure one through."
				+ " I doubt whatever they are will be friendly, I should prepare for a fight. (Bones or Rotten Flesh, each will give a different creature)");
		//8
		Text.add("The beast charged me the moment it was through, I barely managed to defeat it. The chaotic energy seems to have infused its"
				+ " hide, that must be what allowed it to pass through the rend unharmed. The state the creature is in though makes me think"
				+ " not many creatures could survive its home.  The hide could be used to form some rudimentary armor. If I had some sort of"
				+ " life essence to stabilize the chaotic infusion.");
		//9
		Text.add("The Shade flew towards me desperately and nearly destroyed me. I managed to disperse its essence. Its shape leads me to believe"
				+ " it is the spirit of some lost traveler twisted by chaotic energies. A small amount of essence remains, the tainted spirit"
				+ " essence of a living being should work as a binding catalyst.   I should gather some.");
		//10
		Text.add("The essence that i gather from these creatures is the result of prolonged exposure to chaotic energies. I could use it to "
				+ "refine the crystalline structure of the void orb and shards. It is too chaotic to do so in its current state. I need to "
				+ "create another chaos attuned device to contain the essence.");
		//11
		Text.add("Now that I have the crystallizer ready, I need to fill it with water and toss in some Void Essence. Once that is done, if I "
				+ "add a Spirit Fragment the mixture should be stable. Then I just need to add a Void Shard and wait. This should allow the "
				+ "crystalline matrix to reform in a more pure form allowing more chaotic energies to flow through. (Right click with water bucket"
				+ "drop 4 Essences in, then a Spirit Fragment, then the item to change)");
		//12
		Text.add("The shard is pure, it radiates intense power but it isn't leaking out into the world i should be able to get more power out"
				+ " of each shard. As the energy flows around me I feel more connected, I run to grab the Void Orb and I can almost make out "
				+ "a surface dotted with dark shapes and moving figures. The truth is so close I much continue. The next step is to awaken the"
				+ " full potential of this void orb. I must repeat the same process with it.");
		//13
		Text.add("The Pure Void Shard will work well for providing power in an area to different devices. However what I need is a device to "
				+ "funnel chaotic energies through. I need to create a conduit that will focus the energies into one place to supply it to "
				+ "specific items. Items placed upon it should receive a constant influx of power. It will still need shards nearby like the"
				+ " altar, Pure Void Shards should increase the flow.");
		//14
		Text.add("It is perfect... I can see clearly now... As if I am above a grey flowing landscape with dark rock structures protruding"
				+ " from the surface. Creatures like the ones I have lured here on the surface. I see something I was not expecting, "
				+ "structures built of a dark stone dotted with red lights. Who could have built them? What kind of creatures could survive"
				+ " in a place like that and retain their sanity? With this new Awakened Void Orb, I can feel it drawing in power. The "
				+ "farther I get from the light of my work area the more I can feel that strange hungering presence. Once the orb gains "
				+ "enough power I will reach out to that Void Energy for the first time. I don't know what to expect but I feel I must do"
				+ " this. (Either build a conduit and place the orb on it to charge it or go to a low light area, once the orb is over "
				+ "about 10% you can activate it. With the orb is active it will still charge in low light areas. It will gain more than"
				+ " I uses to stay active in very dark places like caves, but even being outside at night it will lose power slowly, if "
				+ "you in a bright area with it on it will drain without charging.)");
		//15
		Text.add("I understand now,the problem with the stability of the rend was the focus. With this new Awakened Void Orb I should be "
				+ "able to open the way at last. This will, however, require more power. I will need 4 Pure Void Shards near the alter along"
				+ " with power in the Awakened Void Orb to open the portal and keep it open. I should fully charge the Orb before I leave, and"
				+ " it will need to stay in the altar to keep the rift open. The rift will need room to open, It will take up a 3x3x1 area 3 "
				+ "blocks away from the altar in the direction I am facing when placing the orb.");
		//16
		Text.add("The energy flowed over me and I fell to my knees. I sat unable to think, only feeling a barely controllable need to pass "
				+ "through. I need to think about this first. I may want to make a second orb to bring with me to aid in sight. If the Orb "
				+ "in the altar loses power, while I'm inside, the rift will close. I can sense weaknesses on the surface when peering through"
				+ " the Orb. Should i need to, I may be able to use one of them to return. Their is no guarantee on where in this world I would"
				+ " end up and how safe it may be. I should only bring items that are essential it may be hard to return to them if the worst "
				+ "happens.");
		//17
		Text.add("I stumble as I pass through the rift, the darkness fells like a pressure surrounding me pressing in from all sides. Like a "
				+ "film along the surface of it I can sense a chaotic energy, it seems to be holding back the void somehow. I need to "
				+ "investigate further. The ground around me is covered in ash and the stone itself seems infused with the chaotic energy. "
				+ "I can see several void creatures I should be prepared for a fight. My first objective should be to find one of the weak "
				+ "spots to I can be sure I can return safely. I see small structures with what feels like stabilized tears hovering over them. "
				+ "I should find one and try to pass through.");
	}
	
	public static void checkAdvancements(EntityPlayerMP player) {
			List<Integer> index = new ArrayList<Integer>();
			for (BookEntry ent : Entrys) {
				if (ent.index > 0) {
					Advancement adv = player.getServerWorld().getAdvancementManager().getAdvancement(new ResourceLocation(Ref.MODID, ent.advancement));
					if (ent.parent == true) adv = adv.getParent();
					AdvancementProgress progress = player.getAdvancements().getProgress(adv);
					if (progress.isDone()) {
						index.add(ent.index);
						
						if (ent.index >= 5) {
							Sections.get(1).active = true;
							index.add(-1);
						}
						if (ent.index >= 14) {
							Sections.get(2).active = true;
							index.add(-2);
						}
					}
				}
			}
			if (index.size() > 0) {
				NBTTagCompound send = new NBTTagCompound();
				int[] indexi = new int[index.size()];
				for (Integer indexs : index) {
					indexi[index.indexOf(indexs)] = indexs;
				}
				send.setIntArray("index", indexi);
				Ref.INSTANCE.sendTo(new BookGuiPacket(send), player);
			} 
	}

	public static void checkAdvancements(NBTTagCompound data) {
		int[] array = data.getIntArray("index");
		for (int i : array) {
			if (i > 0) {
				Entrys.get(i).active = true;
			} else {
				Sections.get(-i).active = true;
			}
		}
	}

}
