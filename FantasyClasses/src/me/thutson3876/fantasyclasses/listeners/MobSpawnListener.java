package me.thutson3876.fantasyclasses.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.custommobs.DrownedMiner;
import me.thutson3876.fantasyclasses.custommobs.FailedExperiment;
import me.thutson3876.fantasyclasses.custommobs.LostGuardian;
import me.thutson3876.fantasyclasses.custommobs.Parasite;
import me.thutson3876.fantasyclasses.custommobs.UndeadMiner;
import me.thutson3876.fantasyclasses.custommobs.boss.TargetDummy;
import me.thutson3876.fantasyclasses.custommobs.boss.engineer.Engineer;
import me.thutson3876.fantasyclasses.custommobs.boss.uthroes.Uthroes;
import me.thutson3876.fantasyclasses.custommobs.boss.voidremnant.VoidRemnant;
import me.thutson3876.fantasyclasses.util.Schematic;

public class MobSpawnListener implements Listener {
	private static final FantasyClasses plugin = FantasyClasses.getPlugin();

	public MobSpawnListener() {
		plugin.registerEvents(this);
	}
	
	@EventHandler
	public void onMobSpawnEvent(EntitySpawnEvent e) {
		Random rng = new Random();
		Location loc = e.getLocation();
		if(e.getEntityType().equals(EntityType.ZOMBIE)) {
			if(rng.nextDouble() < 0.05) {
				if(loc.getY() < -30) {
					e.setCancelled(true);
					loc.getWorld().playSound(loc, Sound.ENTITY_WITHER_AMBIENT, 1.5f, 1.0f);
					new UndeadMiner(loc);
				}
				else if(loc.getY() < 0) {
					e.setCancelled(true);
					loc.getWorld().playSound(loc, Sound.ENTITY_CREEPER_PRIMED, 2.0f, 0.5f);
					new FailedExperiment(loc);
				}
			}
		}
		
		else if(e.getEntityType().equals(EntityType.DROWNED)) {
			double chance = rng.nextDouble();
			if(chance < 0.01) {
				e.setCancelled(true);
				loc.getWorld().playSound(loc, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 2.0f, 1.6f);
				new LostGuardian(loc);
			}
			else if(chance < 0.05) {
				e.setCancelled(true);
				loc.getWorld().playSound(loc, Sound.ENTITY_PARROT_IMITATE_DROWNED, 2.0f, 0.5f);
				new DrownedMiner(loc);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent e) {
		int level = plugin.getPlayerManager().getPlayer(e.getPlayer()).getPlayerLevel();
		Random rng = new Random();
		Location loc = e.getBlock().getLocation();
		if(e.getBlock().getType().equals(Material.MYCELIUM)) {
			if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.SLIME_BALL)){
				loc.getWorld().playSound(loc, Sound.ENTITY_SLIME_SQUISH, 1.5f, 2.0f);
				new TargetDummy(loc);
			}
			else if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WITHER_SKELETON_SKULL)){
				loc.getWorld().playSound(loc, Sound.ENTITY_WITHER_SPAWN, 1.5f, 2.0f);
				new VoidRemnant(loc);
			}
			else if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TNT)){
				loc.getWorld().playSound(loc, Sound.ENTITY_PARROT_IMITATE_ZOMBIE, 1.5f, 1.0f);
				new Engineer(loc);
			}
			else if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ICE)){
				loc.getWorld().playSound(loc, Sound.ENTITY_HOGLIN_CONVERTED_TO_ZOMBIFIED, 2.5f, 0.8f);
				new Uthroes(loc);
			}
		}
		
		if(loc.getY() > -15)
			return;
		
		if(rng.nextDouble() < (level - 30) * 0.000025) {
			loc.getWorld().playSound(loc, Sound.ENTITY_DONKEY_HURT, 1.0f, 2.0f);
			new Parasite(loc);
		}
		
		
	}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent e) {
		Block placed = e.getBlockPlaced();
		Integer[][] schematicCoords = new Integer[][] {
			{1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1},
			{0, -1, 0}, {1, -1, 0}, {-1, -1, 0}, {0, -1, 1}, {0, -1, -1},
			{0, -2, 0}
		};
		
		Map<Integer[], Material> schematic = new HashMap<>();
		for(int i = 0; i < schematicCoords.length; i++) {
			if(i < 4) {
				schematic.put(schematicCoords[i], Material.WITHER_SKELETON_SKULL);
				continue;
			}
			schematic.put(schematicCoords[i], Material.POLISHED_DEEPSLATE);
		}
		
		if(!Schematic.detectAndRemove(placed, schematic))
			return;
		
		Location loc = e.getBlockAgainst().getLocation();
		loc.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_GROWL, 2.0f, 0.7f);
		loc.getWorld().spawnParticle(Particle.PORTAL, loc, 20);
		new BukkitRunnable() {

			@Override
			public void run() {
				loc.getWorld().createExplosion(loc, 6.0f);
				new VoidRemnant(loc);
			}
			
		}.runTaskLater(plugin, 3 * 20);
		
	}
}
