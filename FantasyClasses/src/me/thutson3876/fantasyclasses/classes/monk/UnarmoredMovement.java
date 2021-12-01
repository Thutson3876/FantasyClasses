package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class UnarmoredMovement extends AbstractAbility {

	private float speedMod = 0.02f;

	public UnarmoredMovement(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Unarmored Movement";
		this.skillPointCost = 1;
		this.maximumLevel = 5;

		this.createItemStack(Material.FEATHER);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof InventoryCloseEvent))
			return false;

		InventoryCloseEvent e = (InventoryCloseEvent)event;
		
		if(!e.getPlayer().equals(this.player))
			return false;
		
		if (!AbilityUtils.hasArmor(player)) {
			player.setWalkSpeed(0.2f + this.speedMod);
		} else {
			player.setWalkSpeed(0.2f);
		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "Wear no armor";
	}

	@Override
	public String getDescription() {
		return "While wearing no armor you move &6" + AbilityUtils.doubleRoundToXDecimals(speedMod * 100, 1)+ "% &rfaster";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		speedMod = 0.02f * currentLevel;
	}
	
	@Override
	public void deInit() {
		player.setWalkSpeed(0.2f);
	}
}
