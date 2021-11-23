package me.thutson3876.fantasyclasses.classes.highroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class HookLineAnd extends AbstractAbility {

	private final Random rng = new Random();
	private int dc = 19;
	
	private static List<EntityType> goodRolls = new ArrayList<>();
	private static List<EntityType> badRolls = new ArrayList<>();
	private static List<Material> goodItemRolls = new ArrayList<>();
	
	static {
		//Good rolls
		goodRolls.add(EntityType.BOAT);
		goodRolls.add(EntityType.DOLPHIN);
		goodRolls.add(EntityType.COD);
		goodRolls.add(EntityType.SALMON);
		goodRolls.add(EntityType.SKELETON_HORSE);
		goodRolls.add(EntityType.TURTLE);
		goodRolls.add(EntityType.AXOLOTL);
		goodRolls.add(EntityType.CAT);
		goodRolls.add(EntityType.DROPPED_ITEM);
		//Bad rolls
		badRolls.add(EntityType.PRIMED_TNT);
		badRolls.add(EntityType.CREEPER);
		badRolls.add(EntityType.DROWNED);
		badRolls.add(EntityType.GUARDIAN);
		badRolls.add(EntityType.PUFFERFISH);
		//Item Types
		goodItemRolls.add(Material.DIAMOND_BLOCK);
		goodItemRolls.add(Material.EMERALD_BLOCK);
		goodItemRolls.add(Material.GOLD_BLOCK);
		goodItemRolls.add(Material.IRON_BLOCK);
		goodItemRolls.add(Material.LAPIS_BLOCK);
		goodItemRolls.add(Material.REDSTONE_BLOCK);
		goodItemRolls.add(Material.COAL_BLOCK);
	}
	
	public HookLineAnd(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30 * 20;
		this.displayName = "Hook, Line, and ???";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		
		this.createItemStack(Material.COD);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerFishEvent))
			return false;
		
		PlayerFishEvent e = (PlayerFishEvent)event;
		
		if(!e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH))
			return false;
		
		e.getCaught().remove();
		
		Entity newEntity = null;
		int roll = rng.nextInt(20);
		if(roll > dc) {
			newEntity = player.getWorld().spawnEntity(e.getHook().getLocation(), goodRolls.get(rng.nextInt(goodRolls.size())));
			if(newEntity.getType().equals(EntityType.DROPPED_ITEM)) {
				Material type = goodItemRolls.get(rng.nextInt(goodItemRolls.size()));
				((Item)newEntity).setItemStack(new ItemStack(type));
			}
		}
		else if(roll == 0) {
			newEntity = player.getWorld().spawnEntity(e.getHook().getLocation(), badRolls.get(rng.nextInt(badRolls.size())));
		}
		
		if(newEntity != null) {
			e.getHook().addPassenger(newEntity);
			return true;
		}
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Catch a fish";
	}

	@Override
	public String getDescription() {
		return "Roll a d20 when you catch a fish. :)";
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
