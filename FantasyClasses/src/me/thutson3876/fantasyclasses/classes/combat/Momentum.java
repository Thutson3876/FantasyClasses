package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class Momentum extends AbstractAbility {

	private double dmgMod = 1.0;
	
	public Momentum(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Momentum";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		
		this.createItemStack(Material.FEATHER);
	}
	
	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent)) {
			return false;
		}
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getDamager().equals(this.player))
			return false;
		
		if(player.isSprinting()) {
			e.setDamage(e.getDamage() * dmgMod);
			return true;
		}
			
		return false;
	}

	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public String getName() {
		return displayName;
	}

	@Override
	public String getInstructions() {
		return "Attack while sprinting";
	}

	@Override
	public String getDescription() {
		return "Your attacks while sprinting deal &6" + dmgMod + "&r times more damage";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return currentLevel > 0;
	}

	@Override
	public void applyLevelModifiers() {
		dmgMod = 1.0 + (0.2 * currentLevel);
	}

}
