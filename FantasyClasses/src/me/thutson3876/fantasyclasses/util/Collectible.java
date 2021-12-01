package me.thutson3876.fantasyclasses.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Collectible {

	ANCIENT_TECHNIQUE(Material.LECTERN), MINING_SCHEMATICS(Material.SKELETON_SKULL);

	private final Material type;
	private static final Random rng = new Random();

	private Collectible(Material type) {
		this.type = type;
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
		lore.add(ChatUtils.chat("Contained Experience: &6" + (int) (rng.nextGaussian() * 15 + 30)));
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
}
