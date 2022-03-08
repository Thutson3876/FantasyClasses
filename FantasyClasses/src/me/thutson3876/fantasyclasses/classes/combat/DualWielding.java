package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class DualWielding extends AbstractAbility {

	private int breakDuration = 50;
	private PotionEffect blindness;
	private double healAmt = 4.0;
	private AttributeModifier attackSpeed;

	public DualWielding(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 4 * 20;
		this.displayName = "Dual Wielding";
		this.skillPointCost = 2;
		this.maximumLevel = 1;
		this.attackSpeed = new AttributeModifier(displayName, 1.0, Operation.ADD_NUMBER);
		this.blindness = new PotionEffect(PotionEffectType.BLINDNESS, 60, 2);

		this.createItemStack(Material.IRON_SWORD);
	}

	@Override
	public boolean trigger(Event event) {
		if (event instanceof PlayerSwapHandItemsEvent) {

			PlayerSwapHandItemsEvent e = (PlayerSwapHandItemsEvent) event;

			if (!e.getPlayer().equals(this.player))
				return false;

			ItemStack offhandItem = e.getOffHandItem();

			if (offhandItem == null) {
				swordPassiveOff();
				return false;
			}

			if (MaterialLists.SWORD.getMaterials().contains(offhandItem.getType())) {
				swordPassiveOn();
			} else {
				swordPassiveOff();
			}

			return false;
		} else if (event instanceof InventoryCloseEvent) {

			InventoryCloseEvent e = (InventoryCloseEvent) event;
			if (!e.getPlayer().equals(this.player))
				return false;

			if (e.getInventory().getSize() != 46)
				return false;

			ItemStack offhandItem = e.getInventory().getItem(-106);

			if (offhandItem == null) {
				swordPassiveOff();
				return false;
			} else if (MaterialLists.SWORD.getMaterials().contains(offhandItem.getType())) {
				swordPassiveOn();
			} else {
				swordPassiveOff();
			}

			return false;
		} else if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

			if (!e.getDamager().equals(this.player)) {
				return false;
			}

			if (isOnCooldown())
				return false;

			ItemStack offhand = this.player.getInventory().getItemInOffHand();

			if (offhand == null)
				return false;
			Material mat = offhand.getType();

			if (MaterialLists.AXE.getMaterials().contains(mat)) {
				if (!(e.getEntity() instanceof Player)) {
					return false;
				}
				Player target = (Player) e.getEntity();
				if (target.isBlocking()) {
					target.setCooldown(Material.SHIELD, this.breakDuration);
					return true;
				}
			} else if (MaterialLists.HOE.getMaterials().contains(mat)) {
				if (!(e.getEntity() instanceof LivingEntity))
					return false;
				LivingEntity target = (LivingEntity) e.getEntity();

				if (e.getFinalDamage() > 1.0) {
					target.addPotionEffect(this.blindness);
					AbilityUtils.heal(this.player, this.healAmt);
					return true;
				}
			}
		}

		return false;
	}

	private void swordPassiveOn() {
		if (!this.player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getModifiers().contains(attackSpeed))
			this.player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(this.attackSpeed);
	}

	private void swordPassiveOff() {
		if (this.player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getModifiers().contains(attackSpeed))
			this.player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).removeModifier(this.attackSpeed);
	}

	@Override
	public String getName() {
		return this.displayName;
	}

	@Override
	public String getInstructions() {
		return "Equip a melee weapon in your offhand for a special bonus";
	}

	@Override
	public String getDescription() {
		return "While you have a sword in your offhand you attack faster, an axe allows you to break shields, and a hoe acts as a powerful rejuvenative scythe";
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

	@Override
	public void deInit() {
		swordPassiveOff();
	}
}
