package me.thutson3876.fantasyclasses.classes.alchemy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PotionSplashEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class SunderingSplash extends AbstractAbility {

	private float power = 1.0f;
	
	public SunderingSplash(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 16;
		this.displayName = "Sundering Splash";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.CREEPER_HEAD);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PotionSplashEvent))
			return false;
		
		PotionSplashEvent e = (PotionSplashEvent)event;
		
		if(!e.getEntity().getShooter().equals(player))
			return false;
		
		e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), power);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Throw a splash potion";
	}

	@Override
	public String getDescription() {
		return "Your splash potions explode upon impact with a force of &6" + power * 1000 + " &rnewtons";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		power = 1.0f * currentLevel;
	}

}
