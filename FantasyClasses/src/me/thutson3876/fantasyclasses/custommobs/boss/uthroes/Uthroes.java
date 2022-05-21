package me.thutson3876.fantasyclasses.custommobs.boss.uthroes;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.custommobs.boss.AbstractBoss;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class Uthroes extends AbstractBoss {

	private final PolarBear ahsmi;
	private final PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 5 * 20, 3);
	
	private final int freezeAmt = 120;
	
	public Uthroes(Location loc) {
		super(loc, "&bUthroes");
		this.setBossBar("&bUthroes", BarColor.BLUE, BarStyle.SOLID, new BarFlag[0]);
		
		((Ageable)ent).setAdult();
		
		Collection<ItemStack> loot = new HashSet<>();
		
		loot.add(new ItemStack(Material.TRIDENT));
		loot.add(new ItemStack(Material.CONDUIT));
		loot.add(new ItemStack(Material.FROSTED_ICE, 64));
		loot.add(new ItemStack(Material.BLUE_ICE, 64));
		
		this.setDrops(loot);
		
		this.setGear();
		
		ahsmi = (PolarBear) ent.getWorld().spawnEntity(ent.getLocation(), EntityType.POLAR_BEAR);
		ahsmi.addPassenger(ent);
		
		abilities.add(new FrozenPrison());
		abilities.add(new RemorselessWinter());
		abilities.add(new WinteryGrasp());
		abilities.add(new SummonUndead());
		abilities.add(new LeechingGrasp());
		
		this.startAbilityTick();
	}
	
	@Override
	protected void applyDefaults() {
		this.setMaxHealth(300);
		this.setAttackDamage(10);
		this.setSkillExpReward(50);
		this.setMoveSpeed(0.11f);
	}
	
	@Override
	protected void targeted(EntityTargetEvent e) {
		if(e.getTarget() instanceof LivingEntity)	
			ahsmi.setTarget((LivingEntity)e.getTarget());
	}
	
	@Override
	protected void dealtDamage(EntityDamageByEntityEvent e) {
		AbilityUtils.heal(ahsmi, e.getFinalDamage());
		e.getEntity().setFreezeTicks(e.getEntity().getFreezeTicks() + freezeAmt);
	}
	
	@Override
	protected void tookDamage(EntityDamageByEntityEvent e) {
		if(e.getCause().equals(DamageCause.FREEZE))
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onProjectileHitEvent(ProjectileHitEvent e) {
		if(!(e.getEntity() instanceof Snowball) || e.isCancelled() || !e.getEntity().getShooter().equals(ent)) {
			return;
		}
		
		Entity target = e.getHitEntity();
		if(target == null)
			return;
		
		target.setVelocity(AbilityUtils.getVectorBetween2Points(target.getLocation(), ent.getLocation(), 0.6));
		
		if(target instanceof LivingEntity) {
			LivingEntity livingTarget = (LivingEntity) target;
			livingTarget.addPotionEffect(slowness);
			livingTarget.setFreezeTicks(livingTarget.getFreezeTicks() + freezeAmt);
			livingTarget.damage(6.0, ent);
		}
		
		ent.getWorld().playSound(ent.getLocation(), Sound.BLOCK_CONDUIT_AMBIENT, 10.0f, 0.8f);
		target.getWorld().playSound(target.getLocation(), Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 10.0f, 0.1f);
	}
	
	@Override
	public String getMetadataTag() {
		return "uthroes";
	}
	
	private void setGear() {
		EntityEquipment equip = ent.getEquipment();
		
		ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
		boots.addEnchantment(Enchantment.BINDING_CURSE, 1);
		boots.addEnchantment(Enchantment.DURABILITY, 3);
		ItemStack legs = new ItemStack(Material.NETHERITE_LEGGINGS);
		legs.addEnchantment(Enchantment.BINDING_CURSE, 1);
		legs.addEnchantment(Enchantment.DURABILITY, 3);
		ItemStack chest = new ItemStack(Material.NETHERITE_CHESTPLATE);
		chest.addEnchantment(Enchantment.BINDING_CURSE, 1);
		chest.addEnchantment(Enchantment.DURABILITY, 3);
		ItemStack helm = new ItemStack(Material.NETHERITE_HELMET);
		helm.addEnchantment(Enchantment.BINDING_CURSE, 1);
		helm.addEnchantment(Enchantment.DURABILITY, 3);
		
		equip.setBoots(boots);
		equip.setBootsDropChance(0.1f);
		equip.setLeggings(legs);
		equip.setBootsDropChance(0.1f);
		equip.setChestplate(chest);
		equip.setChestplateDropChance(0.1f);
		equip.setHelmet(helm);
		equip.setHelmetDropChance(0.1f);
		
		equip.setItemInMainHand(new ItemStack(Material.TRIDENT));
	}

	@Override
	protected EntityType getEntityType() {
		return EntityType.ZOMBIE;
	}
}
