package me.thutson3876.fantasyclasses.classes.seaguardian;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class Conductive extends AbstractAbility {

	private double dmgMod = 0.33;
	
	public Conductive(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Conductive";
		this.skillPointCost = 1;
		this.maximumLevel = 4;

		this.createItemStack(Material.COPPER_INGOT);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageEvent))
			return false;
		
		EntityDamageEvent e = (EntityDamageEvent)event;
		
		if(!e.getEntity().equals(player))
			return false;
		
		if(!e.getCause().equals(DamageCause.LIGHTNING))
			return false;
		
		e.setDamage(e.getDamage() * (1 - dmgMod));
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Take lightning damage";
	}

	@Override
	public String getDescription() {
		return "When you take lightning damage, it is reduced by &6" + AbilityUtils.doubleRoundToXDecimals(dmgMod * 100, 1) + "%";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		dmgMod = 0.33 * currentLevel;
	}

}
