package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class LifeRip extends AbstractAbility {

	private double healAmt = 2.0;
	
	public LifeRip(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 8 * 20;
		this.displayName = "Life Rip";
		this.skillPointCost = 1;
		this.maximumLevel = 2;
		
		this.createItemStack(Material.BEETROOT);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent)) {
			return false;
		}
		
		if(this.isOnCooldown())
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getDamager().equals(this.player))
			return false;

		Entity ent = e.getEntity();
		if(!(ent instanceof LivingEntity))
			return false;
		
		if(!e.getCause().equals(DamageCause.ENTITY_ATTACK))
			return false;
		
		if(!(MaterialLists.HOE.getMaterials().contains(player.getInventory().getItemInMainHand().getType())))
			return false;
		
		if(e.getFinalDamage() < 2.0)
			return false;
		
		ent.getWorld().playSound(ent.getLocation(), Sound.ENTITY_ZOMBIE_INFECT, 1.4f, 0.7f);
		ent.getWorld().spawnParticle(Particle.CRIMSON_SPORE, ent.getLocation(), 3);
		AbilityUtils.heal(player, healAmt);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Hit an entity with a scythe";
	}

	@Override
	public String getDescription() {
		return "When striking an entity with a scythe, heal for &6" + healAmt;
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		healAmt *= currentLevel;
	}

}
