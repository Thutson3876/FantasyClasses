package me.thutson3876.fantasyclasses.classes.highroller;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class TwentyMore extends AbstractAbility {

	private final Random rng = new Random();
	private int dc = 19;
	private double dmgMod = 2.0;

	public TwentyMore(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Twenty More";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.EMERALD_BLOCK);
	}

	@Override
	public boolean trigger(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {

			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

			if (!e.getDamager().equals(this.player))
				return false;
			
			if(!(e.getEntity() instanceof LivingEntity))
				return false;

			int roll = rng.nextInt(20);
			if (roll >= dc) {
				e.setDamage(e.getDamage() * dmgMod);
				
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.2F, 1.2F);
				player.getWorld().spawnParticle(Particle.CRIT_MAGIC, e.getEntity().getLocation(), roll);
				return true;
			} else if (roll == 0) {
				e.setCancelled(true);
				player.damage(e.getDamage() / 2.0);
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1.2F, 0.3F);
				player.getWorld().spawnParticle(Particle.NOTE, e.getEntity().getLocation(), 4);
				return true;
			}
		}

		return false;
	}

	@Override
	public String getInstructions() {
		return "Break a block";
	}

	@Override
	public String getDescription() {
		return "Roll a d20 whenever you attack an entity. On a roll above a &6" + this.dc
				+ "&r you deal twice as much damage. On a natural one, you take half of the dealt damage and it receives none";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		dc = 20 - currentLevel;
	}
}
