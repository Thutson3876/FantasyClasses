package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class UnarmoredDexterity extends AbstractAbility {

	private double damageMod = 0.95;
	
	public UnarmoredDexterity(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Unarmored Dexterity";
		this.skillPointCost = 1;
		this.maximumLevel = 7;

		this.createItemStack(Material.SHIELD);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof EntityDamageEvent))
			return false;

		EntityDamageEvent e = (EntityDamageEvent)event;
		
		if(!e.getEntity().equals(this.player))
			return false;
		
		if(AbilityUtils.hasArmor(player)) {
			return false;
		}
			
		e.setDamage(e.getDamage() * damageMod);
		return true;
	}

	@Override
	public String getInstructions() {
		return "Wear no armor";
	}

	@Override
	public String getDescription() {
		return "Take only &6" + AbilityUtils.doubleRoundToXDecimals(damageMod * 100, 1) + "% &rdamage while wearing no armor";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		damageMod = 1.0 - (0.05 * currentLevel);
	}

}
