package me.thutson3876.fantasyclasses.classes.highroller;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class Tolerance extends AbstractAbility {

	public Tolerance(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Tolerance";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.IRON_CHESTPLATE);
	}

	@Override
	public boolean trigger(Event event) {
		if(event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
			if(e.getDamager().equals(player) && e.getEntity().equals(player))
				e.setCancelled(true);
		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "Take self-inflicted damage";
	}

	@Override
	public String getDescription() {
		return "All self-damage is negated";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
