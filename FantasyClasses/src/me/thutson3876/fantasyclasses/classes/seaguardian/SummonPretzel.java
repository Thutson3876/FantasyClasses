package me.thutson3876.fantasyclasses.classes.seaguardian;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.TropicalFish.Pattern;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public class SummonPretzel extends AbstractAbility implements Bindable {
	
	private Material type = null;
	
	private int counter = 0;
	private double healAmt = 0.5;
	private int tickRate = 10;
	private int duration = 10 * 20;
	private double range = 6;
	
	public SummonPretzel(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 18 * 20;
		this.displayName = "Summon Pretzel";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.TROPICAL_FISH);	
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
		
		boolean correctType = false;
		
		if(e.getMainHandItem() != null) {
			if(e.getMainHandItem().getType().equals(type))
				correctType = true;
		}
		if(e.getOffHandItem() != null) {
			if(e.getOffHandItem().getType().equals(type))
				correctType = true;
		}
			
		if(!correctType)
			return false;
		
		spawnPretzel();
		
		return true;
	}

	private void spawnPretzel() {
		TropicalFish pretzel = (TropicalFish) player.getWorld().spawnEntity(player.getLocation(), EntityType.TROPICAL_FISH);
		pretzel.setPattern(Pattern.GLITTER);
		pretzel.setPatternColor(DyeColor.WHITE);
		pretzel.setBodyColor(DyeColor.PINK);
		pretzel.setCustomName(ChatUtils.chat("&6Pretzel"));
		pretzel.setCustomNameVisible(true);
		
		new BukkitRunnable() {

			@Override
			public void run() {
				if(pretzel == null || pretzel.isDead()) {
					counter = 0;
					this.cancel();
				}
				
				for(LivingEntity ent : AbilityUtils.getNearbyLivingEntities(pretzel, range, range, range)) {
					if(ent instanceof Mob)
						continue;
					
					AbilityUtils.heal(ent, healAmt);
				}
				
				if(counter > (duration / tickRate)) {
					counter = 0;
					pretzel.remove();
					this.cancel();
				}
				
				counter++;
			}
			
		}.runTaskTimer(plugin, tickRate, tickRate);
	}

	@Override
	public String getInstructions() {
		return "Swap hands with bound item type";
	}

	@Override
	public String getDescription() {
		return "Summon your faithful companion, a fish named &6Pretzel &rthat heals you and your allies for a short duration";
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
