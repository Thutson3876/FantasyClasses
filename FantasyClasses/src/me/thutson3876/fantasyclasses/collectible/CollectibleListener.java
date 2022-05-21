package me.thutson3876.fantasyclasses.listeners;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.metadata.FixedMetadataValue;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.util.Collectible;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class CollectibleListener implements Listener {
	private static final FantasyClasses plugin = FantasyClasses.getPlugin();

	public CollectibleListener() {
		plugin.registerEvents(this);
	}

	@EventHandler
	public void onChunkLoadEvent(ChunkLoadEvent e) {
		if (!e.isNewChunk())
			return;

		Random rng = new Random();

		if (rng.nextDouble() > 0.005)
			return;

		Chunk chunk = e.getChunk();
		World world = chunk.getWorld();
		int x = chunk.getX();
		int z = chunk.getZ();

		// Ancient Technique Spawner
		if (chunk.getWorld().getHighestBlockYAt(x, z) > 160) {
			Block b = world.getHighestBlockAt(x, z);
			b.setType(Collectible.ANCIENT_TECHNIQUE.getType());
			b.setMetadata("Collectible", new FixedMetadataValue(plugin, Collectible.ANCIENT_TECHNIQUE));
			System.out.println("!!!Ancient Technique Spawned!!!");
			return;
		}

		// Mining Schematics Spawner
		Block b = world.getBlockAt(x, rng.nextInt(30) - 60, z);
		if (!(b.getType().equals(Material.DEEPSLATE) || b.getType().equals(Material.AIR)
				|| b.getType().equals(Material.CAVE_AIR)))
			return;
		b.setType(Collectible.MINING_SCHEMATICS.getType());
		b.setMetadata("Collectible", new FixedMetadataValue(plugin, Collectible.MINING_SCHEMATICS));
		System.out.println("!!!Mining Schematics Spawned!!!");
		System.out.println(b.getLocation().toString());

		// Druidic Inscription Spawner
		b = world.getBlockAt(x + rng.nextInt(12), rng.nextInt(60) + 60, z + rng.nextInt(12));
		if (!MaterialLists.LEAVES.contains(b.getType()))
			return;
		b.setType(Collectible.DRUIDIC_INSCRIPTION.getType());
		b.setMetadata("Collectible", new FixedMetadataValue(plugin, Collectible.DRUIDIC_INSCRIPTION));
		System.out.println("!!!Druidic Inscription Spawned!!!");
		System.out.println(b.getLocation().toString());
	}

}
