package me.thutson3876.fantasyclasses.classes.seaguardian;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.PotionList;

public class Cleanse extends AbstractAbility implements Bindable {

	private static final List<PotionEffectType> DEBUFFS = PotionList.DEBUFF.getPotList();

	private double maxAngle = 1.3;
	private double maxDistance = 6.0;
	private Material type = null;
	
	public Cleanse(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 12 * 20;
		this.displayName = "Cleanse";
		this.skillPointCost = 1;
		this.maximumLevel = 1;

		this.createItemStack(Material.MILK_BUCKET);	
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
		
		LivingEntity target = null;
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			target = AbilityUtils.getLivingEntityClosestToCursor(player, maxAngle, maxDistance, 0.3);
			if(target == null)
				target = player;
		}
		else if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			target = player;
		}
		else {
			return false;
		}
		
		boolean hadDebuff = false;
		for(PotionEffectType effectType : DEBUFFS) {
			if(target.hasPotionEffect(effectType)) {
				target.removePotionEffect(effectType);
				hadDebuff = true;
			}
		}
		if(hadDebuff) {
			target.getWorld().playSound(target.getLocation(), Sound.ITEM_TOTEM_USE, 0.8f, 1.3f);
			target.getWorld().spawnParticle(Particle.NAUTILUS, target.getLocation(), 8);
		}
			
		return hadDebuff;
	}

	@Override
	public String getInstructions() {
		return "Right-click with the bound item type";
	}

	@Override
	public String getDescription() {
		return "Cleanse the impurities from your target, removing all debuffs";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
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
