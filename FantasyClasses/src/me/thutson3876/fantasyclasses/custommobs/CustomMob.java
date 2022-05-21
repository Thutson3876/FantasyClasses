package me.thutson3876.fantasyclasses.custommobs;

import org.bukkit.entity.LivingEntity;

import org.bukkit.entity.EntityType;

import me.thutson3876.fantasyclasses.custommobs.boss.TargetDummy;
import me.thutson3876.fantasyclasses.custommobs.boss.voidremnant.VoidRemnant;

public enum CustomMob {

	LOST_GUARDIAN(EntityType.GUARDIAN, LostGuardian.class), PARASITE(EntityType.SILVERFISH, Parasite.class),
	UNDEAD_MINER(EntityType.WITHER_SKELETON, UndeadMiner.class), TARGET_DUMMY(EntityType.SLIME, TargetDummy.class),
	VOID_REMNANT(EntityType.WITHER, VoidRemnant.class);

	private EntityType type = null;
	private Class<? extends AbstractCustomMob> clazz;

	private CustomMob(EntityType type, Class<? extends AbstractCustomMob> clazz) {
		this.type = type;
		this.clazz = clazz;
	}
	
	public String getMetadataTag() {
		return this.name().toLowerCase();
	}
	
	public EntityType getEntityType() {
		return type;
	}
	
	public AbstractCustomMob newMob(LivingEntity ent) {
		try {
			return clazz.getConstructor(LivingEntity.class).newInstance(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getMetadataTag(EntityType type) {
		for(CustomMob mob : CustomMob.values()) {
			if(mob.getEntityType().equals(type))
				return mob.getMetadataTag();
		}
		
		return "";
	}
}
