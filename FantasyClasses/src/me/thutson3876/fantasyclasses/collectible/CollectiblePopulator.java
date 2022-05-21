package me.thutson3876.fantasyclasses.collectible;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class CollectiblePopulator extends BlockPopulator {

	private static final FantasyClasses plugin = FantasyClasses.getPlugin();
	
	@Override
	public void populate(WorldInfo worldInfo, Random random, int x, int z, LimitedRegion limitedRegion) {
		if(!worldInfo.getEnvironment().equals(Environment.NORMAL))
			return;
		
		int randX = x * 16 + random.nextInt(16);
		int randZ = z * 16 + random.nextInt(16);
		int randY = random.nextInt(30) - 60;
		
		Material type = limitedRegion.getType(randX, randY, randZ);
		// Mining Schematics Spawner
		if (!(type.equals(Material.DEEPSLATE) || type.equals(Material.AIR)
				|| type.equals(Material.CAVE_AIR)))
			return;
		// b.setType(Collectible.MINING_SCHEMATICS.getType());
		limitedRegion.setType(randX, randY, randZ, Collectible.MINING_SCHEMATICS.getType());
		plugin.addCollectible(randX, randY, randZ, Collectible.MINING_SCHEMATICS);
		// System.out.println("!!!Mining Schematics Spawned!!!");
		// System.out.println(b.getLocation().toString());

		// Druidic Inscription Spawner
		for (int i = 0; i < 50; i++) {
			randY = random.nextInt(100) + 58;
			type = limitedRegion.getType(randX, randY, randZ);
			if (MaterialLists.LEAVES.contains(type)) {
				limitedRegion.setType(randX, randY, randZ, Collectible.DRUIDIC_INSCRIPTION.getType());
				plugin.addCollectible(randX, randY, randZ, Collectible.DRUIDIC_INSCRIPTION);
				break;
			}
		}

		// Etched Glass Spawner
		for (int i = 0; i < 50; i++) {
			randY = random.nextInt(100) + 58;
			type = limitedRegion.getType(randX, randY, randZ);
			if (MaterialLists.ICE.contains(type)) {
				limitedRegion.setType(randX, randY, randZ, Collectible.ETCHED_GLASS.getType());
				plugin.addCollectible(randX, randY, randZ, Collectible.ETCHED_GLASS);
				break;
				// System.out.println("!!!Etched Ice Spawned!!!");
				// System.out.println(b.getLocation().toString());
			}
		}
	}

}
