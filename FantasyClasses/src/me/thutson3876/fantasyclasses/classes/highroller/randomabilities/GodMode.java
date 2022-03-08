package me.thutson3876.fantasyclasses.classes.highroller.randomabilities;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GodMode implements RandomAbility {

	@Override
	public void run(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 45 * 20, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 45 * 20, 9));
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 45 * 20, 9));
		p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 45 * 20, 1));
		
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 0.8f, 1.0f);
	}

}
