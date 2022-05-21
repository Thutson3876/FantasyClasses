package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class Archer extends AbstractAbility {

	private int pierceLevel = 1;
	  
	private double speedMult = 1.1;
	
	public Archer(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Archer";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		
		this.createItemStack(Material.BOW);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityShootBowEvent))
			return false;
		
		EntityShootBowEvent e = (EntityShootBowEvent)event;
		
		if(!e.getEntity().equals(player))
			return false;
		
		if (!(e.getProjectile() instanceof AbstractArrow))
		      return false;
		
		AbstractArrow arrow = (AbstractArrow)e.getProjectile();
	    arrow.setPierceLevel(arrow.getPierceLevel() + this.pierceLevel);
	    arrow.setVelocity(arrow.getVelocity().multiply(this.speedMult));
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Shoot an arrow";
	}

	@Override
	public String getDescription() {
		return "Your arrows are shot with such ferocity that they can pierce through &6" + pierceLevel + " &radditional foe(s)";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		this.pierceLevel = 1 * this.currentLevel;
	    this.speedMult = 1.0D + 0.1D * currentLevel;
	}

}
