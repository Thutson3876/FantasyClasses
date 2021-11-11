package me.thutson3876.fantasyclasses.classes.alchemy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.custombrewing.Brewable;
import me.thutson3876.fantasyclasses.custombrewing.BrewingRecipe;

public class EnhancedRepitoire extends AbstractAbility implements Brewable {

	private List<BrewingRecipe> recipes;
	private BrewingRecipe resistance = new BrewingRecipe(Material.DIAMOND, Color.TEAL, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40 * 20, 0));
	private BrewingRecipe wither = new BrewingRecipe(Material.WITHER_ROSE, Color.BLACK, new PotionEffect(PotionEffectType.WITHER, 20 * 20, 0));
	private BrewingRecipe nausea = new BrewingRecipe(Material.RED_MUSHROOM, Color.OLIVE, new PotionEffect(PotionEffectType.CONFUSION, 45 * 20, 0));
	private BrewingRecipe haste = new BrewingRecipe(Material.DIAMOND_BLOCK, Color.SILVER, new PotionEffect(PotionEffectType.FAST_DIGGING, 180 * 20, 0));
	private BrewingRecipe saturation = new BrewingRecipe(Material.GOLDEN_APPLE, Color.FUCHSIA, new PotionEffect(PotionEffectType.SATURATION, 90 * 20, 0));
	private BrewingRecipe hunger = new BrewingRecipe(Material.ROTTEN_FLESH, Color.MAROON, new PotionEffect(PotionEffectType.HUNGER, 45 * 20, 0));
	private BrewingRecipe luck = new BrewingRecipe(Material.GOLD_INGOT, Color.YELLOW, new PotionEffect(PotionEffectType.LUCK, 240 * 20, 0));
	private BrewingRecipe unluck = new BrewingRecipe(Material.COAL, Color.GRAY, new PotionEffect(PotionEffectType.UNLUCK, 240 * 20, 0));
	private BrewingRecipe glowing = new BrewingRecipe(Material.GLOW_BERRIES, Color.WHITE, new PotionEffect(PotionEffectType.GLOWING, 240 * 20, 0));

	
	public EnhancedRepitoire(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 10;
		this.displayName = "Enhanced Repitoire";
		this.skillPointCost = 2;
		this.maximumLevel = 1;
		recipes = new ArrayList<>();
		recipes.add(resistance);
		recipes.add(wither);
		recipes.add(nausea);
		recipes.add(saturation);
		recipes.add(haste);
		recipes.add(hunger);
		recipes.add(saturation);
		recipes.add(luck);
		recipes.add(unluck);
		recipes.add(glowing);

		this.createItemStack(Material.BREWING_STAND);
	}

	@Override
	public boolean trigger(Event event) {
		return false;
	}

	@Override
	public String getInstructions() {
		return "Refer to custom brewing chart to see new potions";
	}

	@Override
	public String getDescription() {
		return "You can now brew more kinds of potions";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

	@Override
	public List<BrewingRecipe> getBrewingRecipe() {
		return recipes;
	}

}
