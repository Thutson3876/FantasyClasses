package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class Disarm extends AbstractAbility {

	private int duration = 4 * 20;
	
	public Disarm(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 15 * 20;
		this.displayName = "Disarm";
		this.skillPointCost = 1;
		this.maximumLevel = 1;

		this.createItemStack(Material.LEAD);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getDamager().equals(player))
			return false;
		
		if(!e.getCause().equals(DamageCause.ENTITY_ATTACK))
			return false;
		
		Entity ent = e.getEntity();
		
		if(ent instanceof LivingEntity) {
			((LivingEntity)ent).setArrowCooldown(duration);
		
			if(ent instanceof Player) {
				Player target = ((Player)ent);
				ItemStack used = target.getItemInUse();
				if(used != null)
					target.setCooldown(used.getType(), duration);
				
				ItemStack hand = target.getInventory().getItemInMainHand();
				if(hand != null)
					target.setCooldown(hand.getType(), duration);
				
				ItemStack off = target.getInventory().getItemInOffHand();
				if(off != null)
					target.setCooldown(off.getType(), duration);
			}
		}
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Strike an entity";
	}

	@Override
	public String getDescription() {
		return "When you attack a foe, cause it to have an attack cooldown of &6" + duration / 20 + " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
