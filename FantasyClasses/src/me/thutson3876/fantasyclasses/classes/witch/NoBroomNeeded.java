package me.thutson3876.fantasyclasses.classes.witch;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class NoBroomNeeded extends AbstractAbility {

	private boolean isGliding = false;

	public NoBroomNeeded(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 60 * 20;
		this.displayName = "No Broom Needed";
		this.skillPointCost = 2;
		this.maximumLevel = 3;

		this.createItemStack(Material.ELYTRA);
	}

	@Override
	public boolean trigger(Event event) {
		if (event instanceof PlayerToggleSneakEvent) {
			PlayerToggleSneakEvent e = (PlayerToggleSneakEvent) event;

			if (!e.getPlayer().equals(player))
				return false;

			if (isOnCooldown())
				return false;

			if (AbilityUtils.getHeightAboveGround(player) < 0.3)
				return false;

			 if(!e.isSneaking())
				 return false;

			e.setCancelled(true);

			player.setVelocity(player.getVelocity().add(new Vector(0, 2, 0)));

			isGliding = true;
			player.setGliding(true);
			
			return true;
		} else if (event instanceof PlayerMoveEvent) {
			PlayerMoveEvent e = (PlayerMoveEvent) event;

			if (!e.getPlayer().equals(player))
				return false;

			if (!isGliding)
				return false;

			if (AbilityUtils.getHeightAboveGround(player) < 0.3) {
				isGliding = false;
				player.setGliding(false);
			}

		} else if (event instanceof EntityToggleGlideEvent) {
			EntityToggleGlideEvent e = (EntityToggleGlideEvent) event;

			if (!e.getEntity().equals(player))
				return false;

			if (e.isGliding() && !isGliding) {
				e.setCancelled(false);
			} 
			else {
				e.setCancelled(true);
			}
				
		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "Crouch while mid-air";
	}

	@Override
	public String getDescription() {
		return "Launch yourself high into the air and glide to your destination safely. Without the need of a broom. Has a cooldown of &6 "
				+ this.coolDowninTicks / 20 + " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		this.coolDowninTicks = (90 - 15 * this.currentLevel) * 20;
	}

}
