package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class WayOfTheBlade extends AbstractAbility {

	public WayOfTheBlade(Player p) {
		super(p);
	}
	
	//convert half of open palm damage increase to use with a weapon
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Way Of The Blade";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.IRON_SWORD);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getDamager().equals(this.player))
			return false;
		
		if ((player.getInventory().getItemInMainHand() == null
				|| player.getInventory().getItemInMainHand().getType().equals(Material.AIR)))
			return false;
		
		double damageMod = 0.0;
		
		for(Ability abil : fplayer.getAbilities()) {
			if(abil instanceof OpenPalm)
				damageMod = ((OpenPalm)abil).getDamageModifier();
		}
		
		e.setDamage(e.getDamage() + damageMod);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Attack with a weapon";
	}

	@Override
	public String getDescription() {
		return "Your weapon attacks deal bonus damage equal to half of your &6Open Palm &rmodifier";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
