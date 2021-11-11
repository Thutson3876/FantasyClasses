package me.thutson3876.fantasyclasses.classes.highroller;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class ConstitutionSavingThrow extends AbstractAbility {

	private final PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 1);
	private final Random rng = new Random();
	private int dc = 19;
	
	public ConstitutionSavingThrow(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Constitution Saving Throw";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.GHAST_TEAR);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageEvent))
			return false;
		
		EntityDamageEvent e = (EntityDamageEvent)event;
		
		if(!e.getEntity().equals(player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		int roll = rng.nextInt(20);
		World world = player.getWorld();
		if (roll >= dc) {
			e.setDamage(0.0);
			e.setCancelled(true);
			player.addPotionEffect(regen);
			
			world.playSound(player.getLocation(), Sound.BLOCK_CAKE_ADD_CANDLE, 1.0f, 1.0f);
		}
		else if(roll == 0) {
			e.setDamage(e.getDamage() * 3.0);
			
			world.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
		}
		
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Take damage";
	}

	@Override
	public String getDescription() {
		return "Roll a d20 when you take damage. On a roll above a &6" + this.dc + "&r take no damage and gain regeneration. On a natural one, take triple damage";
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
