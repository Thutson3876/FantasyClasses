package me.thutson3876.fantasyclasses.classes.witch;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class WitchWand extends AbstractAbility implements Bindable {

	private Material boundType = null;
	private double bulletVelocity = 2.0;
	private double damage = 6.0;

	public WitchWand(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 5 * 20;
		this.displayName = "Witch's Wand";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.SHULKER_BOX);
	}

	@Override
	public boolean trigger(Event event) {
		if (event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e = (PlayerInteractEvent) event;

			if (!e.getPlayer().equals(player))
				return false;

			if (e.getItem() == null || !e.getItem().getType().equals(this.boundType))
				return false;

			if (!e.getAction().equals(Action.LEFT_CLICK_AIR))
				return false;
			
			if(isOnCooldown())
				return false;

			launchProjectile();
			return true;
		} 
		else if (event instanceof ProjectileHitEvent) {
			ProjectileHitEvent e = (ProjectileHitEvent) event;

			if (!e.getEntity().getShooter().equals(player))
				return false;

			if (!e.getEntityType().equals(EntityType.SHULKER_BULLET))
				return false;

			Entity hit = e.getHitEntity();
			if (hit == null)
				return false;

			if (!(hit instanceof LivingEntity))
				return false;

			LivingEntity livingHit = (LivingEntity) hit;
			if(hit.isDead())
				return false;
			
			livingHit.damage(damage, player);
			livingHit.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, (int) Math.round(getMagickaBonus() * 50), 0));
		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "Left-click with your bound item";
	}

	@Override
	public String getDescription() {
		return "Launch a ball of energy from your wand that causes its target to levitate. It deals &6"
				+ damage * (1 + getMagickaBonus()) + " &rdamage and has a cooldown of &6" + this.coolDowninTicks / 20
				+ " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		damage = 4.0 + 2.0 * currentLevel * (1 + getMagickaBonus());
		this.coolDowninTicks = (6 - currentLevel) * 20;
	}

	@Override
	public Material getBoundType() {
		return this.boundType;
	}

	@Override
	public void setBoundType(Material type) {
		this.boundType = type;
	}

	private void launchProjectile() {
		Location spawnAt = player.getEyeLocation().toVector().add(player.getEyeLocation().getDirection())
				.toLocation(player.getWorld());

		ShulkerBullet bullet = (ShulkerBullet) player.getWorld().spawnEntity(spawnAt, EntityType.SHULKER_BULLET);
		bullet.setShooter(player);
		bullet.setVelocity(player.getEyeLocation().getDirection().multiply(bulletVelocity));
		LivingEntity target = AbilityUtils.getNearestLivingEntity(player.getLocation(), AbilityUtils.onlyLiving(AbilityUtils.getEntitiesInAngle(player, 0.8, 25, 0.2)));
		if (target != null)
			bullet.setTarget(target);

		//bullet.setVelocity(bullet.getVelocity().multiply(bulletVelocity));

		player.getWorld().playSound(spawnAt, Sound.ENTITY_SHULKER_SHOOT, 0.7f, 1.2F);
	}

	private double getMagickaBonus() {
		if (this.fplayer == null)
			return 0;

		return (fplayer.getMagicka() / 100);
	}
}
