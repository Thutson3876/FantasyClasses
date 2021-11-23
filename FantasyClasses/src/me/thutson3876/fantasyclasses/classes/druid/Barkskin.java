package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class Barkskin extends AbstractAbility {

	private double dmgReduction = 0.1;
	private boolean isOn = false;
	private int durationInTicks = 8 * 20;
	
	public Barkskin(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 45 * 20;
		this.displayName = "Barkskin";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.OAK_WOOD);	
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getEntity().equals(player))
			return false;
		
		if(isOnCooldown()) {
			if(isOn)
				e.setDamage(dmgReduction * e.getDamage());
			
			return false;
		}
		
		e.setDamage(dmgReduction * e.getDamage());
		
		isOn = true;
		
		new BukkitRunnable() {

			@Override
			public void run() {
				isOn = false;
				
			}
			
		}.runTaskLater(plugin, durationInTicks);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Take damage from an entity";
	}

	@Override
	public String getDescription() {
		return "Take &6" + (dmgReduction * 100) + "% &rless damage for 8 seconds";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		dmgReduction = 0.1 * currentLevel;
	}

}
