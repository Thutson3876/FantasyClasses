package me.thutson3876.fantasyclasses.classes.dungeoneer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.DamageCauseList;

public class HotHeaded extends AbstractAbility {

	private double dmgReduction = 0.2;
	
	public HotHeaded(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Hot Headed";
		this.skillPointCost = 1;
		this.maximumLevel = 2;

		this.createItemStack(Material.MAGMA_BLOCK);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageEvent))
			return false;
		
		EntityDamageEvent e = (EntityDamageEvent)event;
		
		if(!e.getEntity().equals(player))
			return false;
		
		if(!DamageCauseList.FIRE.contains(e.getCause()))
			return false;
		
		e.setDamage(e.getDamage() * (1 - dmgReduction));
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Take fire damage";
	}

	@Override
	public String getDescription() {
		return "Gain &6" + (AbilityUtils.doubleRoundToXDecimals(dmgReduction, 1) * 100) + "% &rdamage reduction against fire damage";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		dmgReduction = 0.2 * currentLevel;
	}

}
