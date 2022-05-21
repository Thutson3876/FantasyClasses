package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class BeerBelly extends AbstractAbility {

	double conversion = 0.06;
	
	public BeerBelly(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Beer Belly";
		this.skillPointCost = 1;
		this.maximumLevel = 2;

		this.createItemStack(Material.HONEYCOMB_BLOCK);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageEvent))
			return false;
		
		EntityDamageEvent e = (EntityDamageEvent)event;
		
		if(!e.getEntity().equals(player))
			return false;
		
		if(AbilityUtils.hasArmor(player))
			return false;
		
		double dmg = e.getDamage();
		player.setExhaustion((float) (player.getExhaustion() + (dmg * conversion)));
		e.setDamage(dmg * (1 - conversion));
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Wear no armor";
	}

	@Override
	public String getDescription() {
		return "Convert &6" + AbilityUtils.doubleRoundToXDecimals(conversion * 100, 1) + "% &rof damage taken into exhaustion while not wearing armor";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		conversion = 0.06 * currentLevel;
	}

}
