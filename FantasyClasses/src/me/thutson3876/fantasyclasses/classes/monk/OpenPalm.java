package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class OpenPalm extends AbstractAbility {

	private static double damageModPerLevel = 0.75;
	private double damageMod = damageModPerLevel;
	
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
				|| player.getInventory().getItemInMainHand().getType().equals(Material.AIR)  || player.getInventory().getItemInMainHand().getType().equals(Material.STICK)))
			return false;
		
		e.setDamage(e.getDamage() + damageMod);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Attack with empty hand";
	}

	@Override
	public String getDescription() {
		return "Your attacks deal &6" + (damageMod * 2) + "&r extra damage";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		damageMod = damageModPerLevel * currentLevel;
	}

	public double getDamageModifier() {
		return damageMod;
	}
	
	public static double getDamageModPerLevel() {
		return damageModPerLevel;
	}
}
