package me.thutson3876.fantasyclasses.classes.alchemy;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.custombrewing.BrewingRecipe;
import me.thutson3876.fantasyclasses.custombrewing.CauldronBrewEvent;

public class EnhancedRepitoire extends AbstractAbility{
	
	public EnhancedRepitoire(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Enhanced Repitoire";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.BREWING_STAND);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerInteractEvent))
			return false;
		
		PlayerInteractEvent e = (PlayerInteractEvent)event;
		if(!e.getPlayer().equals(player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		Block block = e.getClickedBlock();
		if(block == null)
			return false;
		
		if(!block.getType().equals(Material.WATER_CAULDRON))
			return false;
		
		Levelled l = ((Levelled)block.getBlockData());
		if(l.getLevel() < l.getMaximumLevel())
			return false;
		
		block.setType(Material.CAULDRON);
		
		Collection<Entity> entities =  block.getWorld().getNearbyEntities(block.getBoundingBox());
		Collection<ItemStack> ingredients = new ArrayList<>();
		for(Entity ent : entities) {
			if(ent.getType().equals(EntityType.DROPPED_ITEM)) {
				Item i = (Item) ent;
				ingredients.add(i.getItemStack());
			}
		}
		ItemStack brew = BrewingRecipe.getDrop(ingredients);
		if(brew == null) {
			player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_HURT, 1.0f, 1.0f);
			return true;
		}
		
		CauldronBrewEvent cauldronEvent = new CauldronBrewEvent(player, block, ingredients, brew);
		Bukkit.getPluginManager().callEvent(cauldronEvent);
		
		PotentBrewing potent = null;
		for(Ability abil : plugin.getPlayerManager().getPlayer(player).getAbilities()) {
			if(abil instanceof PotentBrewing) {
				potent = (PotentBrewing)abil;
				break;
			}
		}
		
		if(potent != null)
			brew = potent.newTrigger(cauldronEvent);
		
		
		if(brew == null)
			return false;
		
		for(Entity ent : entities) {
			if(ent.getType().equals(EntityType.DROPPED_ITEM)) {
				((Item)ent).remove();
			}
		}
		
		player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1.0f, 1.0f);
		block.getWorld().spawnParticle(Particle.BUBBLE_COLUMN_UP, block.getLocation(), 4);
		block.getWorld().dropItemNaturally(block.getLocation(), brew);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Brew potions in a cauldron";
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

}
