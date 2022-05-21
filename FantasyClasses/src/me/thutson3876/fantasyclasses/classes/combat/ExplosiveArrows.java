package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class ExplosiveArrows extends AbstractAbility {

	private float power = 1.75F;

	private boolean isOn = false;

	private BossBar bar;

	private Projectile projectile = null;

	public ExplosiveArrows(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 8 * 20;
		this.displayName = "Explosive Arrows";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.TNT);
	}

	@Override
	public boolean trigger(Event event) {
		if (event instanceof PlayerToggleSneakEvent) {

			PlayerToggleSneakEvent e = (PlayerToggleSneakEvent) event;

			if (!e.getPlayer().equals(player))
				return false;

			if (player.isSneaking())
				return false;

			if (isOnCooldown())
				return false;
			if (this.isOn) {
				this.isOn = false;
				this.bar.setVisible(false);
			} else {
				this.isOn = true;
				this.bar.setVisible(true);
			}
			return false;
		} else if (event instanceof EntityShootBowEvent) {
			EntityShootBowEvent e = (EntityShootBowEvent) event;

			if (!e.getEntity().equals(player))
				return false;

			if (isOnCooldown())
				return false;
			if (!this.isOn)
				return false;
			this.isOn = false;
			this.bar.setVisible(false);
			this.projectile = (Projectile) e.getProjectile();
			return true;
		} else if (event instanceof ProjectileHitEvent) {
			if (this.projectile == null)
				return false;
			ProjectileHitEvent e = (ProjectileHitEvent) event;
			Projectile eventProjectile = e.getEntity();
			if (!this.projectile.equals(eventProjectile))
				return false;
			eventProjectile.getWorld().createExplosion(eventProjectile.getLocation(), this.power, false, false, player);
			return false;
		}

		return false;
	}

	@Override
	public String getInstructions() {
		return "Crouch to toggle explosive arrows";
	}

	@Override
	public String getDescription() {
		return "Fire an explosive arrow that explodes with a radius of &6" + AbilityUtils.doubleRoundToXDecimals(power, 1) + " &rblock(s)";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		this.bar = Bukkit.createBossBar(getName(), BarColor.WHITE, BarStyle.SEGMENTED_20,
				new org.bukkit.boss.BarFlag[0]);
		this.bar.addPlayer(player);
		this.bar.setVisible(false);
		
		power = 1.0f + (0.75f * currentLevel);
	}

}
