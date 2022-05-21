package me.thutson3876.fantasyclasses.classes.combat;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public class ReturningArrows extends AbstractAbility implements Bindable {

	private int maxArrowCount = 3;
	private Queue<Arrow> shotArrows;
	private Material boundType = null;
	
	public ReturningArrows(Player p) {
		super(p);
		shotArrows = new LinkedList<>();
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 1 * 20;
		this.displayName = "Returning Arrows";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.ARROW);
	}

	@Override
	public boolean trigger(Event event) {
		if(event instanceof EntityShootBowEvent) {
			if(shotArrows.size() >= maxArrowCount)
				return false;
			
			EntityShootBowEvent e = (EntityShootBowEvent)event;
			
			if(!e.getEntity().equals(player))
				return false;
			
			if(!(e.getProjectile() instanceof Arrow))
				return false;
			
			shotArrows.add((Arrow) e.getProjectile());
			player.sendMessage(ChatUtils.chat("Stored arrow count: &6" + shotArrows.size()));
		}
		
		
		if(!(event instanceof PlayerInteractEvent))
			return false;
		
		PlayerInteractEvent e = (PlayerInteractEvent)event;
		
		if(!e.getPlayer().equals(player))
			return false;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))
			return false;
		
		ItemStack item = e.getItem();
		if(item == null)
			return false;
		
		if(!item.getType().equals(boundType))
			return false;
		
		boolean worked = false;
		for(Arrow arr : shotArrows) {
			if(arr == null || arr.isDead()) {
				//player.sendMessage(ChatUtils.chat("&4Arrow was null"));
				continue;
			}
				
			
			if(arr.isInBlock()) {
				Arrow newArrow = arr.getWorld().spawnArrow(arr.getLocation(), player.getLocation().toVector().subtract(arr.getLocation().toVector()).normalize(), 2.0f, 6);
				newArrow.setPierceLevel(arr.getPierceLevel());
				newArrow.setCritical(arr.isCritical());
				newArrow.setFireTicks(arr.getFireTicks());
				newArrow.setShooter(arr.getShooter());
				newArrow.setKnockbackStrength(arr.getKnockbackStrength());
				newArrow.setDamage(arr.getDamage());
				newArrow.setPickupStatus(PickupStatus.CREATIVE_ONLY);
				arr.remove();
				worked = true;
				//player.sendMessage(ChatUtils.chat("&6Arrow pulled from wall"));
			}
			else {
				arr.setVelocity(player.getLocation().toVector().subtract(arr.getLocation().toVector()).normalize().multiply(1.5));
				worked = true;
				//player.sendMessage(ChatUtils.chat("&3Arrow from air"));
			}
		}
		shotArrows.clear();
		
		return worked;
	}

	@Override
	public String getInstructions() {
		return "Left-click with bound item type";
	}

	@Override
	public String getDescription() {
		return "Recall &6" + maxArrowCount + " &rarrows that you have shot back to your position.";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		maxArrowCount = 3 * currentLevel;
	}

	@Override
	public Material getBoundType() {
		return boundType;
	}

	@Override
	public void setBoundType(Material type) {
		this.boundType = type;
	}

}
