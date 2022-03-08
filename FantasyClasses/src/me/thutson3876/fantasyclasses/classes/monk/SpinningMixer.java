package me.thutson3876.fantasyclasses.classes.monk;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;

public class SpinningMixer extends AbstractAbility implements Bindable {

	private boolean weightless = false;

	private double range = 3.5D;

	private int counter = 0;

	private int duration = 12;

	private List<Entity> entities = new ArrayList<>();

	private float vertical_ticker = 0.0F;

	private float horizontal_ticker = (float) (Math.random() * 2.0D * Math.PI);
	
	private Material type = null;

	public SpinningMixer(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 16 * 20;
		this.displayName = "Spinning Mixer";
		this.skillPointCost = 2;
		this.maximumLevel = 2;

		this.createItemStack(Material.TARGET);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof PlayerSwapHandItemsEvent))
			return false;

		PlayerSwapHandItemsEvent e = (PlayerSwapHandItemsEvent) event;

		if (!e.getPlayer().equals(player))
			return false;
			
		boolean correctType = false;
		
		if(e.getMainHandItem() != null) {
			if(e.getMainHandItem().getType().equals(type))
				correctType = true;
		}
		if(e.getOffHandItem() != null) {
			if(e.getOffHandItem().getType().equals(type))
				correctType = true;
		}
			
		if(!correctType)
			return false;
		
		if (spawnTornado()) {
			e.setCancelled(true);
			return true;
		}

		return false;
	}

	@Override
	public String getInstructions() {
		return "Swap hands with the bound item type";
	}

	@Override
	public String getDescription() {
		return "Swiftly twirl the air around you to create a small tornado that sucks up any nearby entities. You have no gravity while using this ability at level 2";
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

	@Override
	public Material getBoundType() {
		return type;
	}

	@Override
	public void setBoundType(Material type) {
		this.type = type;
	}
}
