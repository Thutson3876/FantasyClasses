package me.thutson3876.fantasyclasses.classes.witch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class WitchesCauldron extends AbstractAbility {

	public WitchesCauldron(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Witch's Cauldron";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.CAULDRON);
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
		
		if(!player.getInventory().getItem(e.getHand()).getType().equals(Material.STICK))
			return false;
		
		Block block = e.getClickedBlock();
		if(block == null)
			return false;
		
		if(!block.getType().equals(Material.WATER_CAULDRON))
			return false;
		
		Collection<Entity> entities =  block.getWorld().getNearbyEntities(block.getBoundingBox());
		Collection<Material> mats = new ArrayList<>();
		for(Entity ent : entities) {
			if(ent.getType().equals(EntityType.DROPPED_ITEM)) {
				Item i = (Item) ent;
				mats.add(i.getItemStack().getType());
				i.remove();
			}
		}
		ItemStack brew = AbilityUtils.getWitchesBrew();
		ItemMeta meta = brew.getItemMeta();

		List<String> lore = meta.getLore();
		if(lore == null)
			lore = new ArrayList<>();
		
		for (Material mat : mats) {
			lore.add(mat.name().toLowerCase());
		}
		brew.setItemMeta(meta);
		block.getWorld().dropItemNaturally(block.getLocation(), brew);
		
		boolean isPerfect = false;
		for(WitchBrewRecipe recipe : WitchBrewRecipe.values()) {
			if(recipe.getResult().isSimilar(brew)) {
				player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 1.0f, 1.0f);
				isPerfect = true;
				break;
			}
		}
		
		if(!isPerfect)
			player.playSound(player.getLocation(), Sound.ENTITY_WITCH_HURT, 1.0f, 1.0f);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Toss ingredients into a filled cauldron and right-click on it with a stick";
	}

	@Override
	public String getDescription() {
		return "You can brew a special kind of splash potion that has varying effects depending on the ingredients you put in";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

	
}
