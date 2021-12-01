package me.thutson3876.fantasyclasses.classes.druid;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTameEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class BeastMaster extends AbstractAbility {

	private double healthMod = 0.3;
	private double damageMod = 0.2;
	private AttributeModifier attackDmg;
	private AttributeModifier maxHealth;
	
	private List<LivingEntity> pets = new ArrayList<>();

	public BeastMaster(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 20;
		this.displayName = "Beast Master";
		this.skillPointCost = 1;
		this.maximumLevel = 5;
		maxHealth = new AttributeModifier("maxHealth", healthMod, Operation.ADD_SCALAR);
		attackDmg = new AttributeModifier("attackDmg", damageMod, Operation.ADD_SCALAR);

		this.createItemStack(Material.BONE);
	}

	@Override
	public boolean trigger(Event event) {
		if (event instanceof EntityTameEvent) {

			EntityTameEvent e = (EntityTameEvent) event;

			if (!e.getOwner().equals(player))
				return false;
			
			AbilityUtils.setMaxHealth(e.getEntity(), attackDmg);
			AbilityUtils.setAttackDamage(e.getEntity(), maxHealth);
			
			pets.add(e.getEntity());
			return true;
		}
		else if(event instanceof EntityDeathEvent) {
			EntityDeathEvent e = (EntityDeathEvent)event;
			pets.remove(e.getEntity());
			return false;
		}
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Tame an animal";
	}

	@Override
	public String getDescription() {
		return "Your tamed animals have &6" + AbilityUtils.doubleRoundToXDecimals(healthMod * 100, 1) + "% &rmore health and deal &6" + AbilityUtils.doubleRoundToXDecimals(damageMod * 100, 1) + "% &rmore damage";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		for(LivingEntity e : pets) {
			e.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(maxHealth);
			e.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).removeModifier(attackDmg);
		}
		
		healthMod = 0.3 * currentLevel;
		damageMod = 0.2 * currentLevel;
		maxHealth = new AttributeModifier("maxHealth", healthMod, Operation.ADD_SCALAR);
		attackDmg = new AttributeModifier("attackDmg", damageMod, Operation.ADD_SCALAR);
		
		for(LivingEntity e : pets) {
			e.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(maxHealth);
			e.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(attackDmg);
		}
	}

}
