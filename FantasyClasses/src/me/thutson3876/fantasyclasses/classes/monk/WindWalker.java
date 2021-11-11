package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class WindWalker extends AbstractAbility {

	private double velocityMod = 2.0;
	
	public WindWalker(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 3 * 20;
		this.displayName = "Windwalker";
		this.skillPointCost = 1;
		this.maximumLevel = 1;
		
		this.createItemStack(Material.IRON_BOOTS);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerToggleSneakEvent))
			return false;

		PlayerToggleSneakEvent e = (PlayerToggleSneakEvent)event;
		
		if(!e.getPlayer().equals(this.player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		if(AbilityUtils.getHeightAboveGround(player) < 0.3)
			return false;
		
		if(AbilityUtils.hasArmor(player))
			return false;
		
		Vector dash = player.getEyeLocation().getDirection().multiply(velocityMod);
		dash.setY(0.5);
		player.setVelocity(dash);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 0.5f, 1F);
		return true;
	}

	@Override
	public String getInstructions() {
		return "Crouch mid-air while not wearing armor";
	}

	@Override
	public String getDescription() {
		return "Perform a horizontal dash mid-air in the direction you are facing. All &6Windwalker sub-skills &rrequire you to not wear armor";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
