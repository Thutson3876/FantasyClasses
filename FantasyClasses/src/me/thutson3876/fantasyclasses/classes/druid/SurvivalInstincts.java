package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class SurvivalInstincts extends AbstractAbility {

	private int duration = 6 * 20;
	private PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, duration, 0);
	private PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration, 0);
	private PotionEffect haste = new PotionEffect(PotionEffectType.FAST_DIGGING, duration, 0);
	
	public SurvivalInstincts(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 80 * 20;
		this.displayName = "Survival Instincts";
		this.skillPointCost = 1;
		this.maximumLevel = 1;

		this.createItemStack(Material.SKELETON_SKULL);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getEntity().equals(player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		double health = player.getHealth();
		
		if(e.getFinalDamage() < health)
			return false;
		
		e.setDamage(health - 2.0);
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.2f, 1.0f);
		player.getWorld().spawnParticle(Particle.FALLING_LAVA, player.getLocation(), 4);
		
		player.addPotionEffect(speed);
		player.addPotionEffect(strength);
		player.addPotionEffect(haste);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Take lethal damage";
	}

	@Override
	public String getDescription() {
		return "Upon taking lethal damage, negate it and gain speed, strength, and haste for &6" + (duration / 20) + " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
