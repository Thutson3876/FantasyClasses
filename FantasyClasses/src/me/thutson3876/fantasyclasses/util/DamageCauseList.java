package me.thutson3876.fantasyclasses.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public enum DamageCauseList {

	MAGICAL, PHYSICAL;
	
	private List<DamageCause> causeList = new ArrayList<>();
	
	static {
		generateLists();
	}
	
	private static void generateLists() {
		//Magical
		MAGICAL.causeList.add(DamageCause.DRAGON_BREATH);
		MAGICAL.causeList.add(DamageCause.ENTITY_EXPLOSION);
		MAGICAL.causeList.add(DamageCause.FIRE);
		MAGICAL.causeList.add(DamageCause.FIRE_TICK);
		MAGICAL.causeList.add(DamageCause.FREEZE);
		MAGICAL.causeList.add(DamageCause.HOT_FLOOR);
		MAGICAL.causeList.add(DamageCause.LAVA);
		MAGICAL.causeList.add(DamageCause.LIGHTNING);
		MAGICAL.causeList.add(DamageCause.MAGIC);
		MAGICAL.causeList.add(DamageCause.MELTING);
		MAGICAL.causeList.add(DamageCause.POISON);
		MAGICAL.causeList.add(DamageCause.THORNS);
		MAGICAL.causeList.add(DamageCause.VOID);
		MAGICAL.causeList.add(DamageCause.WITHER);
		MAGICAL.causeList.add(DamageCause.CUSTOM);
		//Physical
		PHYSICAL.causeList.add(DamageCause.BLOCK_EXPLOSION);
		PHYSICAL.causeList.add(DamageCause.CONTACT);
		PHYSICAL.causeList.add(DamageCause.CRAMMING);
		PHYSICAL.causeList.add(DamageCause.DROWNING);
		PHYSICAL.causeList.add(DamageCause.ENTITY_ATTACK);
		PHYSICAL.causeList.add(DamageCause.ENTITY_SWEEP_ATTACK);
		PHYSICAL.causeList.add(DamageCause.FALL);
		PHYSICAL.causeList.add(DamageCause.FALLING_BLOCK);
		PHYSICAL.causeList.add(DamageCause.FLY_INTO_WALL);
		PHYSICAL.causeList.add(DamageCause.PROJECTILE);
		PHYSICAL.causeList.add(DamageCause.STARVATION);
	}
	
	public List<DamageCause> getDamageCauseList(){
		return this.causeList;
	}
}
