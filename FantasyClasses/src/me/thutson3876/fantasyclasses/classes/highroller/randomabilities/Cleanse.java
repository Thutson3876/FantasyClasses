package me.thutson3876.fantasyclasses.classes.highroller.randomabilities;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cleanse implements RandomAbility {

	@Override
	public void run(Player p) {
		p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		p.setFoodLevel(20);
		p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 200, 0));
	}

}
