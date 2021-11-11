package me.thutson3876.fantasyclasses.playermanagement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.thutson3876.fantasyclasses.FantasyClasses;

public abstract class AbstractTracker implements Tracker, Listener {

	protected static final FantasyClasses plugin = FantasyClasses.getPlugin();
	protected Map<Location, UUID> tracker = new HashMap<>();
	protected int slot = 0;
	protected InventoryType type = null;
	
	public AbstractTracker() {
		plugin.registerEvents(this);
	}
	
	public Map<Location, UUID> getTracker(){
		return this.tracker;
	}
	
	public UUID remove(Location loc) {
		return this.tracker.remove(loc);
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		if(!event.getInventory().getType().equals(type))
			return;
		
		if(!(event.getWhoClicked() instanceof Player))
			return;
		
		Player p = (Player) event.getWhoClicked();
		
		if(event.getAction().equals(InventoryAction.PLACE_ALL) || event.getAction().equals(InventoryAction.PLACE_ONE) || event.getAction().equals(InventoryAction.PLACE_SOME)) {
			if(!(event.getSlot() == slot))
				return;
			
			for(Location loc : tracker.keySet()) {
				if(event.getInventory().getLocation().equals(loc)) {
					tracker.remove(loc);
				}
			}
			
			tracker.put(event.getInventory().getLocation(), p.getUniqueId());
		}
		else if(event.getAction().equals(InventoryAction.PICKUP_ALL)) {
			if(!(event.getSlot() == slot))
				return;
			
			for(Location loc : tracker.keySet()) {
				if(event.getInventory().getLocation().equals(loc)) {
						tracker.remove(loc);
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		for(Location loc : tracker.keySet()) {
			if(event.getBlock().getLocation().equals(loc)) {
				tracker.remove(loc);
			}
		}
	}
}
