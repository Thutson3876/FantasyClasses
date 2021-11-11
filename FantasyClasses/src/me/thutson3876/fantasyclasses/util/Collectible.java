package me.thutson3876.fantasyclasses.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Collectible {

	ANCIENT_TECHNIQUE(Material.LECTERN, Material.BOOK, "Tornado Technique"), MINING_SCHEMATICS(Material.SKELETON_SKULL, Material.BONE, "Fast Fracking");
	
	private final Material type;
	private List<String> possibleLore = new ArrayList<>();
	private ItemStack drop;
	
	private Collectible(Material type, Material dropType, String... possibleLore) {
		Random rng = new Random();
		ItemStack item = new ItemStack(dropType);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatUtils.chat("&6" + ChatUtils.toRegularName(this.name())));
		for(String s : possibleLore) {
			this.possibleLore.add(s);
		}
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatUtils.chat("&3" + this.possibleLore.get(rng.nextInt(this.possibleLore.size()))));
		meta.setLore(lore);
		item.setItemMeta(meta);
		this.drop = item;
		this.type = Material.LECTERN;
	}

	public ItemStack getDrop() {
		return drop;
	}

	public Material getType() {
		return type;
	}

	public List<String> getPossibleLore() {
		return possibleLore;
	}
	
	public static Collectible getMatchingCollectible(Material type) {
		for(Collectible c : Collectible.values()) {
			if(c.getType().equals(type))
				return c;
		}
		
		return null;
	}
}
