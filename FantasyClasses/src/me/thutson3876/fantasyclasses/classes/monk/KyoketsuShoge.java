package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class KyoketsuShoge extends AbstractAbility implements Bindable {

	private double velocityMod = 1.2;
	private double damage = 4.0;
	private Arrow arrow = null;
	private double yMod = 1.0;
	
	private Material type = null;

	public KyoketsuShoge(Player p) {
		super(p);
	}

	//fix weird launching
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 18 * 20;
		this.displayName = "Kyoketsu-Shoge";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.ARROW);
	}

	@Override
	public boolean trigger(Event event) {
		if (event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e = (PlayerInteractEvent) event;

			if (!e.getPlayer().equals(this.player))
				return false;
			
			if (isOnCooldown())
				return false;

			if (!(e.getAction() == Action.RIGHT_CLICK_AIR))
				return false;
			if (!(player.getInventory().getItemInOffHand().getType().equals(type) || player.getInventory().getItemInMainHand().getType().equals(type)))
				return false;

			fireArrow();

			return true;
		} else if (event instanceof ProjectileHitEvent) {
			ProjectileHitEvent e = (ProjectileHitEvent) event;

			if (!e.getEntity().equals(this.arrow))
				return false;

			if (e.getHitBlock() != null) {
				AbilityUtils.moveToward(player, e.getHitBlock().getLocation(),
						player.getLocation().distance(e.getHitBlock().getLocation()), yMod);
			} else if (e.getHitEntity() != null) {
				AbilityUtils.moveToward(e.getHitEntity(), player.getLocation(),
						player.getLocation().distance(e.getHitEntity().getLocation()), yMod);
			}
		}

		return false;
	}

	private void fireArrow() {
		Vector velocity = player.getEyeLocation().getDirection().multiply(this.velocityMod);
		Location spawnAt = player.getEyeLocation().toVector().add(player.getEyeLocation().getDirection())
				.toLocation(player.getWorld());
		arrow = player.getWorld().spawnArrow(spawnAt, velocity, 1F, 1F);
		arrow.setShooter(player);
		arrow.setDamage(this.damage);
		arrow.setPickupStatus(PickupStatus.DISALLOWED);

		BukkitRunnable task = new BukkitRunnable() {

			@Override
			public void run() {
				if (arrow != null) {
					arrow.remove();
					arrow = null;
				}

			}
		};

		task.runTaskLater(plugin, 6 * 20);
	}

	@Override
	public String getInstructions() {
		return "Right-Click with an open hand";
	}

	@Override
	public String getDescription() {
		return "Launches an arrow that will pull enemies towards you or act as a grappling hook with a cooldown of &6"
				+ coolDowninTicks / 20 + "&r seconds";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		coolDowninTicks = (23 - currentLevel * 5) * 20;
		damage = 4.0 * currentLevel;
	}

	@Override
	public Material getBoundType() {
		return type;
	}

	@Override
	public void setBoundType(Material type) {
		this.type = type;
	}

}
