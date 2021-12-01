package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class CulturalWisdom extends AbstractAbility {

	public CulturalWisdom(Player p) {
		super(p);
	}
	
	// use world.locateneareststructure() to find nearest structure
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30 * 20;
		this.displayName = "Cultural Wisdom";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.ENCHANTED_BOOK);
	}

	@Override
	public boolean trigger(Event event) {
		
		return false;
	}

	@Override
	public String getInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		return "Send out an ender signal that points in the direction of the nearest structure";
	}

	@Override
	public boolean getDealsDamage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		// TODO Auto-generated method stub
		
	}

}
