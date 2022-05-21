package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class AxeWielder extends AbstractAbility {

	private double dmgMod = 1.1;
	
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
			player.getWorld().playSound(e.getEntity().getLocation(), Sound.BLOCK_GLASS_BREAK, 1.1f, 0.6f);
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
		return "When you hit a foe beneath you with an axe, deal &6" + AbilityUtils.doubleRoundToXDecimals(dmgMod, 2) + "&r times more damage";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		this.dmgMod = 1.0 + (0.1 * currentLevel);	
	}

}
