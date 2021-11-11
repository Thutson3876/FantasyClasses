package me.thutson3876.fantasyclasses.custombrewing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.FantasyClasses;

public class BrewingRecipe {
	private static final FantasyClasses plugin = FantasyClasses.getPlugin();
	private static List<BrewingRecipe> recipes = new ArrayList<BrewingRecipe>();
	private ItemStack ingredient;
	private BrewAction action;
	private boolean perfect;
	private ItemStack result;
	private List<PotionEffect> potionEffects;

	public BrewingRecipe(ItemStack ingredient, BrewAction action, boolean perfect) {
		this.ingredient = ingredient;
		
		if(action == null) {
			this.action = defaultAction();
		}
		else {
			this.action = action;
		}
		
		this.perfect = perfect;
		recipes.add(this);
	}

	public BrewingRecipe(Material ingredient, Color color, BrewAction action, PotionEffect... effects) {
		this(new ItemStack(ingredient), action, false);
		potionEffects = Arrays.asList(effects);
		this.result = initResult(color, effects);
	}
	
	public BrewingRecipe(Material ingredient, Color color, PotionEffect... effects) {
		this(new ItemStack(ingredient), null, false);
		potionEffects = Arrays.asList(effects);
		this.result = initResult(color, effects);
	}

	private ItemStack initResult(Color color, PotionEffect... effects) {
		ItemStack pot = new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta) pot.getItemMeta();
		for(PotionEffect effect : effects) {
			meta.addCustomEffect(effect, true);
		}
		meta.setColor(color);
		pot.setItemMeta(meta);
		
		return pot;
	}
	
	private BrewAction defaultAction() {
		return (inventory, item, ingredient) -> {
			if (!item.getType().equals(Material.POTION) && !item.getType().equals(Material.SPLASH_POTION))
				return;
			
			PotionMeta meta = (PotionMeta) item.getItemMeta();
			if(meta.equals((PotionMeta) result.getItemMeta())) {
				ItemStack newItem = checkCommons(ingredient, item);
				if(newItem != null) {
					item = newItem;
				}
				return;
			}
			
			PotionType type = meta.getBasePotionData().getType();
			if(type.equals(PotionType.AWKWARD)) {
				item = this.result;
			}
		};
	}
	
	public ItemStack getIngredient() {
		return ingredient;
	}

	public BrewAction getAction() {
		return action;
	}

	public boolean isPerfect() {
		return perfect;
	}
	
	public ItemStack getResult() {
		return result;
	}
	
	public ItemStack ampUp() {
		ItemStack pot = new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta) result.getItemMeta();
		List<PotionEffect> newEffects = new ArrayList<>();
		for(PotionEffect effect : meta.getCustomEffects()) {
			newEffects.add(new PotionEffect(effect.getType(), effect.getDuration(), effect.getAmplifier() + 1));
		}
		meta.clearCustomEffects();
		for(PotionEffect effect : newEffects) {
			meta.addCustomEffect(effect, true);
		}
		pot.setItemMeta(meta);
		return pot;
	}
	
	public ItemStack durationUp() {
		ItemStack pot = new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta) result.getItemMeta();
		List<PotionEffect> newEffects = new ArrayList<>();
		for(PotionEffect effect : meta.getCustomEffects()) {
			newEffects.add(new PotionEffect(effect.getType(), effect.getDuration() * 2, effect.getAmplifier()));
		}
		meta.clearCustomEffects();
		for(PotionEffect effect : newEffects) {
			meta.addCustomEffect(effect, true);
		}
		pot.setItemMeta(meta);
		return pot;
	}
	
	public ItemStack makeSplash() {
		ItemStack pot = new ItemStack(Material.SPLASH_POTION);
		PotionMeta meta = (PotionMeta) result.getItemMeta();
		List<PotionEffect> newEffects = new ArrayList<>();
		for(PotionEffect effect : meta.getCustomEffects()) {
			newEffects.add(new PotionEffect(effect.getType(), effect.getDuration() * 2, effect.getAmplifier()));
		}
		meta.clearCustomEffects();
		for(PotionEffect effect : newEffects) {
			meta.addCustomEffect(effect, true);
		}
		pot.setItemMeta(meta);
		return pot;
	}
	
	public ItemStack checkCommons(ItemStack ingredient, ItemStack currentItem) {
		ItemMeta itemMeta = currentItem.getItemMeta();
		if(!(itemMeta instanceof PotionMeta))
			return null;
		
		PotionMeta meta = (PotionMeta) itemMeta;
		if(!meta.hasCustomEffects())
			return null;
		
		if(!meta.getCustomEffects().containsAll(potionEffects))
			return null;
		
		Material ingredientType = ingredient.getType();
		if(ingredientType.equals(Material.GLOWSTONE_DUST)) {
			return ampUp();
		}
		else if(ingredientType.equals(Material.REDSTONE)) {
			return durationUp();
		}
		else if(ingredientType.equals(Material.GUNPOWDER) && !currentItem.getType().equals(Material.SPLASH_POTION)) {
			return makeSplash();
		}
		
		return null;
	}
	
	
	/**
	 * Get the BrewRecipe of the given recipe , will return null if no recipe is
	 * found
	 * 
	 * @param inventory The inventory
	 * @return The recipe
	 */
	@Nullable
	public static BrewingRecipe getRecipe(BrewerInventory inventory) {
		boolean notAllAir = false;
		for (int i = 0; i < 3 && !notAllAir; i++) {
			if (inventory.getItem(i) == null)
				continue;
			if (inventory.getItem(i).getType() == Material.AIR)
				continue;
			notAllAir = true;
		}
		if (!notAllAir)
			return null;

		for (BrewingRecipe recipe : recipes) {
			if (!recipe.isPerfect() && inventory.getIngredient().getType() == recipe.getIngredient().getType()) {
				return recipe;
			}
			if (recipe.isPerfect() && inventory.getIngredient().isSimilar(recipe.getIngredient())) {
				return recipe;
			}
		}
		return null;
	}

	public void startBrewing(BrewerInventory inventory) {
		new BrewClock(this, inventory);
	}

	private class BrewClock extends BukkitRunnable {
		private BrewerInventory inventory;
		private BrewingRecipe recipe;
		private ItemStack ingredient;
		private BrewingStand stand;
		private int time = 400; // Like I said the starting time is 400

		public BrewClock(BrewingRecipe recipe, BrewerInventory inventory) {
			this.recipe = recipe;
			this.inventory = inventory;
			this.ingredient = inventory.getIngredient();
			this.stand = inventory.getHolder();
			runTaskTimer(plugin, 0L, 1L);
		}

		@Override
		public void run() {
			if (time == 0) {
				inventory.setIngredient(new ItemStack(Material.AIR));
				for (int i = 0; i < 3; i++) {
					if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR)
						continue;
					recipe.getAction().brew(inventory, inventory.getItem(i), ingredient);
				}
				cancel();
				return;
			}
			if (inventory.getIngredient().isSimilar(ingredient)) {
				stand.setBrewingTime(400); // Reseting everything
				cancel();
				return;
			}
			// You should also add here a check to make sure that there are still items to
			// brew
			time--;
			stand.setBrewingTime(time);
		}
	}
}
