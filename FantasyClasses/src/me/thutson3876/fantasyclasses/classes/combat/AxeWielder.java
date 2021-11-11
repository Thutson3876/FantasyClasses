package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class AxeWielder extends AbstractAbility {

	private double dmgMod = 1.2;
	
	public AxeWielder(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 16;
		this.displayName = "Axe Wielder";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		
		this.createItemStack(Material.IRON_AXE);
		
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent)) {
			return false;
		}
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getDamager().equals(this.player))
			return false;
		
		if(!e.getCause().equals(DamageCause.ENTITY_ATTACK))
			return false;
		
		if(!MaterialLists.AXE.getMaterials().contains(player.getInventory().getItemInMainHand().getType()))
			return false;
		
		if(AbilityUtils.isCritical(player)) {
			e.setDamage(e.getDamage() * dmgMod);
			player.getWorld().playEffect(e.getEntity().getLocation(), Effect.POTION_BREAK, 2.0);
			return true;
		}
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Critically hit with an axe";
	}

	@Override
	public String getDescription() {
		return "Your critical hits with axes deal &6" + dmgMod * currentLevel + "&r times more damage";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		this.dmgMod = 1.0 + (0.2 * currentLevel);	
	}

}
