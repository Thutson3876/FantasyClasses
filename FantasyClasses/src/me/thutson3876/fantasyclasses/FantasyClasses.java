package me.thutson3876.fantasyclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.collectible.Collectible;
import me.thutson3876.fantasyclasses.collectible.CollectibleListener;
import me.thutson3876.fantasyclasses.commands.CommandManager;
import me.thutson3876.fantasyclasses.cooldowns.CooldownManager;
import me.thutson3876.fantasyclasses.custommobs.CustomMobManager;
import me.thutson3876.fantasyclasses.listeners.AbilityListener;
import me.thutson3876.fantasyclasses.listeners.CustomDamageListener;
import me.thutson3876.fantasyclasses.listeners.MobSpawnListener;
import me.thutson3876.fantasyclasses.listeners.PlayerRegistryListener;
import me.thutson3876.fantasyclasses.listeners.ScalingListener;
import me.thutson3876.fantasyclasses.listeners.SkillPointExpListener;
import me.thutson3876.fantasyclasses.listeners.WitchesBrewListener;
import me.thutson3876.fantasyclasses.playermanagement.FantasyPlayer;
import me.thutson3876.fantasyclasses.playermanagement.PlayerManager;

public class FantasyClasses extends JavaPlugin {

	protected static FantasyClasses plugin;

	private CommandManager commandManager;

	private CooldownManager cooldownManager;

	private PlayerManager playerManager;

	private CustomMobManager mobManager;

	private Map<Collectible, List<Location>> collectiblesToLoad = new HashMap<>();
	
	private Map<Collectible, List<Location>> collectiblesToSave = new HashMap<>();

	@Override
	public void onEnable() {
		// registerSerializableClasses();
		for(Collectible c : Collectible.values()) {
			collectiblesToLoad.put(c, new ArrayList<>());
			collectiblesToSave.put(c, new ArrayList<>());
		}
		
		plugin = this;
		playerManager = new PlayerManager();
		playerManager.init();
		cooldownManager = new CooldownManager();
		commandManager = new CommandManager();
		mobManager = new CustomMobManager();
		mobManager.init();

		registerListeners();
		loadCollectibles();
		log("FantasyClasses has been loaded!");
	}

	@Override
	public void onDisable() {
		playerManager.deInit();
		mobManager.deInit();
		cooldownManager.deInit();
		for(Collectible c : Collectible.values()) {
			c.saveCollectibles(collectiblesToSave);
		}
		
		this.saveConfig();
		log("FantasyClasses has been disabled!");
	}

	private void registerListeners() {
		new CollectibleListener();
		new PlayerRegistryListener();
		new AbilityListener();
		new SkillPointExpListener();
		new WitchesBrewListener();
		new ScalingListener();
		new CustomDamageListener();
		new MobSpawnListener();
	}

