package me.thutson3876.fantasyclasses.classes.dungeoneer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class HotHands extends AbstractAbility {

	private int duration = 2 * 20;
	
	public HotHands(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 12 * 20;
		this.displayName = "Hot Hands";
		this.skillPointCost = 2;
		this.maximumLevel = 3;

		this.createItemStack(Material.LAVA_BUCKET);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getDamager().equals(player))
			return false;
		
		if(!e.getCause().equals(DamageCause.ENTITY_ATTACK))
			return false;
		
		e.getEntity().setFireTicks(duration);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Attack an entity";
	}

	@Override
	public String getDescription() {
		return "Attacking an entity causes them to catch fire for &6" + (duration / 20) + " &rseconds. Has a cooldown of &6" + (this.coolDowninTicks / 20) + " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		duration = (1 + currentLevel) * 20;
		this.coolDowninTicks = (14 - (2 * currentLevel)) * 20;
	}

}
