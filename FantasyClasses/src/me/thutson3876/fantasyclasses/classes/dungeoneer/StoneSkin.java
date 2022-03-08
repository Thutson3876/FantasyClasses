package me.thutson3876.fantasyclasses.classes.dungeoneer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class StoneSkin extends AbstractAbility {

	private double dmgReduction = 0.03;
	//private boolean isOn = false;
	//private int durationInTicks = 10 * 20;
	
	public StoneSkin(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Stoneskin";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		
		this.createItemStack(Material.STONE_BRICKS);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getEntity().equals(player))
			return false;
		
		/*if(isOnCooldown()) {
			if(isOn)
				e.setDamage(dmgReduction * e.getDamage());
			
			return false;
		}*/
		
		e.setDamage((1.0 - dmgReduction) * e.getDamage());
		
		World world = player.getWorld();
		Location loc = player.getLocation();
		
		world.playSound(loc, Sound.BLOCK_DRIPSTONE_BLOCK_BREAK, 0.5f, 0.8f);
		world.spawnParticle(Particle.ASH, loc, currentLevel * 2);
		
		/*isOn = true;
		
		new BukkitRunnable() {

			@Override
			public void run() {
				isOn = false;
				
			}
			
		}.runTaskLater(plugin, durationInTicks);*/
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Take damage from an entity";
	}

	@Override
	public String getDescription() {
		return "Take &6" + AbilityUtils.doubleRoundToXDecimals(dmgReduction * 100, 1) + "% &rless damage from all sources";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		dmgReduction = 0.03 * currentLevel;
		//durationInTicks = (4 + 3 * currentLevel) * 20;
	}

}
