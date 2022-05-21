package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class Cripple extends AbstractAbility {

	private int durationInTicks = 5 * 20;
	private PotionEffect blindness;
	private PotionEffect slowness;
	
	public Cripple(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 10 * 20;
		this.displayName = "Cripple";
		this.skillPointCost = 1;
		this.maximumLevel = 2;
		this.blindness = new PotionEffect(PotionEffectType.BLINDNESS, durationInTicks, 0);
		this.slowness = new PotionEffect(PotionEffectType.SLOW, durationInTicks, 0);
		
		this.createItemStack(Material.BONE);
	}

	@Override
	public boolean trigger(Event event) {
		if(!(event instanceof EntityDamageByEntityEvent)) {
			return false;
		}
		
		if(this.isOnCooldown())
			return false;
		
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
		
		if(!e.getDamager().equals(this.player))
			return false;

		if(!(e.getEntity() instanceof LivingEntity))
			return false;
		
		if(!e.getCause().equals(DamageCause.ENTITY_ATTACK))
			return false;
		
		if(!(MaterialLists.HOE.getMaterials().contains(player.getInventory().getItemInMainHand().getType())))
			return false;
		
		if(e.getFinalDamage() < 1.0)
			return false;
		
		LivingEntity target = (LivingEntity) e.getEntity();
		
		target.addPotionEffect(blindness);
		if(currentLevel > 1)
			target.addPotionEffect(slowness);
		
		return true;
	}

	@Override
	public String getInstructions() {
		return "Attack an entity with a scythe";
	}

	@Override
	public String getDescription() {
		return "Applies blindness for " + this.durationInTicks / 20 + " seconds. Also applies slowness at level 2";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		if(currentLevel == 1) {
			blindness = new PotionEffect(PotionEffectType.BLINDNESS, durationInTicks, 0);
		}
		else if(currentLevel == 2) {
			slowness = new PotionEffect(PotionEffectType.SLOW, durationInTicks, 0);
		}
	}

}
