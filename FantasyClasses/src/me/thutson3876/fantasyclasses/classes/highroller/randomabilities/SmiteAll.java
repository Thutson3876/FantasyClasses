package me.thutson3876.fantasyclasses.classes.highroller.randomabilities;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class SmiteAll implements RandomAbility {

	@Override
	public void run(Player p) {
		for(Entity e : AbilityUtils.getEntitiesInAngle(p, 1.4, 8.0)) {
			e.getWorld().strikeLightning(e.getLocation());
		}
	}

}
