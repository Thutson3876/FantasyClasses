package me.thutson3876.fantasyclasses.classes.highroller;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;

public class TwentySides extends AbstractAbility {

	private final Random rng = new Random();
	private int dc = 19;

	public TwentySides(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 5 * 20;
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
			player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 30 * 20, 9));
			e.setExpToDrop(e.getExpToDrop() * 5);
			
			Location loc = e.getBlock().getLocation();
			Material blockType = e.getBlock().getType();
			Collection <ItemStack> drops = e.getBlock().getDrops(player.getInventory().getItemInMainHand(), player);
			if(drops != null && !drops.isEmpty()) {
				for(ItemStack i : drops) {
					if(!i.getType().equals(blockType)) {
						player.getWorld().dropItemNaturally(loc, i);
					}
				}
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
			e.getBlock().getWorld().createExplosion(e.getBlock().getLocation(), 3.5f, false, true);
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
				+ "&r you gain Haste X. On a natural one it explodes instead";
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
