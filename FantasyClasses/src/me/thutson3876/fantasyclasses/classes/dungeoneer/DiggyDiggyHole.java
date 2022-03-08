package me.thutson3876.fantasyclasses.classes.dungeoneer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;

public class DiggyDiggyHole extends AbstractAbility implements Bindable {

	private int duration = 5 * 20;
	private Material type = null;
	private PotionEffect haste;
	
	public DiggyDiggyHole(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 60 * 20;
		this.displayName = "Diggy Diggy Hole";
		this.skillPointCost = 3;
		this.maximumLevel = 2;
		haste = new PotionEffect(PotionEffectType.FAST_DIGGING, duration, 1);

		this.createItemStack(Material.FEATHER);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerInteractEvent))
			return false;
		
		PlayerInteractEvent e = (PlayerInteractEvent)event;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		if(e.getItem() == null)
			return false;
		
		if(!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return false;
		
		if(!e.getItem().getType().equals(type))
			return false;
		
		player.addPotionEffect(haste);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Right-click with bound item type";
	}

	@Override
	public String getDescription() {
		return "Gain a burst of speed and dig faster for &6" + (duration / 20) + " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		duration = (5 * currentLevel) * 20;
		haste = new PotionEffect(PotionEffectType.FAST_DIGGING, duration, 1);
	}

	@Override
	public Material getBoundType() {
		return type;
	}

	@Override
	public void setBoundType(Material type) {
		this.type = type;
	}

}
