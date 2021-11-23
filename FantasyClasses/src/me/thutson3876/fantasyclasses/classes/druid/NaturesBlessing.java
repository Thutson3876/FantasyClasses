package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class NaturesBlessing extends AbstractAbility {
	
	private BukkitTask task = null;
	private BukkitRunnable runnable = new BukkitRunnable() {

		@Override
		public void run() {
			if(player.getHealth() >= player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
				for(LivingEntity e : AbilityUtils.getNearbyPlayerPets(player, 15.0)) {
					if(e.getHealth() >= e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue())
						continue;

					else {
						AbilityUtils.heal(e, 1.0);
						return;
					}
						
				}
			}
			else {
				AbilityUtils.heal(player, 1.0);
				return;
			}
		}
		
	};
	
	public NaturesBlessing(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 8 * 20;
		this.displayName = "Nature's Blessing";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.FLOWERING_AZALEA_LEAVES);
	}

	@Override
	public boolean trigger(Event event) {
		return false;
	}

	@Override
	public String getInstructions() {
		return "Passive";
	}

	@Override
	public String getDescription() {
		return "Heals you for half a heart every &6" + (coolDowninTicks / 20) + " &rseconds. If you are at full health, an injured nearby pet will be healed instead";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		coolDowninTicks = (10 - (currentLevel * 2)) * 20;
		
		if(task != null)
			task.cancel();
		
		task = runnable.runTaskTimer(plugin, coolDowninTicks, coolDowninTicks);
	}

}
