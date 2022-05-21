package me.thutson3876.fantasyclasses.classes.highroller;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;

public class WondrousMagic extends AbstractAbility implements Bindable {

	private Material boundtype = null;
	
	public WondrousMagic(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 1 * 20;
		this.displayName = "Wondrous Magic";
		this.skillPointCost = 2;
		this.maximumLevel = 1;	
		
		this.createItemStack(Material.BLAZE_POWDER);		
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerInteractEvent))
			return false;
		
		PlayerInteractEvent e = (PlayerInteractEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return false;
		
		if(!e.getMaterial().equals(boundtype))
			return false;
		
		WondrousTable.roll(player);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Right click while holding bound item type";
	}

	@Override
	public String getDescription() {
		return "Cast a random effect. WARNING: DO NOT USE NEAR BUILDS OR IMPORTANT AREAS";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
	}

	@Override
	public Material getBoundType() {
		return boundtype;
	}

	@Override
	public void setBoundType(Material type) {
		this.boundtype = type;
	}

	
}
