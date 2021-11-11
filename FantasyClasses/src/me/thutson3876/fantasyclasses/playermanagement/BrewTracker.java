package me.thutson3876.fantasyclasses.playermanagement;

import org.bukkit.event.inventory.InventoryType;

public class BrewTracker extends AbstractTracker {
	
	public BrewTracker() {
		super();
		
		this.slot = 3;
		this.type = InventoryType.BREWING;
	}
}
