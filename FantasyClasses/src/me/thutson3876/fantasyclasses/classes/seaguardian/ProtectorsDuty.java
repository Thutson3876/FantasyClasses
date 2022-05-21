package me.thutson3876.fantasyclasses.classes.seaguardian;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class ProtectorsDuty extends AbstractAbility {

	private static final List<EntityType> hostileAquatics = generateHostiles();
	
	private double dmgMod = 0.1;
	
	public ProtectorsDuty(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "A Protector's Duty";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.PRISMARINE_SHARD);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getDamager().equals(player))
			return false;
		
		if(!hostileAquatics.contains(e.getEntityType()))
			return false;
		
		e.setDamage(e.getDamage() * (1 + dmgMod));
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Attack a hostile aquatic entity";
	}

	@Override
	public String getDescription() {
		return "You deal &6" + AbilityUtils.doubleRoundToXDecimals(dmgMod * 100, 1) + "% &rmore damage to hostile aquatic entities";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		dmgMod = 0.1 * currentLevel;
	}

	private static List<EntityType> generateHostiles() {
		List<EntityType> hostiles = new ArrayList<>();
		
		hostiles.add(EntityType.DROWNED);
		hostiles.add(EntityType.GUARDIAN);
		hostiles.add(EntityType.ELDER_GUARDIAN);
		
		return hostiles;
	}
}
