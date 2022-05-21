package me.thutson3876.fantasyclasses.classes.highroller;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class CharismaCheck extends AbstractAbility {

	private final Random rng = new Random();
	private int dc = 19;
	private boolean isOn = false;
	private final int duration = 30 * 20;
	private final double radius = 30.0;
	private final PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, duration, 1);
	
	public CharismaCheck(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 20 * 20;
		this.displayName = "Charisma Check";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.BOOK);
	}

	@Override
	public boolean trigger(Event event) {
		if(event instanceof PlayerToggleSneakEvent) {
			PlayerToggleSneakEvent e = (PlayerToggleSneakEvent)event;
			
			if(!e.getPlayer().equals(player))
				return false;
			
			if(isOnCooldown())
				return false;
			
			if(AbilityUtils.getHeightAboveGround(player) < 0.3)
				return false;
			
			World world = player.getWorld();
			int roll = rng.nextInt(20);
			if (roll >= dc) {
				world.playSound(player.getLocation(), Sound.BLOCK_BELL_RESONATE, 1.0f, 1.0f);
				world.spawnParticle(Particle.HEART, player.getLocation(), 20);
				
				BukkitRunnable task = new BukkitRunnable() {
	
					@Override
					public void run() {
						isOn = false;
					}
					
				};
				
				isOn = true;
				task.runTaskLater(plugin, duration);
			}
			else if(roll == 0) {
				for(Entity ent : player.getNearbyEntities(radius, radius, radius)) {
					if(ent instanceof Creature) {
						((Creature) ent).setTarget(player);
						((Creature) ent).addPotionEffect(speed);
					}
				}
				
				world.playSound(player.getLocation(), Sound.ENTITY_ZOMBIFIED_PIGLIN_ANGRY, 1.0f, 1.0f);
				world.spawnParticle(Particle.VILLAGER_ANGRY, player.getLocation(), 8);
			}
			
			return true;
		}
		else if(event instanceof EntityTargetEvent) {
			EntityTargetEvent e = (EntityTargetEvent)event;
			if(e.getTarget() != null) {
				if(!e.getTarget().equals(player))
					return false;
			}
			if(isOn) {
				e.setCancelled(true);
				World world = player.getWorld();
				world.playSound(e.getEntity().getLocation(), Sound.ENTITY_CAT_PURREOW, 1.3f, 1.0f);
				world.spawnParticle(Particle.HEART, e.getEntity().getLocation(), 20);
			}
		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "Crouch while mid-air";
	}

	@Override
	public String getDescription() {
		return "Roll a d20 whenever you crouch mid-air. On a roll above a &6" + this.dc
				+ "&r nearby mobs won't attack you. On a natural one, all nearby mobs attack you post-haste";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		dc = 20 - currentLevel;
	}

}
