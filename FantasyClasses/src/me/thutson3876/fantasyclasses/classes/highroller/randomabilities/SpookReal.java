package me.thutson3876.fantasyclasses.classes.highroller.randomabilities;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.thutson3876.fantasyclasses.util.Sphere;

public class SpookReal implements RandomAbility {

	@Override
	public void run(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2));
		p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 2));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 2));
		
		p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 1.0f, 1.0f);
		p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SCREAM, 0.7f, 1.0f);
		
		List<Location> locs = Sphere.generateSphere(p.getLocation(), 8, true);
		Random rng = new Random();
		for(int i = 0; i < 5; i++) {
			p.getWorld().spawnEntity(locs.get(rng.nextInt(locs.size())), EntityType.GHAST);
		}

	}

}
