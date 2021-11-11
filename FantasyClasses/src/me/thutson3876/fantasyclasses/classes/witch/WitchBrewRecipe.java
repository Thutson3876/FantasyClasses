package me.thutson3876.fantasyclasses.classes.witch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.Sphere;

public enum WitchBrewRecipe {

	CHICKEN((event) -> {
		event.getHitBlock().getWorld().spawnEntity(
				event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.CHICKEN);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.EGG), COW((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.COW);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.LEATHER), SHEEP((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.SHEEP);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.WHITE_WOOL), RABBIT((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.RABBIT);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.RABBIT_HIDE), SQUID((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.SQUID);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.INK_SAC), GLOW_SQUID((event) -> {
		event.getHitBlock().getWorld().spawnEntity(
				event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.GLOW_SQUID);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.GLOW_INK_SAC), TURTLE((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.SQUID);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.TURTLE_EGG), PANDA((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.PANDA);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.BAMBOO), ZOMBIE((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.ZOMBIE);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.ROTTEN_FLESH), SKELETON((event) -> {
		event.getHitBlock().getWorld().spawnEntity(
				event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.SKELETON);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.BONE), SLIME((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.SLIME);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.SLIME_BALL), PHANTOM((event) -> {
		event.getHitBlock().getWorld().spawnEntity(
				event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.PHANTOM);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.PHANTOM_MEMBRANE), GHAST((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.GHAST);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.GHAST_TEAR), MAGMA_CUBE((event) -> {
		event.getHitBlock().getWorld().spawnEntity(
				event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.MAGMA_CUBE);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.MAGMA_CREAM), BLAZE((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.BLAZE);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.BLAZE_ROD), EVOKER((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.EVOKER);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_LANTERN, Material.TOTEM_OF_UNDYING), VEX((event) -> {
		event.getHitBlock().getWorld()
				.spawnEntity(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.VEX);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.IRON_SWORD), RAVAGER((event) -> {
		event.getHitBlock().getWorld().spawnEntity(
				event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.RAVAGER);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_LANTERN, Material.SADDLE), SHULKER((event) -> {
		event.getHitBlock().getWorld().spawnEntity(
				event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), EntityType.SHULKER);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_LANTERN, Material.SHULKER_SHELL), TNT((event) -> {
		World world = event.getHitBlock().getWorld();
		event.getHitBlock().getLocation();
		world.createExplosion(event.getHitBlock().getLocation(), 4.0f);
		Firework firework = (Firework) world.spawnEntity(event.getHitBlock().getLocation(), EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();

		Type type = Type.CREEPER;
		Color c = Color.GREEN;

		FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c).withFade(c).with(type).trail(true)
				.build();

		meta.addEffect(effect);
		meta.setPower(0);
		firework.setFireworkMeta(meta);
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.GUNPOWDER),
	GROWTH((event) -> {
		for (Location loc : Sphere.generateSphere(event.getHitBlock().getLocation(), 5, false)) {
			Block b = loc.getBlock();
			BlockData data = b.getBlockData();
			if (!(data instanceof Ageable))
				return;

			Ageable ageable = (Ageable) data;
			int maxAge = ageable.getMaximumAge();
			if (ageable.getAge() < maxAge)
				return;
			int newAge = 2 + ageable.getAge();
			if(newAge > maxAge)
				newAge = maxAge;
			
			ageable.setAge(2 + ageable.getAge());
			b.setBlockData(ageable);
		}
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.BONE_MEAL),
	GLOWSTONE((event) -> {
		AbilityUtils.randomSpreadGeneration(event.getHitBlock().getRelative(event.getHitBlockFace()).getLocation(), Material.GLOWSTONE, 160, 20, false);
		
		
		
	}, Material.NETHER_WART, Material.BLAZE_POWDER, Material.SOUL_SAND, Material.GLOWSTONE);
	

	private ItemStack result;
	private Collection<Material> ingredients;
	private WitchesBrewAction action;

	static {

	}

	private WitchBrewRecipe(WitchesBrewAction action, Material... ingredients) {
		ItemStack result = AbilityUtils.getWitchesBrew();
		ItemMeta meta = result.getItemMeta();

		this.ingredients = Arrays.asList(ingredients);
		List<String> lore = meta.getLore();
		if(lore == null)
			lore = new ArrayList<>();
		
		for (Material mat : ingredients) {
			lore.add(mat.name().toLowerCase());
		}

		this.result = result;
		this.action = action;
	}

	public Collection<Material> getIngredients() {
		return this.ingredients;
	}

	public ItemStack getResult() {
		return this.result;
	}

	public void runAction(PotionSplashEvent event) {
		this.action.run(event);
	}
}
