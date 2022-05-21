package me.thutson3876.fantasyclasses.custommobs.boss.uthroes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PolarBear;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.custommobs.AbstractCustomMob;

public class Ahsmi extends AbstractCustomMob {

	private final int freezeAmt = 100;
	private final LivingEntity rider;
	
	public Ahsmi(Location loc, LivingEntity rider) {
		super(loc);
		
		((PolarBear)ent).setAdult();
		this.rider = rider;
		
		Collection<ItemStack> loot = new HashSet<>();
		
		Random rng = new Random();
		int extraDrops = 64 - rng.nextInt(40);
		
		loot.add(new ItemStack(Material.COD, extraDrops));
		loot.add(new ItemStack(Material.SALMON, 64 - extraDrops));
		loot.add(new ItemStack(Material.ENDER_EYE, 2));
		
		this.setDrops(loot);
	}

	@Override
	protected void applyDefaults() {
		this.setMaxHealth(200);
		this.setAttackDamage(12);
		this.setSkillExpReward(15);
		this.setMoveSpeed(0.16f);
	}
	
	@Override
	public String getMetadataTag() {
		return "ahsmi";
	}

	@Override
	protected void targeted(EntityTargetEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void healed(EntityRegainHealthEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void tookDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager().equals(rider))
			e.setCancelled(true);
	}

	@Override
	protected void tookDamage(EntityDamageEvent e) {

	}

	@Override
	protected void dealtDamage(EntityDamageByEntityEvent e) {
		e.getEntity().setFreezeTicks(e.getEntity().getFreezeTicks() + freezeAmt);
	}

	@Override
	protected void died(EntityDeathEvent e) {
		ent.getWorld().playSound(ent.getLocation(), Sound.ENTITY_GOAT_SCREAMING_PREPARE_RAM, 12.0f, 0.5f);
	}

	@Override
	protected EntityType getEntityType() {
		return EntityType.POLAR_BEAR;
	}

}
