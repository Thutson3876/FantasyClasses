package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Bindable;

public class BirdSinger extends AbstractAbility implements Bindable {
	
	private Material type = null;
	private int strikeAmt = 1;
	private double range = 10.0;
	
	public BirdSinger(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 10 * 20;
		this.displayName = "Bird Singer";
		this.skillPointCost = 1;
		this.maximumLevel = 3;

		this.createItemStack(Material.WHEAT_SEEDS);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent))
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(isOnCooldown())
			return false;
		
		if(!e.getDamager().equals(player))
			return false;
		
		if (!(player.getInventory().getItemInOffHand().getType().equals(type) || player.getInventory().getItemInMainHand().getType().equals(type)))
				return false;
		
		int count = 0;
		for(Entity ent : player.getNearbyEntities(range, range, range)){
			if(ent instanceof Parrot) {
				if(player.equals(((Parrot)ent).getOwner())) {
					ent.getWorld().playSound(ent.getLocation(), Sound.ENTITY_PARROT_IMITATE_EVOKER, 1.2f, 1.3f);
					count++;
				}
			}
		}
		
		if(count == 0)
			return false;
		Entity target = e.getEntity();
		World world = target.getWorld();
		for(int i = 0; i < count * strikeAmt; i++) {
			world.strikeLightning(target.getLocation());
		}
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Attack an entity with tamed parrots near you";
	}

	@Override
	public String getDescription() {
		return "When you strike an entity while holding the bound item type, your parrots unleash a torrent of &6" + strikeAmt + " &rlightning bolts each. This ability has a cooldown of &6" + this.coolDowninTicks / 20 + " &rseconds";
	}

	@Override
	public boolean getDealsDamage() {
		return true;
	}

	@Override
	public void applyLevelModifiers() {
		this.coolDowninTicks = (12 - 2 * currentLevel) * 20;
		strikeAmt = currentLevel;
	}

	@Override
	public Material getBoundType() {
		return type;
	}

	@Override
	public void setBoundType(Material type) {
		this.type = type;
	}

}
