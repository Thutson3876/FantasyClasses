package me.thutson3876.fantasyclasses.classes.combat;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class ScytheSmith extends AbstractAbility {

	private static Map<Material, Integer> typeBonus = new HashMap<>();
	private double damageMod = 4.0;
	private final double attackSpeedMod = -3.7;
	private AttributeModifier attackSpeed;
	private AttributeModifier attackDamage;

	static {
		typeBonus.put(Material.WOODEN_HOE, 1);
		typeBonus.put(Material.STONE_HOE, 2);
		typeBonus.put(Material.IRON_HOE, 3);
		typeBonus.put(Material.GOLDEN_HOE, 2);
		typeBonus.put(Material.DIAMOND_HOE, 4);
		typeBonus.put(Material.NETHERITE_HOE, 5);
	}
	
	public ScytheSmith(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Scythe Smith";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		this.attackSpeed = new AttributeModifier(new UUID(8, 3), "Scythe Smith", attackSpeedMod, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);
		this.attackDamage = new AttributeModifier(new UUID(9, 3), "Scythe Smith", damageMod, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);

		this.createItemStack(Material.IRON_HOE);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof CraftItemEvent)) {
			return false;
		}

		CraftItemEvent e = (CraftItemEvent)event;
		
		if(!e.getWhoClicked().equals(this.player)) {
			return false;
		}
		
		ItemStack item = e.getCurrentItem();
		
		if(!MaterialLists.HOE.contains(item.getType()))
			return false;
		
		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return false;
		Collection<AttributeModifier> speedMods = meta.getAttributeModifiers(Attribute.GENERIC_ATTACK_SPEED);
		if(speedMods != null && speedMods.contains(attackSpeed)) {
			return false;
		}
		Collection<AttributeModifier> dmgMods = meta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE);
		if(dmgMods != null && dmgMods.contains(attackDamage)) {
			return false;
		}
		
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeed);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(new UUID(9, 3), "Scythe Smith", (damageMod * currentLevel) + typeBonus.get(item.getType()), Operation.ADD_NUMBER,
				EquipmentSlot.HAND));
		item.setItemMeta(meta);
		e.setCurrentItem(item);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Craft a hoe";
	}

	@Override
	public String getDescription() {
		return "Your crafted scythes have their attack speed decreased by &6"
				+ attackSpeedMod + " &rand their damage incrased by &6" + damageMod * currentLevel;
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return currentLevel > 0;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
