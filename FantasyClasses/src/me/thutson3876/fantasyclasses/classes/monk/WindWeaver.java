package me.thutson3876.fantasyclasses.classes.monk;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;

public class WindWeaver extends AbstractAbility {

	private double maxAngle = 0.6;
	private double distance = 5.0;
	private int jumpDistance = 5;
	private double velocity = 1.5;
	private double offset = 0.15;
	
	public WindWeaver(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 1 * 20;
		this.displayName = "Windweaver";
		this.skillPointCost = 1;
		this.maximumLevel = 1;
		
		this.createItemStack(Material.PHANTOM_MEMBRANE);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof PlayerInteractEvent))
			return false;
		
		PlayerInteractEvent e = (PlayerInteractEvent)event;
		
		if(!e.getPlayer().equals(this.player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))
			return false;
		
		if (!(player.getInventory().getItemInMainHand() == null
				|| player.getInventory().getItemInMainHand().getType().equals(Material.AIR)))
			return false;

		double damage = 3.0;
		List<Ability> abils = getFantasyPlayer().getAbilities();
		
		for(int i = 0; i < abils.size(); i++) {
			if(abils.get(i).getName().equalsIgnoreCase("open palm")) {
				damage = abils.get(i).getCurrentLevel() * 3.0;
			}
		}
		
		List<Entity> enemies = AbilityUtils.getEntitiesInAngle(player, maxAngle, distance, offset);
		
		for(Entity ent : enemies) {
			ent.setVelocity(ent.getVelocity().multiply(0.6).add(player.getEyeLocation().getDirection().multiply(velocity)));
			
			if (ent instanceof LivingEntity) {
				((LivingEntity) ent).damage(damage, player);
			}
		}
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, 0.7f, 1F);
		Block b = player.getTargetBlockExact(jumpDistance);
		
		if(b != null) {
			player.setVelocity(player.getEyeLocation().getDirection().multiply(-1.15));
			return true;
		}
		
		if(enemies.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public String getInstructions() {
		return "Punch the air or a block with your empty hand";
	}

	@Override
	public String getDescription() {
		return "Send out a blast of wind to shoot your foes away or launch yourself while not wearing armor";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
	}

}
