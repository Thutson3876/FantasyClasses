package me.thutson3876.fantasyclasses.classes.witch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Scalable;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class WitchHunt extends AbstractAbility implements Scalable {

	private Random rng = new Random();
	private int magicka = 100;

	public WitchHunt(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Witch Hunt";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.IRON_AXE);
	}

	@Override
	public boolean trigger(Event event) {
		 if(!isEnabled()) return false;

		if (!(event instanceof EntityDeathEvent))
			return false;

		EntityDeathEvent e = (EntityDeathEvent) event;

		LivingEntity ent = e.getEntity();

		if (ent.getKiller() == null)
			return false;

		if (!ent.getKiller().equals(player))
			return false;

		if (!ent.getType().equals(EntityType.WITCH))
			return false;

		Collection<ItemStack> drops = e.getDrops();
		if (drops == null)
			drops = new ArrayList<>();

		boolean isLucky = false;
		int chance = rng.nextInt(100);
		if (chance > 30) {
			isLucky = true;
			int i = rng.nextInt(3);
			if(i == 0) {
				if (!drops.isEmpty()) {
					drops.addAll(drops);
					drops.addAll(drops);
				}

				e.setDroppedExp(e.getDroppedExp() * 5);
			}
			else if(i == 1) {
				magicka += 5;
			}	
			else if(i == 2) {
				drops.add(AbilityUtils.generateRandomWitchesBrew());
			}
		}

		magicka += 5;

		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 0.7f, 1.2f);

		return isLucky;
	}

	@Override
	public String getInstructions() {
		return "Kill a witch";
	}

	@Override
	public String getDescription() {
		return "Kill other witches to absorb their power and attain their possessions. Your current magicka level: &6"
				+ magicka;
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

	@Override
	public int getScalableValue() {
		return magicka;
	}

	@Override
	public void setScalableValue(int amt) {
		magicka = amt;
	}

	@Override
	public String getScalableValueName() {
		return "magicka";
	}

}
