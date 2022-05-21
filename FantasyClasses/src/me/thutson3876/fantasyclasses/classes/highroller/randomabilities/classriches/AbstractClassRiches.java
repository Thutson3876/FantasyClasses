package me.thutson3876.fantasyclasses.classes.highroller.randomabilities.classriches;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.Sphere;

public abstract class AbstractClassRiches {

	protected List<ItemStack> riches = new ArrayList<>();
	
	public List<ItemStack> getFullRichesList() {
		return riches;
	}
	
	public List<ItemStack> generateRiches(){
		Random rng = new Random();
		List<ItemStack> list = riches;
		for(int i = 0; i < list.size() / 2; i++) {
			list.remove(rng.nextInt(list.size()));
		}
		
		return list;
	}
	
	public void launchRiches(Entity ent) {
		World world = ent.getWorld();
		Vector v = new Vector(0, 2, 0);
		
		for(ItemStack i : generateRiches()) {
			if(i == null || i.getType().equals(Material.AIR))
				continue;
			Item item = (Item) world.dropItemNaturally(ent.getLocation(), i);
			item.setVelocity(v.add(Vector.getRandom().multiply(0.1)).normalize().multiply(0.5));
		}
	}
	
	public void explodeRiches(Entity ent) {
		Random rng = new Random();
		Location entLoc = ent.getLocation();
		World world = ent.getWorld();
		List<Location> sphere = Sphere.generateSphere(entLoc, 3, true);
		
		for(ItemStack i : generateRiches()) {
			if(i == null || i.getType().equals(Material.AIR))
				continue;
			Item item = (Item) world.dropItemNaturally(entLoc, i);
			item.setVelocity(AbilityUtils.getDifferentialVector(entLoc, sphere.get(rng.nextInt(sphere.size())).multiply(0.5)));
		}
	}
	
	public abstract void generateEvent(Player p);
}
