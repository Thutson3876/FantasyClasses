package me.thutson3876.fantasyclasses.classes.seaguardian;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;

public class SoothingWaters extends AbstractAbility implements Bindable {

	private int duration = 4 * 20;
	private int amp = 0;
	
	private Material type = null;
	
	public SoothingWaters(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 16 * 20;
		this.displayName = "Soothing Waters";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.WATER_BUCKET);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerDropItemEvent))
			return false;
		
		PlayerDropItemEvent e = (PlayerDropItemEvent)event;
		
		e.setCancelled(true);
		
		if(isOnCooldown())
			return false;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		if(!player.isInWater())
			return false;
		
		if(!e.getItemDrop().getItemStack().getType().equals(type))
			return false;
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration, amp));
		player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, duration, amp));
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Press your drop key while holding the bound item type in water";
	}

	@Override
	public String getDescription() {
		return "Gain a short burst of speed and regeneration &6" + (amp + 1) + " &rwhile in the water";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		amp = currentLevel - 1;
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
