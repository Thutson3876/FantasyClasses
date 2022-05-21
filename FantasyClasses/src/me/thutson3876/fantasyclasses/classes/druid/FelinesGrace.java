package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class FelinesGrace extends AbstractAbility {

	private int taskID = 0;

	private BukkitTask task = null;
	private BukkitRunnable runnable = null;

	private int speedLvl = 0;
	private Cat boundCat = null;

	public FelinesGrace(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 10 * 20;
		this.displayName = "Feline's Grace";
		this.skillPointCost = 1;
		this.maximumLevel = 2;

		this.createItemStack(Material.RABBIT_FOOT);
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
		if (event instanceof PlayerInteractEntityEvent) {
			PlayerInteractEntityEvent e = (PlayerInteractEntityEvent) event;

			Entity ent = e.getRightClicked();

			if (ent instanceof Cat) {
				Cat cat = (Cat) ent;
				if (player.equals((cat.getOwner()))) {
					if (cat.isSitting()) {
						applyPotionEffects();
						if ((Bukkit.getScheduler().isCurrentlyRunning(task.getTaskId())
								|| Bukkit.getScheduler().isQueued(task.getTaskId()))) {
							task = runnable.runTaskTimer(plugin, coolDowninTicks - 20, coolDowninTicks - 20);
						}
						boundCat = cat;
					}
				}
			}
		} else if (event instanceof EntityDeathEvent) {
			EntityDeathEvent e = (EntityDeathEvent) event;

			if (e.getEntity().equals(this.boundCat)) {
				deInit();
				this.boundCat = null;
			}

		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "Pet your sitting tamed cat";
	}

	@Override
	public String getDescription() {
		return "While you have a cat following you, gain speed &6" + (speedLvl + 1);
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		speedLvl = currentLevel - 1;

		if (task == null) {

		} else if ((Bukkit.getScheduler().isCurrentlyRunning(task.getTaskId())
				|| Bukkit.getScheduler().isQueued(task.getTaskId())))
			task.cancel();

		this.runnable = new BukkitRunnable() {

			public void run() {
				if (player == null) {
					deInit();
					return;
				}
				if (player.isDead())
					return;

				applyPotionEffects();
			}

		};

		task = runnable.runTaskTimer(plugin, coolDowninTicks - 20, coolDowninTicks - 20);
	}

	private void applyPotionEffects() {
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, this.coolDowninTicks, this.speedLvl));
	}

}
