package me.thutson3876.fantasyclasses.listeners;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.playermanagement.FantasyPlayer;

public class SkillPointExpListener implements Listener {

	private static Map<EntityType, Integer> entityTypeExpDrop = new HashMap<>();
	private static Map<Material, Integer> blockTypeExpDrop = new HashMap<>();
	private static Map<Material, Integer> smeltTypeExpDrop = new HashMap<>();
	private static final Random rng = new Random();
	
	//Entity Killed
	private static final FantasyClasses plugin = FantasyClasses.getPlugin();
	
	private Map<LivingEntity, List<Player>> trackedEntities = new HashMap<>();
	
	public SkillPointExpListener() {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	static {
		//Entity Types
		entityTypeExpDrop.put(EntityType.ZOMBIE, 1);
		entityTypeExpDrop.put(EntityType.HUSK, 1);
		entityTypeExpDrop.put(EntityType.DROWNED, 2);
		entityTypeExpDrop.put(EntityType.SKELETON, 1);
		entityTypeExpDrop.put(EntityType.STRAY, 2);
		entityTypeExpDrop.put(EntityType.SPIDER, 1);
		entityTypeExpDrop.put(EntityType.CAVE_SPIDER, 2);
		entityTypeExpDrop.put(EntityType.CREEPER, 1);
		entityTypeExpDrop.put(EntityType.ZOMBIE_VILLAGER, 1);
		entityTypeExpDrop.put(EntityType.PHANTOM, 2);
		entityTypeExpDrop.put(EntityType.PIGLIN, 3);
		entityTypeExpDrop.put(EntityType.ZOMBIFIED_PIGLIN, 3);
		entityTypeExpDrop.put(EntityType.HOGLIN, 5);
		entityTypeExpDrop.put(EntityType.ZOGLIN, 5);
		entityTypeExpDrop.put(EntityType.PILLAGER, 1);
		entityTypeExpDrop.put(EntityType.GUARDIAN, 2);
		entityTypeExpDrop.put(EntityType.GHAST, 5);
		entityTypeExpDrop.put(EntityType.BLAZE, 5);
		entityTypeExpDrop.put(EntityType.WITHER_SKELETON, 5);
		entityTypeExpDrop.put(EntityType.WITCH, 4);
		entityTypeExpDrop.put(EntityType.RAVAGER, 7);
		entityTypeExpDrop.put(EntityType.ENDERMAN, 4);
		entityTypeExpDrop.put(EntityType.POLAR_BEAR, 4);
		
		entityTypeExpDrop.put(EntityType.EVOKER, 10);
		entityTypeExpDrop.put(EntityType.ELDER_GUARDIAN, 20);
		
		entityTypeExpDrop.put(EntityType.WITHER, 75);
		
		//Block Types
		blockTypeExpDrop.put(Material.ANCIENT_DEBRIS, 20);
		blockTypeExpDrop.put(Material.EMERALD_ORE, 16);
		blockTypeExpDrop.put(Material.DIAMOND_ORE, 12);
		blockTypeExpDrop.put(Material.LAPIS_ORE, 5);
		blockTypeExpDrop.put(Material.REDSTONE_ORE, 5);
		blockTypeExpDrop.put(Material.COAL_ORE, 1);
		//Smelt Types
		smeltTypeExpDrop.put(Material.COPPER_INGOT, 3);
		smeltTypeExpDrop.put(Material.IRON_INGOT, 3);
		smeltTypeExpDrop.put(Material.GOLD_INGOT, 7);
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Mob) {
			if(!(e.getEntity() instanceof LivingEntity))
				return;
			
			LivingEntity victim = (LivingEntity) e.getEntity();
			Entity damager = e.getDamager();
			if(damager instanceof Tameable) {
				damager = (Entity) ((Tameable)damager).getOwner();
				if(damager == null)
					return;
			}
			
			if(!(damager instanceof Player))
				return;
			
			Player player = (Player) damager;
			
			if(e.getFinalDamage() < victim.getHealth()) {
				addEntity(victim, player);
			}
			else {
				int expDrop = entityTypeExpDrop.get(victim.getType());
				for(Player p : trackedEntities.remove(victim)) {
					plugin.getPlayerManager().getPlayer(p).addSkillExp(expDrop);
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent e) {
		Integer expDrop = entityTypeExpDrop.get(e.getEntityType());
		if(expDrop == null)
			return;
		
		List<Player> players = trackedEntities.remove(e.getEntity());
		if(players == null || players.isEmpty())
			return;
		
		for(Player p : players) {
			plugin.getPlayerManager().getPlayer(p).addSkillExp(expDrop);
		}
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent e) {
		if(rng.nextDouble() > 0.995)
			plugin.getPlayerManager().getPlayer(e.getPlayer()).addSkillExp(1);
		
		if(e.getBlock() instanceof Ageable) {
			Ageable agedBlock = (Ageable)e.getBlock();
			if(agedBlock.getAge() >= agedBlock.getMaximumAge())
				plugin.getPlayerManager().getPlayer(e.getPlayer()).addSkillExp(1);
		}
		
		if(e.getExpToDrop() == 0)
			return;
		
		Integer expToDrop = blockTypeExpDrop.get(e.getBlock().getType());
		if(expToDrop == null)
			return;
		
		plugin.getPlayerManager().getPlayer(e.getPlayer()).addSkillExp(expToDrop);
	}
	
	@EventHandler
	public void onPlayerFishEvent(PlayerFishEvent e) {
		if(e.getState().equals(State.CAUGHT_FISH))
			plugin.getPlayerManager().getPlayer(e.getPlayer()).addSkillExp(1);
	}
	
	@EventHandler
	public void onEntityBreedEvent(EntityBreedEvent e) {
		if(e.getBreeder() instanceof Player) {
			plugin.getPlayerManager().getPlayer((Player)e.getBreeder()).addSkillExp(1);
		}
	}
	
	@EventHandler
	public void onFurnaceExtractEvent(FurnaceExtractEvent e) {
		Integer exp = blockTypeExpDrop.get(e.getItemType());
		if(exp != null) {
			int expToDrop = (e.getItemAmount() / 8) * exp;
			plugin.getPlayerManager().getPlayer(e.getPlayer()).addSkillExp(expToDrop);
		}
	}
	
	@EventHandler
	public void onPlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent e) {
		if(checkAdvancementValidity(e.getAdvancement())) {
			FantasyPlayer fplayer = plugin.getPlayerManager().getPlayer(e.getPlayer());
			fplayer.addSkillExp((int) Math.round(10 * (1.0 + fplayer.getPlayerLevel() * 0.01)));
		}
	}
	
	private void addEntity(LivingEntity ent, Player...players) {
		if(trackedEntities.containsKey(ent)) {
			trackedEntities.get(ent).addAll(Arrays.asList(players));
		}
		else {
			trackedEntities.put(ent, Arrays.asList(players));
		}
	}
	
	private static boolean checkAdvancementValidity(Advancement adv) {
		Collection<String> criteriaList = adv.getCriteria();
		for(String criteria : criteriaList) {
			if(criteria.equalsIgnoreCase("has_the_recipe")) {
				return false;
			}
		}
		return true;
	}
	
	public static int getExpReward(Material type) {
		Integer val = blockTypeExpDrop.get(type);
		if(val != null)
			return val;
		
		return 0;
	}
	
	public static int getExpReward(EntityType type) {
		Integer val = entityTypeExpDrop.get(type);
		if(val != null)
			return val;
		
		return 0;
	}
}
