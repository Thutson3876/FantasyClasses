package me.thutson3876.fantasyclasses.classes.alchemy;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PotionSplashEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class PotentSplash extends AbstractAbility {

	private double intensity = 0.2;
	
	public PotentSplash(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 16;
		this.displayName = "Potent Splash";
		this.skillPointCost = 1;
		this.maximumLevel = 5;

		this.createItemStack(Material.SPLASH_POTION);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PotionSplashEvent))
			return false;
		
		PotionSplashEvent e = (PotionSplashEvent)event;
		
		if(!e.getEntity().getShooter().equals(player))
			return false;
		
		for(LivingEntity ent : e.getAffectedEntities()) {
			e.setIntensity(ent, 1.0 + intensity);
		}
		
		e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 0.5f, 1.0f);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Throw a splash potion";
	}

	@Override
	public String getDescription() {
		return "Your splash potions are &6" + (intensity * 100) + "% &rmore potent";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		intensity = 0.2 * currentLevel;
	}

}
