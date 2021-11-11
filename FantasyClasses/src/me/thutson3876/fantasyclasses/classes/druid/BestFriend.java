package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class BestFriend extends AbstractAbility {

	public BestFriend(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 20;
		this.displayName = "Best Friend";
		this.skillPointCost = 1;
		this.maximumLevel = 1;

		this.createItemStack(Material.BEETROOT);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getDamager().equals(player))
			return false;
		
		if(!(e.getEntity() instanceof Tameable))
			return false;
		
		Tameable pet = (Tameable) e.getEntity();
		
		if(player.equals(pet.getOwner())) {
			e.setCancelled(true);
			return true;
		}
			
				
		return false;
	}

	@Override
	public String getInstructions() {
		return "Please don't hit your pets";
	}

	@Override
	public String getDescription() {
		return "Hitting your own pets causes no damage";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
