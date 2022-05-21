package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;

public class Camoflauge extends AbstractAbility implements Bindable {

	private int duration = 4 * 20;
	
	private Material type = null;
	
	public Camoflauge(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 24 * 20;
		this.displayName = "Camoflauge";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.DARK_OAK_LEAVES);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerSwapHandItemsEvent))
			return false;
		
		PlayerSwapHandItemsEvent e = (PlayerSwapHandItemsEvent)event;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		boolean hasBoundType = false;
		if(e.getMainHandItem() != null) {
			hasBoundType = e.getMainHandItem().getType().equals(type);
		}
		if(!hasBoundType && e.getOffHandItem() != null) {
			hasBoundType = e.getOffHandItem().getType().equals(type);
		}
		if(!hasBoundType)
			return false;
		
		e.setCancelled(true);
		addPotionEffects();
		return true;
	}

	@Override
	public String getInstructions() {
		return "Swap hands while holding bound item type";
	}

	@Override
	public String getDescription() {
		return "Blend in with your surroundings gaining invisibility and speed for &6" + (duration / 20) + " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		duration = (2 + (3 * currentLevel)) * 20;
	}

	private void addPotionEffects() {
	    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, this.duration, 0));
	    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, this.duration, 0));
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
