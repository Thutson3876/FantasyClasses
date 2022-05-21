package me.thutson3876.fantasyclasses.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class Schematic {

	public static boolean detect(Block placed, Map<Integer[], Material> schematicMap) {
		int correctionCount = 0;
		
		for(Integer[] coords : schematicMap.keySet()) {
			Material type = placed.getRelative(coords[0], coords[1], coords[2]).getType();
			if(type.equals(schematicMap.get(coords))) {
				correctionCount++;
				continue;
			}
			break;
		}
		
		if(correctionCount != schematicMap.size()) {
			return false;
		}
		
		return true;
	}
	
	public static boolean detectAndRemove(Block placed, Map<Integer[], Material> schematicMap) {
		int correctionCount = 0;
		Set<Block> toRemove = new HashSet<>();
		toRemove.add(placed);
		
		for(Integer[] coords : schematicMap.keySet()) {
			Block b = placed.getRelative(coords[0], coords[1], coords[2]);
			toRemove.add(b);
			Material type = b.getType();
			if(type.equals(schematicMap.get(coords))) {
				correctionCount++;
				continue;
			}
			break;
		}
		
		if(correctionCount != schematicMap.size()) {
			return false;
		}
		
		for(Block b : toRemove) {
			b.setType(Material.AIR);
		}
		return true;
	}
}
