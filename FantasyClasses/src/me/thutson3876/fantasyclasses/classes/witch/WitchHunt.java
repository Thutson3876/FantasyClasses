package me.thutson3876.fantasyclasses.classes.witch;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Scalable;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class WitchHunt extends AbstractAbility implements Scalable {

	private Random rng = new Random();
	private int magicka = 100;

	public WitchHunt(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Witch Hunt";
		this.skillPointCost = 2;
		this.maximumLevel = 1;

		this.createItemStack(Material.IRON_AXE);
	}

	@Override
	public boolean trigger(Event event) {
		if(!isEnabled())
			return false;
		
		if (!(event instanceof LootGenerateEvent))
			return false;

		LootGenerateEvent e = (LootGenerateEvent) event;

		LootContext context = e.getLootContext();
		if (!context.getKiller().equals(player))
			return false;

		if (!e.getEntity().getType().equals(EntityType.WITCH))
			return false;

		LootTable lootTable = e.getLootTable();
		Collection<ItemStack> drops = lootTable.populateLoot(rng, context);

		drops.addAll(lootTable.populateLoot(rng, context));
		drops.addAll(lootTable.populateLoot(rng, context));
		int chance = rng.nextInt(100);
		if (chance > 89) {
			int i = rng.nextInt(3);
			switch (i) {
			case 0:
				drops.add(AbilityUtils.getBloodVial());
			case 1:
				drops.addAll(drops);
			case 2:
				drops.add(AbilityUtils.generateRandomWitchesBrew());
			}
		}

		magicka += 5;
		e.setLoot(drops);

		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 0.7f, 1.2f);

		return true;
	}

	@Override
	public String getInstructions() {
		return "Kill a witch";
	}

	@Override
	public String getDescription() {
		return "Kill other witches to absorb their power and attain their possessions. Your current magicka level: &6" + magicka;
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

	@Override
	public int getScalableValue() {
		return magicka;
	}

	@Override
	public void setScalableValue(int amt) {
		magicka = amt;
	}

	@Override
	public String getScalableValueName() {
		return "magicka";
	}

}
