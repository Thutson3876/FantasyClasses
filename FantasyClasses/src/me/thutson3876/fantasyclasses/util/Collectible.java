package me.thutson3876.fantasyclasses.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Collectible {

	ANCIENT_TECHNIQUE(Material.LECTERN, generateAncientTechniqueLore()), MINING_SCHEMATICS(Material.SKELETON_SKULL, generateMiningSchematicsLore()), DRUIDIC_INSCRIPTION(Material.FLOWERING_AZALEA_LEAVES, generateDruidicInscriptionLore());

	private final Material type;
	private final List<String> lore;
	private static final Random rng = new Random();

	private Collectible(Material type) {
		this.type = type;
		this.lore = null;
	}
	
	private Collectible(Material type, List<String> lore) {
		this.type = type;
		this.lore = lore;
	}

	public Material getType() {
		return type;
	}

	public static Collectible getMatchingCollectible(Material type) {
		for (Collectible c : Collectible.values()) {
			if (c.getType().equals(type))
				return c;
		}

		return null;
	}

	public static ItemStack generateDrop() {
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatUtils.chat("&6Untapped Knowledge"));
		List<String> lore = new ArrayList<>();
		lore.add(ChatUtils.chat("Contained Experience: &6" + (int) (rng.nextGaussian() * 12 + 15)));
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}

	public static int getDropExpAmount(ItemStack item) {
		if (item == null)
			return 0;

		if (!item.getType().equals(Material.ENCHANTED_BOOK))
			return 0;

		if (!item.hasItemMeta())
			return 0;

		ItemMeta meta = item.getItemMeta();
		if (!meta.getDisplayName().equals(ChatUtils.chat("&6Untapped Knowledge")))
			return 0;

		char[] chars = meta.getLore().get(0).toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}

		if(sb.length() == 0)
			return 0;
		
		return Integer.parseInt(sb.toString());
	}
	
	public String getRandomLore() {
		if(this.lore == null || lore.isEmpty())
			return "";
		
		Random rng = new Random();
		
		return ChatUtils.chat(lore.get(rng.nextInt(lore.size())));
	}
	
	private static List<String> generateAncientTechniqueLore() {
		List<String> lore = new ArrayList<>();
		lore.add("&3Ascend. Ascend. Ascend!!!");
		lore.add("&3Oh Great One, grant us your wisdom. Guide us upwards. Let us rise as you have.");
		lore.add("&3Snowflakes gliding down. Each one shining beautifully. Such fools as you are.");
		lore.add("&3From the deep you rose. Come to save us from ourselves. Too late, did they learn.");
		lore.add("&3Power so frequently sought after. Strength always the driving force. Ironic that such desirable traits have become your undoing.");
		
		return lore;
	}
	
	private static List<String> generateMiningSchematicsLore() {
		List<String> lore = new ArrayList<>();
		
		lore.add("&0Another has disappeared among the mineshafts... Its only a matter of time before I'm struck with it. What will I do then?");
		lore.add("&0Sylvan claimed he was attacked by a tar covered skeleton. Probably just the darkness down here, although his wounds are festering much quicker than usual. Could this be the cause of the illness?");
		lore.add("&0I will find you brother. I will save you from your withered mind. I-");
		lore.add("&0It's hard work down here. No telling how much time has passed. I don't mind though, everyone complains about the darkness at first, but eventually it all seems just as bright as the surface.");
		lore.add("&0Diggy diggy hole...");
		lore.add("&0Don't bury me down here. Don't let me become like them. Please...");
		
		return lore;
	}
	
	private static List<String> generateDruidicInscriptionLore() {
		List<String> lore = new ArrayList<>();
		
		lore.add("&2Freedom... at last...");
		lore.add("&2Fresh air. Light so bright. I cannot bear.");
		lore.add("&2These animals so pure and free. How I envy their naivety.");
		lore.add("&2Those religious zealots seak ascension. I am quite content down here in the warmth.");
		lore.add("&2Much to relearned. Such vast purity lost. Now we must rediscover, or face our hubris manifested.");
		
		return lore;
	}
}
