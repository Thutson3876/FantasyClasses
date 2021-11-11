package me.thutson3876.fantasyclasses.classes.combat;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class Swordsman extends AbstractAbility {

	private final float speedMod = 0.2f;
	private AttributeModifier attackSpeed;

	public Swordsman(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Swordsman";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		this.attackSpeed = new AttributeModifier(new UUID(6, 3), "Swordsman", speedMod, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);

		this.createItemStack(Material.IRON_SWORD);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof PlayerItemHeldEvent)) {
			return false;
		}

		PlayerItemHeldEvent e = (PlayerItemHeldEvent) event;

		if (!e.getPlayer().equals(this.player))
			return false;

		AttributeInstance att = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
		if (MaterialLists.SWORD.getMaterials().contains(player.getInventory().getItem(e.getNewSlot()).getType())) {
			if (!att.getModifiers().contains(attackSpeed)) {
				player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(attackSpeed);
				System.out.println("Attack speed boosted");
				return true;
			}
			return false;
		} else {
			if (att.getModifiers().contains(attackSpeed)) {
				player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).removeModifier(attackSpeed);
				System.out.println("Attack speed decreased");
				return false;
			}
		}

		return true;
	}

	@Override
	public String getName() {
		return displayName;
	}

	@Override
	public String getInstructions() {
		return "Wield a sword";
	}

	@Override
	public String getDescription() {
		return "Your sword swings have their attack speed increased by &6" + speedMod * currentLevel;
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
		attackSpeed = new AttributeModifier("Swordsman", speedMod * currentLevel, Operation.ADD_NUMBER);
	}

}
