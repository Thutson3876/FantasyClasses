package me.thutson3876.fantasyclasses.collectible;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class CollectibleGenerator extends ChunkGenerator {

	List<BlockPopulator> populators = new ArrayList<>();
	
	public CollectibleGenerator() {
		populators.add(new CollectiblePopulator());
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world){
		return populators;
	}
}
