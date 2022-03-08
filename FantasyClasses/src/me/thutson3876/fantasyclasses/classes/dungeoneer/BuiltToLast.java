package me.thutson3876.fantasyclasses.classes.dungeoneer;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemDamageEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class BuiltToLast extends AbstractAbility {

	private static Random rng = new Random();
	private double chance = 0.12;
	
	public BuiltToLast(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Built To Last";
		this.skillPointCost = 2;
		this.maximumLevel = 3;

		this.createItemStack(Material.NETHERITE_PICKAXE);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerItemDamageEvent)) 
			return false;
		
		PlayerItemDamageEvent e = (PlayerItemDamageEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		if(!MaterialLists.PICKAXE.contains(e.getItem().getType()))
			return false;
		
		if(rng.nextDouble() <= chance)
			e.setDamage(0);
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Use a pickaxe";
	}

	@Override
	public String getDescription() {
		return "Reduce the durability consumption of your pickaxes by &6" + (AbilityUtils.doubleRoundToXDecimals(chance, 1) * 100 + "%");
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		chance = 0.12 * currentLevel;
	}

}