	public void triggerAllAbilities(Event e) {
		for (FantasyPlayer p : this.playerManager.getFantasyPlayers()) {
			for (Ability abil : p.getAbilities()) {
				if (!abil.isEnabled()) {
					continue;
				}
				if (abil.trigger(e)) {
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
		for (FantasyPlayer p : playerManager.getFantasyPlayers()) {
			for (Ability a : p.getAbilities()) {
				if (a.trigger(e)) {
					a.triggerCooldown();
				}
			}
		}
	}

	public void addCollectible(int x, int y, int z, Collectible col) {
		List<Location> newLoadList = collectiblesToLoad.get(col);
		String worldString = "world";
		World world = Bukkit.getServer().getWorld("world");
		if(world == null)
			world = Bukkit.getServer().getWorld(UUID.fromString(worldString));
		
		Location loc = new Location(world, x, y, z);
		
		if(newLoadList == null) {
			newLoadList = new ArrayList<>();
		}
		newLoadList.add(loc);
		collectiblesToLoad.put(col, newLoadList);
		
		List<Location> newSaveList = collectiblesToSave.get(col);
		if(newSaveList == null) {
			newSaveList = new ArrayList<>();
		}
		newSaveList.add(loc);
		collectiblesToSave.put(col, newSaveList);
	}
	
	public void removeCollectible(Location loc, Collectible col) {
		List<Location> newLoadList = collectiblesToLoad.get(col);
		if(newLoadList == null)
			newLoadList = new ArrayList<>();
		newLoadList.remove(loc);
		collectiblesToLoad.put(col, newLoadList);
		
		List<Location> newSaveList = collectiblesToSave.get(col);
		if(newSaveList == null)
			newSaveList = new ArrayList<>();
		newSaveList.remove(loc);
		collectiblesToSave.put(col, newSaveList);
	}

	public void setCollectiblesMetadata() {
		for (Entry<Collectible, List<Location>> entry : collectiblesToLoad.entrySet()) {
			for(Location loc : entry.getValue()) {
				loc.getBlock().setMetadata("Collectible", new FixedMetadataValue(this, entry.getKey()));
				System.out.println("!!!" + entry.getKey().name() + "!!!");
				System.out.println("Collectible spawned at " + loc.toString());
			}
		}

		collectiblesToLoad.clear();
	}
	
	public void setCollectiblesMetadata(Chunk chunk) {
		Map<Collectible, List<Location>> tempMap = new HashMap<>();
		List<Location> tempList = new ArrayList<>();
		for (Entry<Collectible, List<Location>> entry : collectiblesToLoad.entrySet()) {
			for(Location loc : entry.getValue()) {
				if(loc.getWorld() == null)
					loc.setWorld(Bukkit.getServer().getWorld("world"));
				if(loc.getChunk().equals(chunk)) {
					tempList.add(loc);
					loc.getBlock().setMetadata("Collectible", new FixedMetadataValue(this, entry.getKey()));
					System.out.println("!!!" + entry.getKey().name() + "!!!");
					System.out.println("Collectible spawned at " + loc.toString());
				}
			}
			tempMap.put(entry.getKey(), tempList);
			tempList.clear();
		}
		
		for(Collectible c : collectiblesToLoad.keySet()) {
			tempList = collectiblesToLoad.get(c);
			tempList.removeAll(tempMap.get(c));
			collectiblesToLoad.put(c, tempList);
		}
		
	}
	
	public boolean checkIfCollectibleLocation(Location loc, Collectible col) {
		int count = 0;
		for(Location l : collectiblesToSave.get(col)) {
			if(l.equals(loc))
				return true;
			
			count++;
		}
		
		System.out.println(count + " iterations");
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private void loadCollectibles() {
		FileConfiguration config = plugin.getConfig();
		if(!config.contains("collectibles")) {
			config.createSection("collectibles");
			for(Collectible col : Collectible.values()) {
				config.createSection("collectibles." + col.name().toLowerCase());
			}
			
			return;
		}	
		
		for(Collectible c : Collectible.values()){
			Map<Collectible, List<Location>> newMap = c.deSerialize((List<Map<String, Object>>) config.getList("collectibles." + c.name().toLowerCase()));
			if(newMap == null) {
				System.out.println(c.name() + " was null");
				continue;
			}
			
			List<Location> newLoadList = collectiblesToLoad.get(c);
			newLoadList.addAll(newMap.get(c));
			collectiblesToLoad.put(c, newLoadList);
			
			List<Location> newSaveList = collectiblesToSave.get(c);
			newSaveList.addAll(newMap.get(c));
			collectiblesToSave.put(c, newSaveList);
		}
	}
	
	public void removeLocation(Collectible col, Location loc) {
		List<Location> newList = collectiblesToSave.get(col);
		newList.remove(loc);
		collectiblesToSave.put(col, newList);
	}
	
	public Location deSerializeLocation(Map<String, Object> map) {
		double x = (Double) map.get("x");
		double y = (Double) map.get("y");
		double z = (Double) map.get("z");
		
		return new Location(this.getServer().getWorld("world"), x, y, z);
	}
	
	public static Map<String, Object> serializeLocation(Location loc) {
		Map<String, Object> map = new HashMap<>();
		map.put("x", loc.getX());
		map.put("y", loc.getY());
		map.put("z", loc.getZ());
		
		return map;
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
	
	public CustomMobManager getMobManager() {
		return mobManager;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public void log(String message) {
		System.out.println(message);
	}

	public void log(String message, Throwable error) {
		System.out.println(message);
		error.printStackTrace();
	}

	/*
	 * private static void registerSerializableClasses() {
	 * Combat.getAbilityClassList().forEach(ConfigurationSerialization::
	 * registerClass); }
	 */
}
