package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class OpenPalm extends AbstractAbility {

	private double damageMod = 3.0;
	
	public OpenPalm(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Open Palm";
		this.skillPointCost = 1;
		this.maximumLevel = 4;
		
		this.createItemStack(Material.ACACIA_TRAPDOOR);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getDamager().equals(this.player))
			return false;
		
		if (!(player.getInventory().getItemInMainHand() == null
				|| player.getInventory().getItemInMainHand().getType().equals(Material.AIR)))
			return false;
		
		e.setDamage(damageMod);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Attack an entity with an empty hand";
	}

	@Override
	public String getDescription() {
		return "Your attacks deal &6" + damageMod + "&r extra damage";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		damageMod = 3.0 * currentLevel;
	}

}
