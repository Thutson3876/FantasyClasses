package me.thutson3876.fantasyclasses.classes.dungeoneer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class DungeonDelver extends AbstractAbility {

	private int taskID = 0;

	private BukkitTask task = null;
	private BukkitRunnable runnable = null;

	private int maxLightLevel = 8;

	private final PotionEffect darkVision = new PotionEffect(PotionEffectType.NIGHT_VISION, 240, 0);
	private final PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 40, 0);
	private final PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 0);

	private List<PotionEffect> effects = new ArrayList<>();

	public DungeonDelver(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Dungeon Delver";
		this.skillPointCost = 2;
		this.maximumLevel = 3;

		this.createItemStack(Material.ENDER_EYE);
	}

	@Override
	public void deInit() {
		if (this.task == null || this.taskID == 0)
			return;
		Bukkit.getScheduler().cancelTask(this.taskID);
		this.taskID = 0;
	}

	@Override
	public boolean trigger(Event event) {
		return false;
	}

	@Override
	public String getInstructions() {
		return "Be underground in a light level below " + (maxLightLevel + 1);
	}

	@Override
	public String getDescription() {
		return "While in darkness underground, gain darkvision, speed, and strength per level respectively";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		if(currentLevel > 0)
			effects.add(darkVision);
		if(currentLevel > 1)
			effects.add(speed);
		if(currentLevel > 2)
			effects.add(strength);
		
		if(task == null) {
			
		}
		else if((Bukkit.getScheduler().isCurrentlyRunning(task.getTaskId()) || Bukkit.getScheduler().isQueued(task.getTaskId())))
			task.cancel();
		
		this.runnable = new BukkitRunnable() {

			public void run() {
				if (player == null) {
					deInit();
					return;
				}
				if (player.isDead())
					return;
				Location loc = player.getLocation();
				if (loc.getBlock().getLightLevel() > maxLightLevel
						|| loc.getWorld().getHighestBlockAt(loc).getY() < loc.getY()) {
					player.removePotionEffect(PotionEffectType.NIGHT_VISION);
					return;
				}
				applyPotionEffects();
			}
			
		};
		
		task = runnable.runTaskTimer(plugin, coolDowninTicks, coolDowninTicks);
	}

	private void applyPotionEffects() {
		player.addPotionEffects(effects);
	}

}
