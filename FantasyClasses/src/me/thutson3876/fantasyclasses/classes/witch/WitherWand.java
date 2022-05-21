package me.thutson3876.fantasyclasses.classes.witch;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class WitherWand extends AbstractAbility implements Bindable {

	private Material type = null;
	private WitherSkull skull = null;
	private double bulletVelocity = 2.0;
	private double damage = 6.0;
	private float yield = 1.0f;
	
	public WitherWand(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 6 * 20;
		this.displayName = "Wither Wand";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.WITHER_SKELETON_SKULL);
	}

	@Override
	public boolean trigger(Event event) {
		if (event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e = (PlayerInteractEvent) event;

			if (!e.getPlayer().equals(player))
				return false;

			if (e.getItem() == null || !e.getItem().getType().equals(this.type))
				return false;

			if (!e.getAction().equals(Action.LEFT_CLICK_AIR))
				return false;
			
			if(isOnCooldown())
				return false;

			launchProjectile();
			return true;
		} 
		else if(event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
			
			if(skull == null)
				return false;
			
			if(!e.getCause().equals(DamageCause.ENTITY_EXPLOSION))
				return false;
			
			if(!e.getDamager().equals(player))
				return false;
			
			if(!(e.getEntity() instanceof LivingEntity))
				return false;
			
			LivingEntity le = ((LivingEntity)e.getEntity());
			if(le.isDead())
				return false;
			
			le.damage(calculateDamage() / 2, player);
		}
		else if (event instanceof ProjectileHitEvent) {
			ProjectileHitEvent e = (ProjectileHitEvent) event;

			if(!e.getEntity().equals(skull))
				return false;

			Entity hit = e.getHitEntity();
			if (hit == null)
				return false;

			if (!(hit instanceof LivingEntity))
				return false;

			LivingEntity livingHit = (LivingEntity) hit;
			if(hit.isDead())
				return false;
			
			livingHit.damage(calculateDamage(), player);
			new BukkitRunnable() {

				@Override
				public void run() {
					skull = null;
				}
				
			}.runTaskLater(plugin, 2);
		}
		
		
		return false;
	}

	private void launchProjectile() {
		Location spawnAt = player.getEyeLocation().toVector().add(player.getEyeLocation().getDirection())
				.toLocation(player.getWorld());

		skull = (WitherSkull) player.getWorld().spawnEntity(spawnAt, EntityType.WITHER_SKULL);
		skull.setShooter(player);
		skull.setDirection(player.getEyeLocation().getDirection().normalize().multiply(bulletVelocity));
		skull.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(bulletVelocity));
		skull.setCharged(true);
		skull.setYield(calculateYield());
		new BukkitRunnable() {

			@Override
			public void run() {
				if(skull != null && !skull.isDead()) {
					Vector newVelocity;
					if(player.getTargetBlockExact(30) != null)
						newVelocity = player.getTargetBlockExact(30).getLocation().toVector().subtract(skull.getLocation().toVector());
					else 
						newVelocity = player.getEyeLocation().getDirection();
					
					skull.setDirection(newVelocity.normalize().multiply(bulletVelocity));
					skull.setVelocity(newVelocity.normalize().multiply(bulletVelocity));
				}	
			}
			
		}.runTaskLater(plugin, 30);

		player.getWorld().playSound(spawnAt, Sound.ENTITY_WITHER_SHOOT, 0.9f, 1.0F);
		
	}

	private float calculateYield() {
		return (float) (yield * (1 + (getMagickaBonus() / 2)));
	}
	
	private double calculateDamage() {
		return damage * (1 + getMagickaBonus());
	}

	@Override
	public String getInstructions() {
		return "Left-click with your bound item";
	}

	@Override
	public String getDescription() {
		return "Launch a charged wither skull from your wand that causes its target to wither away. It deals &6"
				+ AbilityUtils.doubleRoundToXDecimals(calculateDamage(), 1) + " &rdamage and has a yield of &6" + AbilityUtils.doubleRoundToXDecimals(calculateYield(), 2) +" &rand a cooldown of &6" + this.coolDowninTicks / 20
				+ " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		damage = 3.0 + 3.0 * currentLevel;
		yield = 1.0f + (0.33f * currentLevel);
		this.coolDowninTicks = (7 - currentLevel) * 20;
	}

	@Override
	public Material getBoundType() {
		return type;
	}

	@Override
	public void setBoundType(Material type) {
		this.type = type;
	}

	private double getMagickaBonus() {
		if (this.fplayer == null)
			return 0;

		return (fplayer.getScalableValue("magicka") / 100);
	}
	
}
