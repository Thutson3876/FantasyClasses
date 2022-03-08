package me.thutson3876.fantasyclasses.classes.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class RaiseDead extends AbstractAbility implements Bindable {

	private Material type = null;
	private List<EntityType> mobTypes = new ArrayList<>();
	private List<Creature> undead = new ArrayList<>();
	private int maxAmt = 2;
	private int duration = 16 * 20;
	private PotionEffect speed = null;
	
	public RaiseDead(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 16 * 20;
		this.displayName = "Raise Dead";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.SKELETON_SKULL);
	}

	@Override
	public boolean trigger(Event event) {
		if(event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e = (PlayerInteractEvent) event;
			
			if(!e.getPlayer().equals(player))
				return false;
			
			if(e.getItem() == null || !e.getItem().getType().equals(this.type))
				return false;
			
			if(!e.getAction().equals(Action.RIGHT_CLICK_AIR))
				return false;
			
			if(isOnCooldown())
				return false;
			
			spawnDead();
			return true;
		}
		else if(event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
			
			if(e.getDamager().equals(player) && e.getEntity() instanceof LivingEntity) {
				for(Creature c : undead) {
					c.setTarget((LivingEntity) e.getEntity());
				}
			}
			else if(e.getEntity().equals(player) && e.getDamager() instanceof LivingEntity) {
				for(Creature c : undead) {
					c.setTarget((LivingEntity) e.getDamager());
				}
			}
		}
		else if(event instanceof EntityTargetEvent) {
			EntityTargetEvent e = (EntityTargetEvent) event;
			
			if(!undead.contains(e.getEntity()))
				return false;
			
			if(e.getTarget() == null || mobTypes.contains(e.getTarget().getType()) || e.getTarget().equals(player)) {
				List<LivingEntity> nearbyEntities = AbilityUtils.getNearbyLivingEntities(player, 15, 15, 15);
				if(nearbyEntities == null) {
					e.setTarget(null);
					return false;
				}
				nearbyEntities.removeAll(undead);
				if(nearbyEntities.isEmpty()) {
					e.setTarget(null);
					return false;
				}
					
				e.setTarget(AbilityUtils.getNearestLivingEntity(player.getLocation(), nearbyEntities));
				return false;
			}
		}
		else if(event instanceof PlayerMoveEvent) {
			PlayerMoveEvent e = (PlayerMoveEvent)event;
			
			if(!e.getPlayer().equals(player))
				return false;
			
			for(Creature c : undead) {
				if(e.getTo().distance(c.getLocation()) > 20.0) {
					c.teleport(e.getTo());
				}
			}
			
		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "Right-click with bound item type in hand";
	}

	@Override
	public String getDescription() {
		return "Summon &6" + maxAmt + " &rundead minions to aid you in battle";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		this.mobTypes.add(EntityType.ZOMBIE);
		this.mobTypes.add(EntityType.HUSK);
		maxAmt = 1 + currentLevel;
		if(this.currentLevel == 2) {
			mobTypes.add(EntityType.SKELETON);
			mobTypes.add(EntityType.STRAY);
		}
		if(this.currentLevel == 3) {
			mobTypes.add(EntityType.ZOMBIFIED_PIGLIN);
			mobTypes.add(EntityType.WITHER_SKELETON);
			speed = new PotionEffect(PotionEffectType.SPEED, 4 * 20, 1);
		}
	}

	@Override
	public Material getBoundType() {
		return type;
	}

	@Override
	public void setBoundType(Material type) {
		this.type = type;		
	}
	
	private void spawnDead() {
		World world = player.getWorld();
		Location spawn = player.getEyeLocation();
		List<LivingEntity> nearbyEntities = AbilityUtils.getNearbyLivingEntities(player, 15, 15, 15);
		Random rng = new Random();
		
		for(int i = 0; i < maxAmt; i++) {
			Creature ent = (Creature) world.spawnEntity(spawn, mobTypes.get(rng.nextInt(mobTypes.size())));
			undead.add(ent);
			((Creature) ent).setTarget(AbilityUtils.getNearestLivingEntity(player.getLocation(), nearbyEntities));
			if(speed != null)
				ent.addPotionEffect(speed);
			
		}
		
		world.spawnParticle(Particle.SOUL, player.getLocation(), maxAmt);
		new BukkitRunnable() {

			@Override
			public void run() {
				for(Creature c : undead) {
					if(!c.isDead()) {
						c.getWorld().playSound(c.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 0.8f, 0.9f);
						c.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, c.getLocation(), 4);
						c.remove();
					}
				}
				undead.clear();
			}
			
		}.runTaskLater(plugin, duration);
	}

}
