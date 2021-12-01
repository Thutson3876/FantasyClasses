package me.thutson3876.fantasyclasses.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.world.LootGenerateEvent;

import me.thutson3876.fantasyclasses.FantasyClasses;

public class AbilityListener implements Listener {

	private static final FantasyClasses plugin = FantasyClasses.getPlugin();
	
	public AbilityListener() {
		plugin.registerEvents(this);
	}
	
	@EventHandler
	public void onEntityDamagedByEntityEvent(EntityDamageByEntityEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onCraftItemEvent(CraftItemEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		plugin.triggerAll(e);
	}

	@EventHandler
	public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onProjectileHitEvent(ProjectileHitEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onEntityPotionEffectEvent(EntityPotionEffectEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onPotionSplashEvent(PotionSplashEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onEntityTameEvent(EntityTameEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onEntityTargetEvent(EntityTargetEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onLootGenerateEvent(LootGenerateEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onEntityToggleGlideEvent(EntityToggleGlideEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onEntityRegainHealthEvent(EntityRegainHealthEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onPlayerItemDamageEvent(PlayerItemDamageEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onPlayerExpChangeEvent(PlayerExpChangeEvent e) {
		plugin.triggerAll(e);
	}
	
	@EventHandler
	public void onEntityExhaustionEvent(EntityExhaustionEvent e) {
		plugin.triggerAll(e);
	}

	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent e) {
		plugin.triggerAll(e);
	}
}
