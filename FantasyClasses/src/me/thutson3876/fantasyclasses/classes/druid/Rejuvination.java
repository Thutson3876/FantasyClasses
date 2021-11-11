package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class Rejuvination extends AbstractAbility implements Bindable {

	private Material boundType = null;
	private double healAmt = 4.0;
	private int duration = 6 * 20;
	private PotionEffect regen = null;
	private double maxAngle = 0.5;
	private double maxDistance = 6.0;
	
	public Rejuvination(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 12 * 20;
		this.displayName = "Rejuvination";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.SMALL_DRIPLEAF);		
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerInteractEvent))
			return false;
		
		PlayerInteractEvent e = (PlayerInteractEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		if(e.getItem() == null || !e.getItem().getType().equals(this.boundType))
			return false;
		
		if(isOnCooldown())
			return false;
		
		LivingEntity target = null;
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			target = AbilityUtils.getLivingEntityClosestToCursor(player, maxAngle, maxDistance, 0.1);
			if(target == null)
				target = player;
		}
		else if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			target = player;
		}
		else {
			return false;
		}
		
		AbilityUtils.heal(target, healAmt);
		if(regen != null)
			target.addPotionEffect(regen);
			
		return true;
	}

	@Override
	public String getInstructions() {
		return "Right-click with your bound item type";
	}

	@Override
	public String getDescription() {
		return "Restore &6" + healAmt + " &rhealth to your target. Targeting the ground heals yourself instead. At max level, also applies regeneration";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		healAmt = 1.0 + 3.0 * currentLevel;
		if(currentLevel >= maximumLevel)
			regen = new PotionEffect(PotionEffectType.REGENERATION, duration, 0);
	}

	@Override
	public Material getBoundType() {
		return boundType;
	}

	@Override
	public void setBoundType(Material type) {
		boundType = type;
	}

}
