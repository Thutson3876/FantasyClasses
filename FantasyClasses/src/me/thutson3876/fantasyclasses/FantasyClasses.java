package me.thutson3876.fantasyclasses;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.classes.combat.Combat;
import me.thutson3876.fantasyclasses.commands.CommandManager;
import me.thutson3876.fantasyclasses.cooldowns.CooldownManager;
import me.thutson3876.fantasyclasses.listeners.AbilityListener;
import me.thutson3876.fantasyclasses.listeners.BrewingListener;
import me.thutson3876.fantasyclasses.listeners.PlayerListener;
import me.thutson3876.fantasyclasses.listeners.PlayerRegistryListener;
import me.thutson3876.fantasyclasses.listeners.WitchesBrewListener;
import me.thutson3876.fantasyclasses.playermanagement.BrewTracker;
import me.thutson3876.fantasyclasses.playermanagement.FantasyPlayer;
import me.thutson3876.fantasyclasses.playermanagement.PlayerManager;

public class FantasyClasses extends JavaPlugin {

	protected static FantasyClasses plugin;
	
	private CommandManager commandManager;
	
	private CooldownManager cooldownManager;
	
	private PlayerManager playerManager;
	
	private BrewTracker brewTracker;
	
	@Override
	public void onEnable() {
		registerSerializableClasses();
		plugin = this;
		playerManager = new PlayerManager();
		playerManager.init();
		cooldownManager = new CooldownManager();
		commandManager = new CommandManager();
		brewTracker = new BrewTracker();
		
		registerListeners();
		log("FantasyClasses has been loaded!");
	}
	
	@Override
	public void onDisable() {
		playerManager.deInit();
		this.saveConfig();
		log("FantasyClasses has been disabled!");
	}
	
	private void registerListeners() {
		new PlayerRegistryListener();
		new PlayerListener();
		new AbilityListener();
		new BrewingListener();
		new WitchesBrewListener();
	}
	
	public void triggerAllAbilities(Event e) {
		for(FantasyPlayer p : this.playerManager.getFantasyPlayers()) {
			for(Ability abil : p.getAbilities()) {
				if(!abil.isEnabled()) {
					continue;
				}
				if(abil.trigger(e)) {
					abil.triggerCooldown();
				}
			}
		}
	}
	
	public void registerEvents(Listener listener) {
		if (listener == null)
			return; 
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
	
	public void triggerAll(Event e) {
		for(FantasyPlayer p : playerManager.getFantasyPlayers()) {
			for(Ability a : p.getAbilities()) {
				if(a.trigger(e)) {
					a.triggerCooldown();
				}
			}
		}
	}
	
	public static FantasyClasses getPlugin() {
		return plugin;
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public CooldownManager getCooldownManager() {
		return cooldownManager;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	
	public BrewTracker getBrewTracker() {
		return brewTracker;
	}
	
	public void log(String message) {
		System.out.println(message);
	}
	
	public void log(String message, Throwable error) {
		System.out.println(message);
		error.printStackTrace();
	}
	
	private static void registerSerializableClasses() {
		Combat.getAbilityClassList().forEach(ConfigurationSerialization::registerClass);
	}
}
