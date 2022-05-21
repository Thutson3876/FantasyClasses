package me.thutson3876.fantasyclasses.classes.seaguardian;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.loot.LootContext;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class FishyBusiness extends AbstractAbility {
	
	public FishyBusiness(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30 * 20;
		this.displayName = "Fishy Business";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.FISHING_ROD);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof PlayerFishEvent))
			return false;

		if (isOnCooldown())
			return false;

		PlayerFishEvent e = (PlayerFishEvent) event;

		if (!e.getPlayer().equals(player))
			return false;

		Entity entity = e.getCaught();
		if (!(entity instanceof LivingEntity))
			return false;

		if(e.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY)) {
			Random rng = new Random();
			Location loc = player.getLocation();
			if (entity instanceof Player) {
				Player target = (Player) entity;
				PlayerInventory inv = target.getInventory();
				ItemStack[] contents = inv.getContents();
				ItemStack stolenItem = contents[rng.nextInt(contents.length)];
				if(stolenItem == null)
					return false;
				target.getInventory().remove(stolenItem);
				target.getWorld().dropItemNaturally(loc, stolenItem);
				return true;
			}
			else if (entity instanceof Mob) {
				Mob target = (Mob) entity;
				LootContext.Builder builder = new LootContext.Builder(loc);
				LootContext.Builder b = builder;
				b = b.killer(player);
				b = b.lootedEntity(entity);
				LootContext lc = b.build();
				Collection<ItemStack> loot = target.getLootTable().populateLoot(rng, lc);
				if(loot == null || loot.isEmpty())
					return false;
				
				for (ItemStack item : loot)
					target.getWorld().dropItemNaturally(loc, item);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getInstructions() {
		return "Fish an entity";
	}

	@Override
	public String getDescription() {
		return "Your prowess with a fishing rod allows you to fish items out of any entity's pockets";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
