package me.thutson3876.fantasyclasses.classes.highroller.randomabilities;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MidasTouch implements RandomAbility {
	
	@Override
	public void run(Player p) {
		World world = p.getWorld();
		Location loc = p.getLocation();
		Random rng = new Random();
		Block b = loc.getBlock();
		b.setType(Material.GOLD_BLOCK);
		BlockData data = b.getBlockData();
		
		for(int i = 0; i < rng.nextInt(5) + 3; i++) {
			FallingBlock fb = world.spawnFallingBlock(loc, data);
			fb.setVelocity(Vector.getRandom().multiply(rng.nextDouble() + 0.7));
		}
	}

}
