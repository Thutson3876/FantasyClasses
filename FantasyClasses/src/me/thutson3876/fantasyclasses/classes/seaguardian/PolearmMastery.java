package me.thutson3876.fantasyclasses.classes.seaguardian;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class PolearmMastery extends AbstractAbility {

	private double dmg = 1.0;
	
	public PolearmMastery(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Polearm Mastery";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.TRIDENT);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof ProjectileHitEvent))
			return false;
		
		ProjectileHitEvent e = (ProjectileHitEvent)event;
		
		if(e.getEntity().getShooter() == null || !e.getEntity().getShooter().equals(player))
			return false;
		
		if(!e.getEntityType().equals(EntityType.TRIDENT))
			return false;
		
		Entity hit = e.getHitEntity();
		if(hit == null)
			return false;
		
		if(!(hit instanceof LivingEntity))
			return false;
		
		LivingEntity livingHit = (LivingEntity)hit;
		if(livingHit.isDead())
			return false;
		
		livingHit.damage(dmg * (player.isInWater() ? 2 : 1));
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Throw a trident";
	}

	@Override
	public String getDescription() {
		return "Your thrown tridents strike with precision, dealing &6" + dmg + " &r bonus damage. This extra damage is doubled if you are in water";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		dmg = 1.0 * currentLevel;
	}

}
