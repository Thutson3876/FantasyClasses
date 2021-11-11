package me.thutson3876.fantasyclasses.classes.highroller.randomabilities;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import me.thutson3876.fantasyclasses.util.Sphere;

public class Arrowsplosion implements RandomAbility {

	@Override
	public void run(Player p) {
		List<Location> locs = Sphere.generateSphere(p.getLocation(), 1, true);
		for(Location loc : locs) {
			loc.getWorld().spawnEntity(loc, EntityType.ARROW).setVelocity(loc.toVector().subtract(p.getLocation().toVector()).multiply(2.0));
		}
	}

}
