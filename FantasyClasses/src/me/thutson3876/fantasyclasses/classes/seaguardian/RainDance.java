package me.thutson3876.fantasyclasses.classes.seaguardian;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class RainDance extends AbstractAbility {

	public RainDance(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 60 * 20;
		this.displayName = "Rain Dance";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.LIGHTNING_ROD);		
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerInteractEvent))
			return false;
		
		if(isOnCooldown())
			return false;
		
		PlayerInteractEvent e = (PlayerInteractEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		if(e.getClickedBlock() == null)
			return false;
		
		if(!e.getClickedBlock().getType().equals(Material.LIGHTNING_ROD))
			return false;
		
		if(!player.isSneaking())
			return false;
		
		World world = player.getWorld();
		boolean isClear = world.isClearWeather();
		if(isClear) {
			world.setStorm(true);
		}
		else {
			world.setClearWeatherDuration(coolDowninTicks);
		}
		
		world.strikeLightning(e.getClickedBlock().getLocation());
		return true;
	}

	@Override
	public String getInstructions() {
		return "Right-click a lightning rod while crouching";
	}

	@Override
	public String getDescription() {
		return "Command the water cycle and control the weather. This ability toggles weather on or off depending on the current conditions";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
