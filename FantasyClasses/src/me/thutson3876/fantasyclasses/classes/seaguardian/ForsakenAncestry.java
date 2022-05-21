package me.thutson3876.fantasyclasses.classes.seaguardian;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class ForsakenAncestry extends AbstractAbility {

	private BukkitTask task = null;
	private BukkitRunnable runnable = null;

	private float prevWalkSpeed = 0.2f;

	private List<PotionEffect> effects = new ArrayList<>();

	public ForsakenAncestry(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Forsaken Ancestry";
		this.skillPointCost = 3;
		this.maximumLevel = 1;

		this.createItemStack(Material.AXOLOTL_BUCKET);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof InventoryCloseEvent))
			return false;

		InventoryCloseEvent e = (InventoryCloseEvent) event;

		if (!e.getPlayer().equals(player))
			return false;

		if (!player.isInWater())
			prevWalkSpeed = player.getWalkSpeed();

		return false;
	}

	@Override
	public String getInstructions() {
		return "Be in water";
	}

	@Override
	public String getDescription() {
		return "While in water, you feel more attuned to your surroundings. Gain conduit power and faster swim speed while in water";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		effects.add(new PotionEffect(PotionEffectType.CONDUIT_POWER, 60, 2));

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

				if (player.isInWater() || (!player.getWorld().isClearWeather() && player.getWorld()
						.getHighestBlockAt(player.getLocation()).getY() < player.getLocation().getY())) {
					applyPotionEffects();
					for (Ability abil : plugin.getPlayerManager().getPlayer(player).getAbilities()) {
						if (abil instanceof Hydrodynamics)
							player.setWalkSpeed(prevWalkSpeed + ((Hydrodynamics) abil).getSwimSpeedBonus());
						if (abil instanceof SharedBlessings) {
							double range = ((SharedBlessings) abil).getRange();
							PotionEffect effect = new PotionEffect(PotionEffectType.CONDUIT_POWER, 40, 0);
							for (LivingEntity l : AbilityUtils.getNearbyLivingEntities(player, range, range, range)) {
								if (l instanceof Mob || l.isDead())
									continue;

								l.addPotionEffect(effect);
							}
						}
					}
				} else {
					player.setWalkSpeed(prevWalkSpeed);
					return;
				}
			}
		};

		task = runnable.runTaskTimer(plugin, 10L, 10L);
	}

	@Override
	public void deInit() {
		if ((Bukkit.getScheduler().isCurrentlyRunning(task.getTaskId())
				|| Bukkit.getScheduler().isQueued(task.getTaskId())))
			task.cancel();
	}

	private void applyPotionEffects() {
		player.addPotionEffects(effects);
	}

}
