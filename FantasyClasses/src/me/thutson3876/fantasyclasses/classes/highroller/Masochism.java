package me.thutson3876.fantasyclasses.classes.highroller;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class Masochism extends AbstractAbility {

	private float yield = 2.0f;

	public Masochism(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Masochism";
		this.skillPointCost = 1;
		this.maximumLevel = 2;

		this.createItemStack(Material.CREEPER_HEAD);
	}

	@Override
	public boolean trigger(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
			if (e.getDamager().equals(player) && e.getEntity().equals(player))
				spawnExplosion();
		}
		
		return false;
	}

	private void spawnExplosion() {
		player.getWorld().createExplosion(player.getLocation(), yield, false, false, player);
	}

	@Override
	public String getInstructions() {
		return "Take self-inflicted damage";
	}

	@Override
	public String getDescription() {
		return "Upon taking self-damage, explode in a &6" + yield + " &rblock radius.";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		yield = 1.0f + 1.0f * currentLevel;
	}

}
