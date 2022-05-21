package me.thutson3876.fantasyclasses.collectible;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import me.thutson3876.fantasyclasses.FantasyClasses;

public class CollectibleListener implements Listener {
	private static final FantasyClasses plugin = FantasyClasses.getPlugin();

	public CollectibleListener() {
		plugin.registerEvents(this);
	}

	@EventHandler
	public void onChunkLoadEvent(ChunkLoadEvent e) {
		if(!e.isNewChunk())
			return;
		
		/*new BukkitRunnable() {

			@Override
			public void run() {
				plugin.setCollectiblesMetadata(e.getChunk());
			}
		}.runTaskLater(plugin, 20);*/
		
		Chunk chunk = e.getChunk();
		
		World world = chunk.getWorld();
		int x = chunk.getX() * 16;
		int z = chunk.getZ() * 16;

		// Ancient Technique Spawner
		int y = chunk.getWorld().getHighestBlockYAt(x, z);
		if (y > 160) {
			Block b = world.getHighestBlockAt(x, z);
			b.setType(Collectible.ANCIENT_TECHNIQUE.getType());
			plugin.addCollectible(x, y, z, Collectible.MINING_SCHEMATICS);
			b.setMetadata("Collectible", new FixedMetadataValue(plugin, Collectible.ANCIENT_TECHNIQUE));
			System.out.println("!!!Ancient Technique Spawned!!!");
			return;
		}

		/*
		// Mining Schematics Spawner
		Block b = world.getBlockAt(x, rng.nextInt(30) - 60, z);
		if (!(b.getType().equals(Material.DEEPSLATE) || b.getType().equals(Material.AIR)
				|| b.getType().equals(Material.CAVE_AIR)))
			return;
		//b.setType(Collectible.MINING_SCHEMATICS.getType());
		b.setType(Collectible.MINING_SCHEMATICS.getType());
		b.setMetadata("Collectible", new FixedMetadataValue(plugin, Collectible.MINING_SCHEMATICS));
		//System.out.println("!!!Mining Schematics Spawned!!!");
		//System.out.println(b.getLocation().toString());

		// Druidic Inscription Spawner
		//for(int i = 0; i < 15; i++) {
			b = world.getBlockAt(x + rng.nextInt(12), rng.nextInt(100) + 68, z + rng.nextInt(12));
			if (MaterialLists.LEAVES.contains(b.getType())) {
				b.setType(Collectible.DRUIDIC_INSCRIPTION.getType());
				b.setMetadata("Collectible", new FixedMetadataValue(plugin, Collectible.DRUIDIC_INSCRIPTION));
				System.out.println("!!!Druidic Inscription Spawned!!!");
				System.out.println(b.getLocation().toString());
				//break;
			}
		//}
		
		// Etched Glass Spawner
		//for(int i = 0; i < 15; i++) {
			b = world.getBlockAt(x + rng.nextInt(12), rng.nextInt(100) + 58, z + rng.nextInt(12));
			if (MaterialLists.ICE.contains(b.getType())) {
				b.setType(Collectible.ETCHED_GLASS.getType());
				b.setMetadata("Collectible", new FixedMetadataValue(plugin, Collectible.ETCHED_GLASS));
				//System.out.println("!!!Etched Ice Spawned!!!");
				//System.out.println(b.getLocation().toString());
				//break;
			}
		//}*/
	}
	
	@EventHandler
	public void onWorldInitEvent(WorldInitEvent e) {
		e.getWorld().getPopulators().add(new CollectiblePopulator());
	}

}
