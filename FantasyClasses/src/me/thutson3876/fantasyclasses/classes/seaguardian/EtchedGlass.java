package me.thutson3876.fantasyclasses.classes.seaguardian;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.collectible.Collectible;

public class EtchedGlass extends AbstractAbility {

	public EtchedGlass(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Etched Glass";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.GLASS);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof BlockBreakEvent))
			return false;
		
		BlockBreakEvent e = (BlockBreakEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		Collectible.ETCHED_GLASS.onBreakEvent(e);
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Break a naturally spawned glass block surrounded by ice and right click the dropped knowledge to gain EXP";
	}

	@Override
	public String getDescription() {
		return "Explore the frozen wastes to unlock lost knowledge...";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
