package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class AncientTechniques extends AbstractAbility {

	public AncientTechniques(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Ancient Techniques";
		this.skillPointCost = 1;
		this.maximumLevel = 1;
		
		this.createItemStack(Material.LECTERN);		
	}

	@Override
	public boolean trigger(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getInstructions() {
		return "Find ";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
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
