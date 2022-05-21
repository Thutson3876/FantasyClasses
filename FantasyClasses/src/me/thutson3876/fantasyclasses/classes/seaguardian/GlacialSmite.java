package me.thutson3876.fantasyclasses.classes.seaguardian;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class GlacialSmite extends AbstractAbility {

	private int freezeAmt = 40;
	private int duration = 2 * 15;
	private int amp = 0;
	private PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, duration, amp);
	private PotionEffect fatigue = new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, amp);
	
	public GlacialSmite(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0 * 20;
		this.displayName = "Glacial Smite";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.ICE);	
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getDamager().equals(player))
			return false;
		
		Entity victim = e.getEntity();
		victim.setFreezeTicks(victim.getFreezeTicks() + freezeAmt);
		
		if(victim instanceof LivingEntity)
			applyPotionEffects((LivingEntity)victim);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Attack an entity";
	}

	@Override
	public String getDescription() {
		return "Your attacks chill your targets applying freeze, slowness, and mining fatigue for &6" + duration / 20 + " &r seconds.";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		duration = (currentLevel) * 10;
		
		freezeAmt = 40 + (20 * currentLevel);
		
		slow = new PotionEffect(PotionEffectType.SLOW, duration, amp);
		fatigue = new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, amp);
	}

	private void applyPotionEffects(LivingEntity ent) {
		if(ent.isDead())
			return;
		
		ent.addPotionEffect(slow);
		ent.addPotionEffect(fatigue);
	}
	
}
