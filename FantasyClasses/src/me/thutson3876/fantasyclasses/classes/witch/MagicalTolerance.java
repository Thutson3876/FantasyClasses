package me.thutson3876.fantasyclasses.classes.witch;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.DamageCauseList;

public class MagicalTolerance extends AbstractAbility {

	private double resist = 0.1;
	
	public MagicalTolerance(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Magical Tolerance";
		this.skillPointCost = 1;
		this.maximumLevel = 5;

		this.createItemStack(Material.BLAZE_POWDER);		
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageEvent))
			return false;
		
		EntityDamageEvent e = (EntityDamageEvent)event;
		
		if(!e.getEntity().equals(player))
			return false;
		
		if(!DamageCauseList.MAGICAL.getDamageCauseList().contains(e.getCause()))
			return false;
		
		e.setDamage(e.getDamage() * (1.0 - resist));
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Take magical damage";
	}

	@Override
	public String getDescription() {
		return "Whenever you take magical damage, reduce the amount by &6" + resist * 100.0 + "%";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		this.resist = 0.1 * currentLevel;
	}

}
