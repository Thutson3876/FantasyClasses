package me.thutson3876.fantasyclasses.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.classes.witch.WitchBrewRecipe;

public class WitchesBrewListener implements Listener {
	private static final FantasyClasses plugin = FantasyClasses.getPlugin();

	public WitchesBrewListener() {
		plugin.registerEvents(this);
	}
	
	@EventHandler
	public void onPotionSplashEvent(PotionSplashEvent e) {
		ItemStack potion = e.getPotion().getItem();
		for(WitchBrewRecipe recipe : WitchBrewRecipe.values()) {
			if(potion.isSimilar(recipe.getResult())) {
				recipe.runAction(e);
				return;
			}	
		}
	}
}
