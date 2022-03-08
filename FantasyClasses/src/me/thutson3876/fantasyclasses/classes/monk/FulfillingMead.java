package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class FulfillingMead extends AbstractAbility {

	private int duration = 5 * 20;
	private int hungerGain = 1;
	private PotionEffect saturation;
	private PotionEffect regen = null;
	
	public FulfillingMead(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30 * 20;
		this.displayName = "Fulfilling Mead";
		this.skillPointCost = 1;
		this.maximumLevel = 2;
		

		this.createItemStack(Material.HONEY_BOTTLE);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerItemConsumeEvent))
			return false;
		
		PlayerItemConsumeEvent e = (PlayerItemConsumeEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getItem().getType().equals(Material.HONEY_BOTTLE))
			return false;
		
		player.setFoodLevel(player.getFoodLevel() + hungerGain);
		player.addPotionEffect(saturation);
		if(regen != null)
			player.addPotionEffect(regen);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Drink a bottle of honey";
	}

	@Override
	public String getDescription() {
		return "Drinking a bottle of honey (mead) will greatly improve your stamina for a short time, granting you saturation for &6" + (duration / 20) + " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		duration = (5 * currentLevel) * 20;
		saturation = new PotionEffect(PotionEffectType.SATURATION, duration, 0);
		if(currentLevel == this.maximumLevel)
			regen = new PotionEffect(PotionEffectType.REGENERATION, duration, 0);
		hungerGain = currentLevel;
	}

}
