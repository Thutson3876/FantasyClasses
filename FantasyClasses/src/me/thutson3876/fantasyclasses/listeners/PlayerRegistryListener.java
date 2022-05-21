package me.thutson3876.fantasyclasses.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.collectible.Collectible;
import me.thutson3876.fantasyclasses.playermanagement.FantasyPlayer;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public class PlayerRegistryListener implements Listener {

	private static final FantasyClasses plugin = FantasyClasses.getPlugin();

	public PlayerRegistryListener() {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		plugin.getPlayerManager().addPlayer(p);
		
		ChatUtils.welcomeMessage(p);
	}
	
	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent e) {
		plugin.getPlayerManager().removePlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerKickEvent(PlayerKickEvent e) {
		plugin.getPlayerManager().removePlayer(e.getPlayer());
	}
	
	@EventHandler (priority=EventPriority.LOWEST)
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		if(!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;
		
		Player p = e.getPlayer();
		FantasyPlayer fplayer = plugin.getPlayerManager().getPlayer(p);
		if(fplayer == null)
			return;
		
		int exp = Collectible.getDropExpAmount(e.getItem());
		if(exp == 0)
			return;
		
		fplayer.addSkillExp(exp);
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.2f);
		new BukkitRunnable() {

			@Override
			public void run() {
				p.getInventory().setItem(e.getHand(), null);				
			}
			
		}.runTaskLater(plugin, 1);
	}
}
