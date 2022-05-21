package me.thutson3876.fantasyclasses.classes.seaguardian;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class SharedBlessings extends AbstractAbility {

	private double range = 8.0;
	
	public SharedBlessings(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Shared Blessings";
		this.skillPointCost = 1;
		this.maximumLevel = 2;

		this.createItemStack(Material.PRISMARINE_CRYSTALS);	
	}

	@Override
	public boolean trigger(Event event) {
		return false;
	}

	@Override
	public String getInstructions() {
		return "Swim in water while near non-mob entities";
	}

	@Override
	public String getDescription() {
		return "You are able to grant Conduit Power to your nearby allies within &6" + range + " &rblocks";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		range = 8.0 * currentLevel;
	}
	
	public double getRange() {
		return this.range;
	}

}
