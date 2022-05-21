package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class HonedEdge extends AbstractAbility {

	private double dmgMod = 1.0;
	
	public HonedEdge(Player p) {
		super(p);
	}
	
	//All damage adding abilities are adding double the stated amount
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Honed Edge";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.CHIPPED_ANVIL);	
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getDamager().equals(player))
			return false;
		
		if(!e.getCause().equals(DamageCause.ENTITY_ATTACK))
			return false;
		
		if(!MaterialLists.SWORD.contains(player.getInventory().getItemInMainHand().getType()))
			return false;
		
		e.setDamage(e.getDamage() + dmgMod);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Attack with a sword";
	}

	@Override
	public String getDescription() {
		return "Your refined skill with the blade allows you to deal &6" + AbilityUtils.doubleRoundToXDecimals(dmgMod, 2) + " &rmore damage with a sword";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		dmgMod = 1.0 * currentLevel;
	}

}
