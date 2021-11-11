package me.thutson3876.fantasyclasses.classes.alchemy;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.PotionList;

public class PotentBrewing extends AbstractAbility {

	private double chance = 0.1;
	private int maxAmp = 1;
	private int minAmp = 0;
	private int maxDuration = 20;
	private int minDuration = 10;
	private boolean triggerTwice = false;
	private Random rng = new Random();

	public PotentBrewing(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 16;
		this.displayName = "Potent Brewing";
		this.skillPointCost = 1;
		this.maximumLevel = 10;

		this.createItemStack(Material.NETHER_WART);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof BrewEvent))
			return false;

		BrewEvent e = (BrewEvent) event;

		UUID uuid = plugin.getBrewTracker().getTracker().get(e.getBlock().getLocation());

		if (!player.getUniqueId().equals(uuid))
			return false;

		if (rng.nextDouble() > chance)
			return false;

		ItemStack[] newContents = new ItemStack[e.getContents().getStorageContents().length];
		for (int l = 0; l < (triggerTwice ? 1 : 0); l++) {
			for (ItemStack i : e.getContents().getStorageContents()) {
				int j = 0;
				if (i == null || !i.getType().equals(Material.POTION)) {
					newContents[j] = i;
					j++;
					continue;
				}

				ItemMeta meta = i.getItemMeta();
				if (!(meta instanceof PotionMeta)) {
					newContents[j] = i;
					j++;
					continue;
				}

				PotionMeta potMeta = (PotionMeta) meta;
				if (potMeta.hasCustomEffects() && !triggerTwice)
					continue;

				PotionEffectType type = potMeta.getBasePotionData().getType().getEffectType();
				List<PotionEffectType> buffs = PotionList.BUFF.getPotList();
				List<PotionEffectType> debuffs = PotionList.DEBUFF.getPotList();

				PotionEffectType newType = null;
				if (buffs.contains(type)) {
					do {
						newType = buffs.get(buffs.size());
					} while (newType.equals(type) || potMeta.hasCustomEffect(newType));

					potMeta.addCustomEffect(new PotionEffect(newType, (rng.nextInt(maxDuration) + minDuration) * 20,
							rng.nextInt(maxAmp) + minAmp), false);

				} else if (debuffs.contains(type)) {
					do {
						newType = debuffs.get(debuffs.size());
					} while (newType.equals(type) || potMeta.hasCustomEffect(newType));

					potMeta.addCustomEffect(new PotionEffect(debuffs.get(debuffs.size()),
							(rng.nextInt(maxDuration) + minDuration) * 20, rng.nextInt(maxAmp) + minAmp), false);
				}

				i.setItemMeta(potMeta);
				newContents[j] = i;
				j++;
				continue;
			}
		}

		return true;
	}

	@Override
	public String getInstructions() {
		return "Brew a potion";
	}

	@Override
	public String getDescription() {
		return "When brewing, gain a &6" + chance * 100
				+ "% &rchance to give it a random additional effect. Bonus duration at level 4 and bonus potency at level 7. Level 10 applies this effect twice";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		chance = 0.1 * currentLevel;
		if (currentLevel > 3) {
			maxDuration = 40;
			minDuration = 30;
		}
		if (currentLevel > 6) {
			maxAmp = 3;
			minAmp = 1;
		}
		if (currentLevel > 9) {
			triggerTwice = true;
		}

	}

}
