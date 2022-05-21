package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.collectible.Collectible;

public class DruidicInscription extends AbstractAbility {

	public DruidicInscription(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Druidic Inscription";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.FLOWERING_AZALEA_LEAVES);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof BlockBreakEvent))
			return false;
		
		BlockBreakEvent e = (BlockBreakEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		Collectible.DRUIDIC_INSCRIPTION.onBreakEvent(e);
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Break a naturally spawned azalea leaves that are out of place and right click the dropped knowledge to gain EXP";
	}

	@Override
	public String getDescription() {
		return "Explore the sprawling forests for leaves that don't quite belong...";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
