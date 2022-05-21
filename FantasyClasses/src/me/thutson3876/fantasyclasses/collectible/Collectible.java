package me.thutson3876.fantasyclasses.collectible;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public enum Collectible {

	ANCIENT_TECHNIQUE(Material.LECTERN, generateAncientTechniqueLore()),
	MINING_SCHEMATICS(Material.SKELETON_SKULL, generateMiningSchematicsLore()),
	DRUIDIC_INSCRIPTION(Material.FLOWERING_AZALEA_LEAVES, generateDruidicInscriptionLore()),
	ETCHED_GLASS(Material.GLASS, generateEtchedIceLore());

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
		lore.add(ChatUtils.chat("Contained Experience: &6" + (int) (rng.nextGaussian() * 10 + 20)));
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

		if (sb.length() == 0)
			return 0;

		String s = sb.toString();

		return Integer.parseInt(s.substring(1));
	}

	public String getRandomLore() {
		if (this.lore == null || lore.isEmpty())
			return "";

		Random rng = new Random();

		return ChatUtils.chat(lore.get(rng.nextInt(lore.size())));
	}

	public Map<Collectible, List<Location>> deSerialize(List<Map<String, Object>> locations) {
		if (locations == null || locations.isEmpty())
			return null;

		Map<Collectible, List<Location>> map = new HashMap<>();
		map.put(this, new ArrayList<>());

		List<Location> newList = map.get(this);
		for (Map<String, Object> loc : locations) {
			newList.add(FantasyClasses.getPlugin().deSerializeLocation(loc));
		}

		map.put(this, newList);

		return map;
	}

	public void saveCollectibles(Map<Collectible, List<Location>> collectiblesToSave) {
		FantasyClasses plugin = FantasyClasses.getPlugin();
		FileConfiguration config = plugin.getConfig();
		List<Map<String, Object>> temp = new ArrayList<>();
		for (Location loc : collectiblesToSave.get(this)) {
			temp.add(FantasyClasses.serializeLocation(loc));
		}

		config.set("collectibles." + this.name().toLowerCase(), temp);
		plugin.saveConfig();
	}
	
	public void onBreakEvent(BlockBreakEvent e) {
		Block b = e.getBlock();
		if(b.hasMetadata("Collectible")) {
			if(this.equals(Collectible.getMatchingCollectible(b.getType()))) {
				drop(e);
				return;
			}
		}
		else if(FantasyClasses.getPlugin().checkIfCollectibleLocation(b.getLocation(), this)) {
			drop(e);
			return;
		}
	}
	
	private void drop(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		p.sendMessage(this.getRandomLore());
		p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 0.8f, 1.0f);
		e.setDropItems(false);
		b.getWorld().dropItemNaturally(b.getLocation(), Collectible.generateDrop());
		FantasyClasses.getPlugin().removeCollectible(b.getLocation(), this);
		return;
	}

	private static List<String> generateAncientTechniqueLore() {
		List<String> lore = new ArrayList<>();
		lore.add("&3Ascend. Ascend. Ascend!!!");
		lore.add("&3Oh Great One, grant us your wisdom. Guide us upwards. Let us rise as you have.");
		lore.add("&3Snowflakes gliding down. Each one shining beautifully. Such fools as you are.");
		lore.add("&3From the deep you rose. Come to save us from ourselves. Too late, did they learn.");
		lore.add(
				"&3Power so frequently sought after. Strength always the driving force. Ironic that such desirable traits have become your undoing.");
		lore.add(
				"&3Were you to fail to ascend, embrace the light. It will save you should you regress back to the realm of demons and parasites.");
		lore.add("&3Stave off the tarred thoughts, and your mind shall too glow. Purity in the next life.");
		lore.add(
				"&3The false god soared as we now dream. How does a creature composed of death and deepslate even float? Perhaps we are the fools to oppose such an unstoppable force.");

		return lore;
	}

	private static List<String> generateMiningSchematicsLore() {
		List<String> lore = new ArrayList<>();

		lore.add("&8Another has disappeared among the mineshafts... Its only a matter of time before I'm next.");
		lore.add(
				"&8Sylvan claimed he was attacked by a tar covered skeleton. Its dark down here sure, but he insisted the corpse itself absorbed the light around him. Shit these wounds are festering quickly.");
		lore.add("&8I will find you brother. I will save you from your withered mind. I-");
		lore.add(
				"&8It's hard work down here. No telling how much time has passed. The newest delvers always whine and complain bout how dark it is. They'll get used to it though. I sure did.");
		lore.add("&8Diggy diggy hole...");
		lore.add(
				"&8-he admitted to it. He's the dumbass that ruined that compactor. Funny how these marvels of technology can still be ruined by our tiny ass bladders.");
		lore.add("&8Don't bury me down here. Don't let me become like them. Please...");
		lore.add(
				"&8Excavator's Compliance Rule 4: Always keep your pickaxe on hand and your wear your helmet. Safety first!");
		lore.add(
				"&8Excavator's Compliance Rule 11: While working with molten materials, use netherite infused gloves.");
		lore.add(
				"&8Excavator's Compliance Rule 25: Breaks while operating void compactor are prohibited. Must provide an equal to take over, or wait for response from a supervisor.");
		lore.add(
				"&8Excavator's Compliance Rule 37: Don't approach the shadows you see. Contact your supervisor immediately upon seeing one.");
		lore.add(
				"&8Excavator's Compliance Rule 42: If you see a four-headed, tar-encased statue, be sure to bow down and submit to its will. Should it ever awaken and devour our societal structure, perhaps it will take pity on you.");
		lore.add(
				"&8Excavator's Compliance Rule 46: It has been discovered that placing the remains of the tarred upon polished deepslate in a 4-segmented statue-like pattern could awaken into one of those monstrosities. We are informing you of this so that you know *not* to do it yourself");

		return lore;
	}

	private static List<String> generateDruidicInscriptionLore() {
		List<String> lore = new ArrayList<>();

		lore.add("&2Freedom... at last...");
		lore.add("&2Fresh air. Light so bright. I cannot bear.");
		lore.add("&2These animals so pure and free. How I envy their naivety.");
		lore.add("&2Those religious zealots seak the frozen peaks. I am quite content down here in the warmth.");
		lore.add("&2Much to relearn. Such vast purity lost. Now we must rediscover, or face our hubris manifested.");
		lore.add("&2-and they will resist. You must ensure they swallow the golden fruit. It's the only cure.");
		lore.add("&2One needn't the bindings of social constraints when guided by such free creatures.");
		lore.add(
				"&2To expand, seek out new experiences, stretch across these vast plains and beyond their shores... it's as natural as the leaves floating in the wind.");
		lore.add("&2What is an ocean, but a plain? A sprawling hallowed land with creatures and fruits of plenty");
		lore.add(
				"&8I won't let the knowledge be lost... we've forgotten our true nature and embraced this new life of complacency when we were once innovators, creators, and engineers.");
		lore.add(
				"&8Deepslate 2 meters high, then extending out 1 meter more on each side... 4 skulls dipped in tar and-");

		return lore;
	}

	private static List<String> generateEtchedIceLore() {
		List<String> lore = new ArrayList<>();

		lore.add(
				"&bI've stored the contents in the Temple Overseers. None shall access to such cursed knowledge ever again.");
		lore.add("&bPoor Pretzel... she probably thinks she was abandoned... I'm so sorry.");
		lore.add(
				"&bThe conduits have proven invaluable in the Battle for Timal. I fear what would happen should those withered beasts get their hands on such a device.");
		lore.add(
				"&bHave we risen to overcome great challenges? Or perhaps forgotten how to live in peace. To go back and ask those first fishmongers that decided to delve deeper... we will never know");
		lore.add(
				"&bA thousand years from now what will be left? Scarred remnants and ruined temples washed ashore. These accomplishments reek of echoed mistakes.");
		lore.add("&4LOG 00D9: SEVERED CONNECTION ; CONDUIT MISSING ; POWERING DOWN ;");
		lore.add("&4LOG 052C: LARGE THREAT DETECTED ; ORGANIC COMPONENTS WITHERING ; FAILURE IMMINENT ;");
		lore.add("&4LOG 7E1A: TARRED REMAINS ; POWER CONDUIT ; DEEPSLATE (PRISTINELY POLISHED) ;");
		lore.add("&4LOG 7E1B: FOUR-HEADED STATUE ; INSERT POWER CONDUIT IN CENTER ;");
		lore.add(
				"&4LOG 9131: COLD REACTOR FUEL AT 24% CAPACITY ; AUXILARY PROCESSES CEASED ; GUARDIAN PRODUCTION MAXIMIZED ;");

		return lore;
	}
}
