package me.thutson3876.fantasyclasses.classes.dungeoneer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.DamageCauseList;

public class StoneSkin extends AbstractAbility {

	private double dmgReduction = 0.03;
	
	public StoneSkin(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Stoneskin";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		
		this.createItemStack(Material.SMOOTH_STONE);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageEvent))
			return false;
		
		EntityDamageEvent e = (EntityDamageEvent)event;
		
		if(!e.getEntity().equals(player))
			return false;
		
		if(!DamageCauseList.PHYSICAL.contains(e.getCause()))
			return false;
		
		e.setDamage((1.0 - dmgReduction) * e.getDamage());
		
		World world = player.getWorld();
		Location loc = player.getLocation();
		
		world.playSound(loc, Sound.BLOCK_DRIPSTONE_BLOCK_BREAK, 0.5f, 0.8f);
		world.spawnParticle(Particle.ASH, loc, currentLevel * 2 + 4);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Take damage";
	}

	@Override
	public String getDescription() {
		return "Take &6" + AbilityUtils.doubleRoundToXDecimals(dmgReduction * 100, 2) + "% &rless physical damage";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		dmgReduction = 0.03 * currentLevel;
	}

}
