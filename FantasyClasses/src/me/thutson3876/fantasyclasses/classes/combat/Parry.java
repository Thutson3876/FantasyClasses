package me.thutson3876.fantasyclasses.classes.combat;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class Parry extends AbstractAbility {

	private int duration = 4;
	private Entity target = null;
	
	public Parry(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 3;
		this.displayName = "Parry";
		this.skillPointCost = 2;
		this.maximumLevel = 1;
		
		this.createItemStack(Material.GOLDEN_SWORD);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;

		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(e.getDamager().equals(this.player)) {
			if(isOnCooldown())
				return false;
			
			BukkitRunnable task = new BukkitRunnable() {

				@Override
				public void run() {
					target = null;
				}
				
			};
			
			target = e.getEntity();
			task.runTaskLater(plugin, duration);
			return true;
		}
		else if(e.getEntity().equals(this.player)) {
			if(target == null)
				return false;
			
			if(!e.getDamager().equals(target))
				return false;
			
			List<Entity> entitiesInSight = AbilityUtils.getEntitiesInAngle(player, 1.4, 6.0);
			if(!entitiesInSight.contains(e.getDamager()))
				return false;
			
			e.setCancelled(true);
		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "Attack an entity as they are attacking you";
	}

	@Override
	public String getDescription() {
		return "Nullifies damage taken if you attack at the same time";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
