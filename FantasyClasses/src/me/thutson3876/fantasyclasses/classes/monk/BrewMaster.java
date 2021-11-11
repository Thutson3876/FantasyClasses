package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class BrewMaster extends AbstractAbility {

	private int duration = 6 * 20;
	private PotionEffect resistance;
	private PotionEffect nausea;
	
	public BrewMaster(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 24 * 20;
		this.displayName = "Brewmaster";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, 0);
		nausea = new PotionEffect(PotionEffectType.CONFUSION, duration, 0);
		
		this.createItemStack(Material.POTION);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerItemConsumeEvent))
			return false;
		
		PlayerItemConsumeEvent e = (PlayerItemConsumeEvent)event;
		
		if(!e.getPlayer().equals(this.player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getItem().getType().equals(Material.POTION))
			return false;
		
		player.addPotionEffect(resistance);
		player.addPotionEffect(nausea);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Drink a potion";
	}

	@Override
	public String getDescription() {
		return "Drink a potion to gain resistance and nausea for &6" + duration / 20 + "&r seconds";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		duration = (6 * currentLevel) * 20;
		resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, 0);
		nausea = new PotionEffect(PotionEffectType.CONFUSION, duration, 0);
		if(currentLevel >= 3)
			resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, 1);
	}

}
