package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class GreenThumb extends AbstractAbility {

	private double chance = 0.15;
	
	public GreenThumb(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Green Thumb";
		this.skillPointCost = 1;
		this.maximumLevel = 4;

		this.createItemStack(Material.IRON_HOE);
	}

	@Override
	public boolean trigger(Event event) {
		if(event instanceof PlayerHarvestBlockEvent) {
			PlayerHarvestBlockEvent e = (PlayerHarvestBlockEvent)event;
			
			if(!e.getPlayer().equals(player))
				return false;
			
			for(ItemStack i : e.getItemsHarvested()) {
				player.getWorld().dropItemNaturally(e.getHarvestedBlock().getLocation(), i);
			}
			
			feedback();
			return true;
		}
		else if(event instanceof BlockBreakEvent) {
			BlockBreakEvent e = (BlockBreakEvent)event;
			
			if(!e.getPlayer().equals(player))
				return false;
			
			if(!MaterialLists.CROP.contains(e.getBlock().getType()))
				return false;
			
			if(e.isDropItems()) {
				for(ItemStack i : e.getBlock().getDrops()) {
					player.getWorld().dropItemNaturally(e.getBlock().getLocation(), i);
				}
				
				feedback();
				return true;
			}
		}
		
		return false;
	}
	
	private void feedback() {
		Location loc = player.getLocation();
		player.playSound(loc, Sound.ITEM_BONE_MEAL_USE, 1.0f, 1.5f);
		player.spawnParticle(Particle.COMPOSTER, loc, 20);
	}

	@Override
	public String getInstructions() {
		return "Harvest a crop";
	}

	@Override
	public String getDescription() {
		return "When you harvest a crop, you have a &6" + AbilityUtils.doubleRoundToXDecimals(chance * 100, 2) + "% &rchance to double the drops";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		chance = 0.15 * currentLevel;
	}

}
