package me.thutson3876.fantasyclasses.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.thutson3876.fantasyclasses.FantasyClasses;

public class PlayerRegistryListener implements Listener {

	private static final FantasyClasses plugin = FantasyClasses.getPlugin();

	public PlayerRegistryListener() {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		plugin.getPlayerManager().addPlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent e) {
		plugin.getPlayerManager().removePlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerKickEvent(PlayerKickEvent e) {
		plugin.getPlayerManager().removePlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onBrewEvent(BrewEvent e) {
		if(e.isCancelled())
			return;
		
		plugin.getBrewTracker().remove(e.getBlock().getLocation());
	}
}
