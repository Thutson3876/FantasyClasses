package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class Regrowth extends AbstractAbility {

	public Regrowth(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 0;
		this.displayName = "Regrowth";
		this.skillPointCost = 1;
		this.maximumLevel = 2;

		this.createItemStack(Material.COMPOSTER);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof BlockBreakEvent))
			return false;

		BlockBreakEvent e = (BlockBreakEvent) event;

		if (!e.getPlayer().equals(player))
			return false;

		Block block = e.getBlock();
		Material type = block.getType();

		if (!MaterialLists.CROP.contains(type))
			return false;

		if (!AbilityUtils.isHarvestable(block.getBlockData()))
			return false;

		new BukkitRunnable() {
			public void run() {
				if (block.getType().equals(Material.AIR) && block.getRelative(BlockFace.DOWN).getType().equals(Material.FARMLAND))
					block.setType(type);
			}
		}.runTaskLater(plugin, 5L);

		return true;
	}

	@Override
	public String getInstructions() {
		return "Harvest a crop";
	}

	@Override
	public String getDescription() {
		return "When you harvest a crop, it automatically replants itself";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
