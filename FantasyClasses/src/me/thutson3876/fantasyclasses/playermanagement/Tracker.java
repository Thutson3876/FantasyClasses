package me.thutson3876.fantasyclasses.playermanagement;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

public interface Tracker {

	UUID remove(Location loc);
	
	Map<Location, UUID> getTracker();
	
}
