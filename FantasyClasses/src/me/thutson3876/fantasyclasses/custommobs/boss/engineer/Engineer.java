package me.thutson3876.fantasyclasses.custommobs.boss.engineer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.custommobs.boss.AbstractBoss;

public class Engineer extends AbstractBoss {

	public Engineer(Location loc) {
		super(loc, "&4Engineer");
		this.setBossBar("&4Engineer", BarColor.RED, BarStyle.SOLID, new BarFlag[0]);
		
		((Zombie)ent).setAdult();
		
		Collection<ItemStack> loot = new HashSet<>();
		Random rng = new Random();
		int extraDrops = 13 - rng.nextInt(8);
		
		loot.add(new ItemStack(Material.TNT, rng.nextInt(7) + 6));
		loot.add(new ItemStack(Material.IRON_INGOT, 2 * (rng.nextInt(6) + 6)));
		loot.add(new ItemStack(Material.NETHERITE_SCRAP, extraDrops));
		loot.add(new ItemStack(Material.DIAMOND, 13 - extraDrops));
		this.setDrops(loot);
		
		this.setGear();
		
		abilities.add(new Bombardment());
		abilities.add(new SummonStudents());
		abilities.add(new FireworkLauncher());
		
		this.startAbilityTick();
	}
	
	@Override
	protected void applyDefaults() {
		this.setMaxHealth(200);
		this.setAttackDamage(10);
		this.setSkillExpReward(30);
	}
	
	@Override
	protected void tookDamage(EntityDamageEvent e) {
		if(e.getEntity().equals(ent) && e.getCause().equals(DamageCause.BLOCK_EXPLOSION)) {
			e.setCancelled(true);
		}
	}
	
	@Override
	protected void tookDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity().equals(ent) && e.getCause().equals(DamageCause.ENTITY_EXPLOSION)) {
			e.setCancelled(true);
		}
	}

	@Override
	public String getMetadataTag() {
		return "engineer";
	}
	
	private void setGear() {
		EntityEquipment equip = ent.getEquipment();
		
		equip.setBoots(new ItemStack(Material.IRON_BOOTS));
		equip.setBootsDropChance(0.25f);
		equip.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		equip.setBootsDropChance(0.25f);
		equip.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		equip.setChestplateDropChance(0.25f);
		equip.setHelmet(new ItemStack(Material.IRON_HELMET));
		equip.setHelmetDropChance(0.25f);
		
		equip.setItemInMainHand(new ItemStack(Material.GOLDEN_PICKAXE));
		equip.setItemInOffHand(new ItemStack(Material.TNT));
	}

	@Override
	protected EntityType getEntityType() {
		return EntityType.ZOMBIE;
	}

}
