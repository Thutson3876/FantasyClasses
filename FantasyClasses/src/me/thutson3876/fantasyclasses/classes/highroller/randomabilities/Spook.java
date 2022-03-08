package me.thutson3876.fantasyclasses.classes.highroller.randomabilities;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.FantasyClasses;

public class Spook implements RandomAbility {

	@Override
	public void run(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2));
		p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 2));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 2));
		
		p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 1.0f, 1.0f);
		p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SCREAM, 0.7f, 1.0f);
		
		Random rng = new Random();
		
		
		
		for(int i = 0; i < 300; i++) {
			int buffer = rng.nextInt(100) + 20;
			
			new BukkitRunnable() {

				@Override
				public void run() {
					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SCREAM, 0.3f + rng.nextFloat(), 1.0f);
				}
				
			}.runTaskLater(FantasyClasses.getPlugin(), buffer);
			i += buffer;
		}
		
		//make this make a bunch of different tasks with increasing times
	}

}
