package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;

public class Druidcraft extends AbstractAbility implements Bindable {

	private Material type = null;
	
	public Druidcraft(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 9 * 20;
		this.displayName = "Druidcraft";
		this.skillPointCost = 1;
		this.maximumLevel = 2;

		this.createItemStack(Material.OAK_SAPLING);	
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerInteractEvent))
			return false;
		
		if(isOnCooldown())
			return false;
		
		PlayerInteractEvent e = (PlayerInteractEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		if(e.getItem() == null || !e.getItem().getType().equals(this.type))
			return false;
		
		Block b = e.getClickedBlock();
		
		if(b == null)
			return false;
		
		b.applyBoneMeal(e.getBlockFace());
		b.applyBoneMeal(e.getBlockFace());
		b.applyBoneMeal(e.getBlockFace());
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Right-click a growable block";
	}

	@Override
	public String getDescription() {
		return "Bless a plant to grow at a much greater speed. This ability has a cooldown &6" + (this.coolDowninTicks / 20) + " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		this.coolDowninTicks = (12 - 3 * currentLevel) * 20;
	}

	@Override
	public Material getBoundType() {
		return type;
	}

	@Override
	public void setBoundType(Material type) {
		this.type = type;
	}

}
