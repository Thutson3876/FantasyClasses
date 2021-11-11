package me.thutson3876.fantasyclasses.classes.highroller;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class AnimalHandling extends AbstractAbility {

	private final Random rng = new Random();
	private int dc = 19;

	public AnimalHandling(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 12 * 20;
		this.displayName = "Animal Handling";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.WHEAT);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerInteractEntityEvent))
			return false;
		
		PlayerInteractEntityEvent e = (PlayerInteractEntityEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		int roll = rng.nextInt(20);
		World world = player.getWorld();
		if (roll >= dc) {
			e.getRightClicked().addPassenger(player);
			world.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 1.0f, 1.0f);
		}
		else if(roll == 0) {
			player.addPassenger(e.getRightClicked());
			world.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
		}
		
		world.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 0.8f, 1.0f);
		return true;
	}

	@Override
	public String getInstructions() {
		return "Right-click an entity";
	}

	@Override
	public String getDescription() {
		return "Roll a d20 when interacting with an entity. On a roll above a &6" + this.dc + "&r you will ride it. On a natural one, it rides you";
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
