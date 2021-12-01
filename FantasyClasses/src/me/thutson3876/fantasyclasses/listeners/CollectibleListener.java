package me.thutson3876.fantasyclasses.listeners;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.metadata.FixedMetadataValue;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.util.Collectible;

public class CollectibleListener implements Listener {
	private static final FantasyClasses plugin = FantasyClasses.getPlugin();

	public CollectibleListener() {
		plugin.registerEvents(this);
	}
	
	@EventHandler
	public void onChunkLoadEvent(ChunkLoadEvent e) {
		if(!e.isNewChunk())
			return;
		
		Random rng = new Random();
		
		if(rng.nextDouble() > 0.001)
			return;
		
		Chunk chunk = e.getChunk();
		World world = chunk.getWorld();
		int x = chunk.getX();
		int z = chunk.getZ();
		
		//Ancient Technique Spawner
		if(chunk.getWorld().getHighestBlockYAt(x, z) > 80) {
			Block b = world.getHighestBlockAt(x, z);
			b.setType(Collectible.ANCIENT_TECHNIQUE.getType());
			b.setMetadata("Collectible", new FixedMetadataValue(plugin, Collectible.ANCIENT_TECHNIQUE));
			return;
		}
		
		//Mining Schematics Spawner
		if(rng.nextDouble() < 0.01) {
			Block b = world.getBlockAt(x, rng.nextInt(10) + 5, z);
			b.setType(Collectible.MINING_SCHEMATICS.getType());
			b.setMetadata("Collectible", new FixedMetadataValue(plugin, Collectible.MINING_SCHEMATICS));
			return;
		}
	}
	
	
}
