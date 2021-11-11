package me.thutson3876.fantasyclasses.classes.highroller;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class TwentySides extends AbstractAbility {

	private final Random rng = new Random();
	private int dc = 19;

	public TwentySides(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Twenty Sides";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.EMERALD);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof BlockBreakEvent))
			return false;

		BlockBreakEvent e = (BlockBreakEvent) event;

		if (!e.getPlayer().equals(this.player))
			return false;

		int roll = rng.nextInt(20);
		if (roll >= dc) {
			Collection<ItemStack> items = e.getBlock().getDrops(player.getInventory().getItem(EquipmentSlot.HAND),
					player);
			items.addAll(items);
			e.setDropItems(false);
			e.setExpToDrop(e.getExpToDrop() * 2);
			for (ItemStack i : items) {
				player.getWorld().dropItemNaturally(e.getBlock().getLocation(), i);
			}

			Firework firework = (Firework) player.getWorld().spawnEntity(e.getBlock().getLocation(),
					EntityType.FIREWORK);
			FireworkMeta meta = firework.getFireworkMeta();

			Type type = Type.STAR;
			Color c = Color.GREEN;
			Color c2 = Color.TEAL;

			FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c).withFade(c2)
					.with(type).trail(true).build();

			meta.addEffect(effect);
			meta.setPower(1);
			firework.setFireworkMeta(meta);
			
			return true;
		} else if (roll == 0) {
			e.getBlock().getWorld().createExplosion(e.getBlock().getLocation(), 4.0f, false, true, player);
			return true;
		}

		return false;
	}

	@Override
	public String getInstructions() {
		return "Break a block";
	}

	@Override
	public String getDescription() {
		return "Roll a d20 whenever you break a block. On a roll above a &6" + this.dc
				+ "&r you get twice the drops. On a natural one it explodes instead";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		dc = 20 - currentLevel;
	}

}
