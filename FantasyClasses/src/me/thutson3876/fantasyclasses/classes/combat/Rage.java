package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class Rage extends AbstractAbility {

	private int duration = 8 * 20;
	private PotionEffect absorb;
	private PotionEffect resist;
	private PotionEffect strength;
	
	public Rage(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 12 * 20;
		this.displayName = "Rage";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		
		this.createItemStack(Material.CRACKED_DEEPSLATE_BRICKS);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent)) {
			return false;
		}
		
		if(this.isOnCooldown())
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getEntity().equals(this.player))
			return false;
		
		player.addPotionEffect(absorb);
		if(currentLevel > 1)
			player.addPotionEffect(resist);
		if(currentLevel > 2)
			player.addPotionEffect(strength);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Take damage from an entity";
	}

	@Override
	public String getDescription() {
		return "Become enraged after receiving damage and gain a potion effect per level. 1: Absorption 2: Resistance 3: Strength";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		this.absorb = new PotionEffect(PotionEffectType.ABSORPTION, duration, 0);
		this.resist = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, 0);
		this.strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration, 0);
	}

}
