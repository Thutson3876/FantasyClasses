package me.thutson3876.fantasyclasses.classes.highroller.randomabilities;

import org.bukkit.entity.Player;

public class SelfExplosion implements RandomAbility {

	@Override
	public void run(Player p) {
		p.getWorld().createExplosion(p.getLocation(), 4.0f, true, true);
	}

}
