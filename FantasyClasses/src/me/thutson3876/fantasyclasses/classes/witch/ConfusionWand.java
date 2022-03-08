package me.thutson3876.fantasyclasses.classes.witch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.Sphere;

public class ConfusionWand extends AbstractAbility implements Bindable {

	private Material boundType = null;
	private double radius = 6.0;
	private int duration = 10 * 20;
	private List<PotionEffect> effects = new ArrayList<>();

	public ConfusionWand(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30 * 20;
		this.displayName = "Confusion Wand";
		this.skillPointCost = 1;
		this.maximumLevel = 3;
		
		this.createItemStack(Material.ENDER_EYE);
	}

	@Override
	public boolean trigger(Event event) {
		if (event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e = (PlayerInteractEvent) event;

			if (!e.getPlayer().equals(player))
				return false;

			if (e.getItem() == null || !e.getItem().getType().equals(this.boundType))
				return false;

			if (!e.getAction().equals(Action.RIGHT_CLICK_AIR))
				return false;
			
			if(!player.isSneaking())
				return false;
			
			if(isOnCooldown())
				return false;
			
			Random rng = new Random();
			List<LivingEntity> entities = AbilityUtils.getNearbyLivingEntities(player, radius, radius, radius);
			for(LivingEntity ent : entities) {
					((LivingEntity)ent).addPotionEffects(effects);
					if(ent instanceof Creature)
						((Creature)ent).setTarget(entities.get(rng.nextInt(entities.size())));
			}
			
			
			World world = player.getWorld();
			world.playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0);
			for(Location loc : Sphere.generateSphere(player.getLocation(), (int) radius, true)) {
				world.spawnParticle(Particle.ENCHANTMENT_TABLE, loc, 1);
			}
			
			return true;
		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "Right-click with bound item type while crouching";
	}

	@Override
	public String getDescription() {
		return "Curse all creatures within &6" + radius + " &rmeters to have nausea and blindness for &6" + duration / 20
				+ " &6seconds. Causes mobs to attack a random nearby entity. At max level, also applies slowness";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		radius = 6.0 + 2.0 * currentLevel;
		duration = (6 + getMagickaBonus() + 4 * currentLevel) * 20;

		effects.clear();
		effects.add(new PotionEffect(PotionEffectType.CONFUSION, duration, 0));
		effects.add(new PotionEffect(PotionEffectType.BLINDNESS, duration, 0));
		if(currentLevel >= this.maximumLevel)
			effects.add(new PotionEffect(PotionEffectType.SLOW, duration, 0));
	}

	@Override
	public Material getBoundType() {
		return boundType;
	}

	@Override
	public void setBoundType(Material type) {
		boundType = type;
	}

	private int getMagickaBonus() {
		if(fplayer == null)
			return 0;
		
		return fplayer.getScalableValue("magicka") / 75;
	}
}
