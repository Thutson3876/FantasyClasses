package me.thutson3876.fantasyclasses.custommobs;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class FailedExperiment extends AbstractCustomMob {

	public FailedExperiment(Location loc) {
		super(loc);
		
		((Zombie)ent).setBaby();
	}

	@Override
	protected void targeted(EntityTargetEvent e) {
		if(e.getTarget() == null)
			return;
		
		if(!e.getEntity().equals(ent))
			return;
		
		//Not spawning with TNT on head
		TNTPrimed tnt = (TNTPrimed) ent.getWorld().spawnEntity(ent.getEyeLocation(), EntityType.PRIMED_TNT);
		tnt.setTicksLived(5);
		tnt.setFuseTicks(4 * 20);
		tnt.setSource(ent);
		ent.addPassenger(tnt);
	}
	
	@Override
	protected void applyDefaults() {
		this.setMaxHealth(10);
		this.setAttackDamage(6);
		this.setMoveSpeed(0.3f);
		this.setSkillExpReward(1);
	}

	@Override
	protected void healed(EntityRegainHealthEvent e) {
		
	}

	@Override
	protected void tookDamage(EntityDamageByEntityEvent e) {
		
	}

	@Override
	protected void dealtDamage(EntityDamageByEntityEvent e) {
		
	}

	@Override
	protected void died(EntityDeathEvent e) {
		
	}

	@Override
	public String getMetadataTag() {
		return "failed_experiment";
	}

	@Override
	protected void tookDamage(EntityDamageEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected EntityType getEntityType() {
		return EntityType.ZOMBIE;
	}
	
}
