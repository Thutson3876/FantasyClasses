package me.thutson3876.fantasyclasses.classes.highroller.randomabilities.classriches;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.collectible.Collectible;
import me.thutson3876.fantasyclasses.custommobs.UndeadMiner;
import me.thutson3876.fantasyclasses.util.ChatUtils;
import me.thutson3876.fantasyclasses.util.Sphere;

public class DungeoneerRiches extends AbstractClassRiches {

	public DungeoneerRiches() {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(Material.COAL_ORE, 32));
		list.add(new ItemStack(Material.COPPER_ORE, 32));
		list.add(new ItemStack(Material.GOLD_ORE, 16));
		list.add(new ItemStack(Material.IRON_ORE, 16));
		list.add(new ItemStack(Material.LAPIS_ORE, 16));
		list.add(new ItemStack(Material.REDSTONE_ORE, 16));
		list.add(new ItemStack(Material.EMERALD_ORE, 16));
		list.add(new ItemStack(Material.DIAMOND_ORE, 8));
		
		this.riches = list;
	}
	
	@Override
	public void generateEvent(Player p) {
		
		this.explodeRiches(p);
		
		for(Location loc : Sphere.generateSphere(p.getLocation(), 4, true)) {
			loc.getBlock().setType(Material.DEEPSLATE);
		}
		
		new UndeadMiner(p.getLocation());
		
		p.sendMessage(ChatUtils.chat(Collectible.MINING_SCHEMATICS.getRandomLore()));
	}

}
