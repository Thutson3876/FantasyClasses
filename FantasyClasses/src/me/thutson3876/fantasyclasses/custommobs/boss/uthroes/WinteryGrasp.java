package me.thutson3876.fantasyclasses.custommobs.boss.uthroes;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import me.thutson3876.fantasyclasses.custommobs.boss.MobAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class WinteryGrasp implements MobAbility {

	@Override
	public String getName() {
		return "Wintery Grasp";
	}

	@Override
	public void run(Mob entity) {
		Vector velocity = AbilityUtils.getVectorBetween2Points(entity.getLocation(), entity.getTarget().getLocation(), 1.0);
		Snowball snowball = (Snowball) entity.getWorld().spawnEntity(entity.getEyeLocation().add(velocity.multiply(0.1)), EntityType.SNOWBALL);
		snowball.setVelocity(velocity.multiply(2.0));
		snowball.setShooter(entity);
	}

}
