package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class BalancedLanding extends AbstractAbility {

	private double dmgMod = 0.25;
	
	public BalancedLanding(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Balanced Landing";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		
		this.createItemStack(Material.FEATHER);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof EntityDamageEvent))
			return false;

		EntityDamageEvent e = (EntityDamageEvent)event;
		
		if(!e.getEntity().equals(this.player))
			return false;
		
		if(!e.getCause().equals(DamageCause.FALL))
			return false;
		
		if(AbilityUtils.hasArmor(player)) {
			return false;
		}
			
		e.setDamage(e.getDamage() * (1 - dmgMod));
		return true;
	}

	@Override
	public String getInstructions() {
		return "Take fall damage without wearing armor";
	}

	@Override
	public String getDescription() {
		return "When you take fall damage, reduce it by &6" + AbilityUtils.doubleRoundToXDecimals(dmgMod * 100, 1) + "% &rwhile not wearing armor";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		dmgMod = 0.25 * currentLevel;
	}

}
