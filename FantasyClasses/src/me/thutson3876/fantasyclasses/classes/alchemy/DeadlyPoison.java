package me.thutson3876.fantasyclasses.classes.alchemy;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class DeadlyPoison extends AbstractAbility {

	private int durationInTicks = 15;
	private PotionEffect poison;

	public DeadlyPoison(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 15;
		this.displayName = "Deadly Poison";
		this.skillPointCost = 1;
		this.maximumLevel = 2;
		poison = new PotionEffect(PotionEffectType.POISON, durationInTicks, 0);

		this.createItemStack(Material.GREEN_DYE);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof EntityDamageByEntityEvent))
			return false;

		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

		if (!e.getDamager().equals(player))
			return false;

		if (!(e.getEntity() instanceof LivingEntity))
			return false;

		if(e.getCause().equals(DamageCause.POISON))
			return false;
		
		LivingEntity target = (LivingEntity) e.getEntity();

		if (target.hasPotionEffect(PotionEffectType.POISON)) {
			PotionEffect currentEffect = target.getPotionEffect(PotionEffectType.POISON);
			int newAmp = currentEffect.getAmplifier() + 1;
			if(newAmp > 9)
				newAmp = 9;
			int newDuration = currentEffect.getDuration() + durationInTicks;
			if(newDuration > 30 * 20)
				newDuration = 30 * 20;
			
			target.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
					newDuration,
					newAmp));
		} else {
			target.addPotionEffect(poison);
		}

		return true;
	}

	@Override
	public String getInstructions() {
		return "Attack an entity";
	}

	@Override
	public String getDescription() {
		return "Your attacks apply poison for &6" + durationInTicks / 20.0
				+ "&r seconds. If the target is already poisoned, increase its potency and reset its duration";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		durationInTicks = 15 * currentLevel;
		poison = new PotionEffect(PotionEffectType.POISON, durationInTicks, 0);
	}

}
