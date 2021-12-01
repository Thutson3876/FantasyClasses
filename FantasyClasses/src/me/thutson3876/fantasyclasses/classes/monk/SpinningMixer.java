package me.thutson3876.fantasyclasses.classes.monk;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class SpinningMixer extends AbstractAbility {

	private boolean weightless = false;

	private double range = 3.5D;

	private int counter = 0;

	private int duration = 12;

	private List<Entity> entities = new ArrayList<>();

	private float vertical_ticker = 0.0F;

	private float horizontal_ticker = (float) (Math.random() * 2.0D * Math.PI);

	public SpinningMixer(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30 * 20;
		this.displayName = "Spinning Mixer";
		this.skillPointCost = 2;
		this.maximumLevel = 2;

		this.createItemStack(Material.FEATHER);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof InventoryOpenEvent))
			return false;

		InventoryOpenEvent e = (InventoryOpenEvent) event;

		if (!e.getPlayer().equals(player))
			return false;

		if (!e.getInventory().equals(player.getInventory()))
			return false;

		if (!player.isSneaking())
			return false;

		if (spawnTornado()) {
			e.setCancelled(true);
			return true;
		}

		return false;
	}

	@Override
	public String getInstructions() {
		return "Open your inventory while crouching to activate";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		if (this.currentLevel > 1)
			weightless = true;
	}

	private boolean spawnTornado() {
		List<Entity> enemies = player.getNearbyEntities(this.range, this.range, this.range);
		if (enemies == null || enemies.isEmpty())
			return false;
		for (Entity e : enemies)
			e.setGravity(false);
		this.counter = 0;
		this.entities = enemies;
		BukkitRunnable task = new BukkitRunnable() {
			public void run() {
				if (counter > duration) {
					for (Entity e : entities) {
						e.setGravity(true);
						e.setVelocity(e.getVelocity().add(Vector.getRandom().multiply(0.4D)));

					}
					player.setGravity(!weightless);
					cancel();
					return;
				}
				tick();
				counter++;
			}
		};
		task.runTaskTimer(plugin, 0L, 2L);
		
		player.setGravity(!weightless);
		
		return true;
	}

	private void tick() {
		double radius = Math.sin(verticalTicker()) * 2.0D;
		float horisontal = horizontalTicker();
		Vector v = new Vector(radius * Math.cos(horisontal), 0.1D, radius * Math.sin(horisontal));
		List<Entity> new_entities = player.getNearbyEntities(this.range, this.range, this.range);
		for (Entity e : new_entities) {
			if (!this.entities.contains(e))
				this.entities.add(e);
		}
		for (Entity e : this.entities) {
			e.setVelocity(v);

			if (counter % 3 == 0) {
				e.getWorld().playSound(e.getLocation(), Sound.ENTITY_PHANTOM_FLAP, 1.0f, 0.8f);
				e.getWorld().spawnParticle(Particle.WHITE_ASH, e.getLocation(), 1);
			}
		}
	}

	private float verticalTicker() {
		if (this.vertical_ticker < 1.0F)
			this.vertical_ticker += 0.05F;
		return this.vertical_ticker;
	}

	private float horizontalTicker() {
		return this.horizontal_ticker += 0.5F;
	}
}
