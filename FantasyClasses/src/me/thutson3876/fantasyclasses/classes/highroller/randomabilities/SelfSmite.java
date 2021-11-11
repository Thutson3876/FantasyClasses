package me.thutson3876.fantasyclasses.classes.highroller.randomabilities;

import org.bukkit.entity.Player;

public class SelfSmite implements RandomAbility {

	@Override
	public void run(Player p) {
		p.getWorld().strikeLightning(p.getLocation());
		p.getWorld().strikeLightning(p.getLocation());
	}

}
