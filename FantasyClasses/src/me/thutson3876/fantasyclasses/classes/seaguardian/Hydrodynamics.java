package me.thutson3876.fantasyclasses.classes.seaguardian;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class Hydrodynamics extends AbstractAbility {

	private float swimSpeedBonus = 0.04f;
	
	public Hydrodynamics(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Hydrodynamics";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.SALMON);	
	}

	@Override
	public boolean trigger(Event event) {
		return false;
	}

	@Override
	public String getInstructions() {
		return "Swim in water";
	}

	@Override
	public String getDescription() {
		return "Your slick and slender build allows you to increase your swim speed by &6" + 20 * currentLevel + "%";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		swimSpeedBonus = 0.04f * currentLevel;
	}
	
	@Override
	public void deInit() {
		player.setWalkSpeed(0.2f);
	}
	
	public float getSwimSpeedBonus() {
		return this.swimSpeedBonus;
	}
}
