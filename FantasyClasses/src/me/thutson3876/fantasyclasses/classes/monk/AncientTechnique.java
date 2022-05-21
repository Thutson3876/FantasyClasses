package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.collectible.Collectible;

public class AncientTechnique extends AbstractAbility {

	public AncientTechnique(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Ancient Technique";
		this.skillPointCost = 1;
		this.maximumLevel = 1;

		this.createItemStack(Material.LECTERN);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof BlockBreakEvent))
			return false;
		
		BlockBreakEvent e = (BlockBreakEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		Collectible.ANCIENT_TECHNIQUE.onBreakEvent(e);
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Break a naturally spawned lecturn in high reaches and right click the dropped knowledge to gain EXP";
	}

	@Override
	public String getDescription() {
		return "Explore the highest peaks to find lecterns filled with ancient knowledge...";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}
	
}
