package me.thutson3876.fantasyclasses.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.util.Vector;

public class AbilityUtils {

	private static final ItemStack BLOOD_VIAL;
	private static final ItemStack WITCHES_BREW;

	static {
		ItemStack bloodTemp = new ItemStack(Material.POTION);
		PotionMeta bloodMeta = (PotionMeta) bloodTemp.getItemMeta();
		bloodMeta.setColor(Color.RED);
		bloodMeta.setDisplayName(ChatUtils.chat("&4Blood Vial"));

		BLOOD_VIAL = bloodTemp;

		ItemStack witchTemp = new ItemStack(Material.SPLASH_POTION);
		PotionMeta witchMeta = (PotionMeta) witchTemp.getItemMeta();
		witchMeta.setColor(Color.PURPLE);
		witchMeta.setDisplayName(ChatUtils.chat("&6Witch's Brew"));
		List<String> lore = new ArrayList<>();
		lore.add("Ingredients: ");

		WITCHES_BREW = witchTemp;
	}

	public static LivingEntity getLivingTarget(LivingEntity ent, double range) {
		for (Entity e : getNearbyLivingEntities(ent, range, range, range)) {
			if (e.getLocation().distance(ent.getEyeLocation()) < 0.1) {
				return (LivingEntity) e;
			}
		}

		return null;
	}
	
	public static List<LivingEntity> onlyLiving(List<Entity> entities){
		List<LivingEntity> result = new ArrayList<>();
		for(Entity ent : entities) {
			if(ent instanceof LivingEntity)
				result.add((LivingEntity)ent);
		}
		
		return result;
	}

	public static List<LivingEntity> getNearbyLivingEntities(Entity ent, double x, double y, double z) {
		List<LivingEntity> livingEntities = new ArrayList<>();
		for (Entity e : ent.getNearbyEntities(x, y, z)) {
			if (e instanceof LivingEntity)
				livingEntities.add((LivingEntity) e);
		}

		return livingEntities;
	}
	
	public static LivingEntity getNearestLivingEntity(Location loc, List<LivingEntity> entities) {
		if(entities.isEmpty())
			return null;
		
		LivingEntity nearest = entities.get(0);
		double distance = 99999;
		for(LivingEntity ent : entities) {
			double temp = ent.getLocation().distance(loc);
			if(temp > distance) {
				distance = temp;
				nearest = ent;
			}
		}
		
		return nearest;
	}

	public static void randomSpreadGeneration(Location start, Material fillType, int spreadChance, int decreasePerTick,
			boolean replaceAll) {
		Block b = start.getBlock();
		b.setType(fillType);
		Random rng = new Random();
		int i = 1;
		int roll = rng.nextInt(100) + 10;
		while (roll < (spreadChance / i) + 10) {
			spreadChance -= decreasePerTick;
			Block next = null;
			do {
				next = b.getRelative(BlockFace.values()[rng.nextInt(BlockFace.values().length)]);
			} while (!replaceAll && !next.isPassable());

			randomSpreadGeneration(next.getLocation(), fillType, spreadChance, decreasePerTick, replaceAll);
			i++;
		}
		return;
	}

	public static void heal(LivingEntity e, double amt) {
		double maxhp = e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		double newhp = e.getHealth() + amt;
		if (newhp > maxhp)
			newhp = maxhp;

		e.setHealth(newhp);
		e.getWorld().spawnParticle(Particle.COMPOSTER, e.getLocation(), 6);
		e.getWorld().playSound(e.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.0f, 1.0f);
	}

	public static void setMaxHealth(Entity e, double amt, Operation op) {
		if (!(e instanceof LivingEntity))
			return;

		LivingEntity ent = (LivingEntity) e;
		AttributeModifier mod = new AttributeModifier("maxhealth", amt, op);
		ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(mod);
	}

	public static void setMaxHealth(LivingEntity e, AttributeModifier mod) {
		if (e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers().contains(mod))
			return;

		e.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(mod);
	}

	public static void setAttackDamage(LivingEntity e, AttributeModifier mod) {
		if (e.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getModifiers().contains(mod))
			return;

		e.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(mod);
	}

	public static void setAttackDamage(Entity e, double amt, Operation op) {
		if (!(e instanceof LivingEntity))
			return;

		LivingEntity ent = (LivingEntity) e;
		AttributeModifier mod = new AttributeModifier("atkdamage", amt, op);
		ent.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(mod);
	}

	// Measures the distance between player and all nearby entities and selects the
	// closest (ignoring y distance)
	public static Entity closestEntity(Player p, Entity[] nearbyEntities, double minHeight) {
		Entity closest = null;
		double distanceToPlayer = 1000; // Specifically the distance from the player without taking the y value into
										// account (only differences between X and Z)

		for (Entity e : nearbyEntities) {
			if (e == null) {
				return null;
			}
			Location entityLoc = e.getLocation();
			Location playerLoc = p.getLocation();

			if (playerLoc.getY() - entityLoc.getY() > minHeight) {
				double xDiffSqrd = (playerLoc.getX() - entityLoc.getX()) * (playerLoc.getX() - entityLoc.getX());
				double zDiffSqrd = (playerLoc.getZ() - entityLoc.getZ()) * (playerLoc.getZ() - entityLoc.getZ());

				// distance formula
				if (distanceToPlayer > Math.sqrt(xDiffSqrd + zDiffSqrd)) {
					closest = e;
				}
			}
		}

		return closest;
	}

	public static List<Material> getCropMaterials() {
		List<Material> materials = new LinkedList<>();
		materials.add(Material.POTATOES);
		materials.add(Material.CARROTS);
		materials.add(Material.WHEAT_SEEDS);
		materials.add(Material.BEETROOTS);
		materials.add(Material.COCOA_BEANS);
		materials.add(Material.MELON_STEM);
		materials.add(Material.ATTACHED_MELON_STEM);
		materials.add(Material.PUMPKIN_STEM);
		materials.add(Material.ATTACHED_PUMPKIN_STEM);
		return materials;
	}

	public static List<Location> generateRectangle(Location center, double xLength, double zLength) {
		List<Location> locations = new LinkedList<>();
		double tempX = -(xLength / 2);
		double tempZ = -(zLength / 2);
		for (int i = 0; i < xLength; i++) {
			tempX += i;

			Location temp = new Location(center.getWorld(), tempX, center.getY(), center.getZ());
			locations.add(temp);
			temp = new Location(center.getWorld(), tempX, center.getY(), center.getZ() + tempZ);
			locations.add(temp);
		}
		for (int i = 0; i < xLength; i++) {
			tempZ += i;

			Location temp = new Location(center.getWorld(), center.getX() - tempX, center.getY(), tempZ);
			if (!locations.contains(temp)) {
				locations.add(temp);
			}
			temp = new Location(center.getWorld(), center.getX(), center.getY(), tempZ);
			if (!locations.contains(temp)) {
				locations.add(temp);
			}
		}

		return locations;
	}

	public static Entity closestEntityFromList(List<Entity> list, Entity entity) {
		if (list == null) {
			return null;
		}
		if (list.isEmpty()) {
			return null;
		}

		Entity closest = null;
		double distance = 10000;
		double tempDistance = 1000;
		for (Entity e : list) {
			tempDistance = e.getLocation().distance(entity.getLocation());
			if (tempDistance < distance) {
				distance = tempDistance;
				closest = e;
			}
		}

		return closest;
	}

	public static List<Entity> getEntitiesInAngle(Player p, double maxAngle, double maxDistance) {
		Vector dirToDestination;
		Vector playerDirection;
		double angle;
		List<Entity> enemies = p.getNearbyEntities(maxDistance, maxDistance, maxDistance);
		List<Entity> targets = new LinkedList<>();

		playerDirection = p.getEyeLocation().getDirection();

		for (Entity e : enemies) {
			if (p.hasLineOfSight(e)) {
				dirToDestination = e.getLocation().toVector().subtract(p.getEyeLocation().toVector());
				angle = dirToDestination.angle(playerDirection);

				if (angle < maxAngle && angle > -maxAngle) {
					targets.add(e);
				}
			}
		}

		return targets;
	}
	
	public static Entity getEntityClosestToCursor(Player p, double maxAngle, double maxDistance, double offset) {
		Vector dirToDestination;
		Vector playerDirection;
		double angle;
		List<Entity> enemies = p.getNearbyEntities(maxDistance, maxDistance, maxDistance);
		Entity target = null;
		double targetAngle = 9999;

		playerDirection = p.getEyeLocation().getDirection();
		playerDirection.setX(playerDirection.getX() - offset);
		playerDirection.setZ(playerDirection.getZ() - offset);
		playerDirection.setY(playerDirection.getY() - offset);

		for (Entity e : enemies) {
			if (p.hasLineOfSight(e)) {
				dirToDestination = e.getLocation().toVector().subtract(p.getEyeLocation().toVector());
				angle = dirToDestination.angle(playerDirection);

				if (angle < maxAngle && angle > -maxAngle) {
					if(Math.abs(angle) > Math.abs(targetAngle)) {
						target = e;
					}
				}
			}
		}

		return target;
	}
	
	public static LivingEntity getLivingEntityClosestToCursor(Player p, double maxAngle, double maxDistance, double offset) {
		Vector dirToDestination;
		Vector playerDirection;
		double angle;
		List<LivingEntity> enemies = getNearbyLivingEntities(p, maxDistance, maxDistance, maxDistance);
		LivingEntity target = null;
		double targetAngle = 9999;

		playerDirection = p.getEyeLocation().getDirection();
		playerDirection.setX(playerDirection.getX() - offset);
		playerDirection.setZ(playerDirection.getZ() - offset);
		playerDirection.setY(playerDirection.getY() - offset);

		for (LivingEntity e : enemies) {
			if (p.hasLineOfSight(e)) {
				dirToDestination = e.getLocation().toVector().subtract(p.getEyeLocation().toVector());
				angle = dirToDestination.angle(playerDirection);

				if (angle < maxAngle && angle > -maxAngle) {
					if(Math.abs(angle) > Math.abs(targetAngle)) {
						target = e;
					}
				}
			}
		}

		return target;
	}

	public static List<Entity> getEntitiesInAngle(Player p, double maxAngle, double maxDistance, double offset) {
		Vector dirToDestination;
		Vector playerDirection;
		double angle;
		List<Entity> enemies = p.getNearbyEntities(maxDistance, maxDistance, maxDistance);
		List<Entity> targets = new LinkedList<>();

		playerDirection = p.getEyeLocation().getDirection();
		playerDirection.setX(playerDirection.getX() - offset);
		playerDirection.setZ(playerDirection.getZ() - offset);

		for (Entity e : enemies) {
			if (p.hasLineOfSight(e)) {
				dirToDestination = e.getLocation().toVector().subtract(p.getEyeLocation().toVector());
				angle = dirToDestination.angle(playerDirection);

				if (angle < maxAngle && angle > -maxAngle) {
					targets.add(e);
				}
			}
		}

		return targets;
	}

	// Filters any non-living entities out of an array of entities
	public static Entity[] onlyLiving(Entity[] entities) {
		ArrayList<Entity> entitiesAsList = new ArrayList<>();
		for (Entity e : entities) {
			if (e instanceof LivingEntity) {
				entitiesAsList.add(e);
			}
		}

		return entitiesAsList.toArray(new Entity[1]);
	}

	public static void moveToward(Entity entity, Location to, double speed) {
		Location loc = entity.getLocation();
		double x = loc.getX() - to.getX();
		double y = loc.getY() - to.getY();
		double z = loc.getZ() - to.getZ();
		Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
		entity.setVelocity(velocity);
	}

	public static void moveToward(Entity entity, Location to, double speed, double yMod) {
		Location loc = entity.getLocation();
		double x = loc.getX() - to.getX();
		double y = (loc.getY() - to.getY()) * yMod;
		double z = loc.getZ() - to.getZ();
		Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
		entity.setVelocity(velocity);
	}

	public static void moveTowardPlusY(Entity entity, Location to, double speed, double bonusY) {
		Location loc = entity.getLocation();
		double x = loc.getX() - to.getX();
		double y = loc.getY() - to.getY() + bonusY;
		double z = loc.getZ() - to.getZ();
		Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
		entity.setVelocity(velocity);
	}

	public static Block[] getBlocksAroundPlayer(Player p, int radius) {
		ArrayList<Block> blockList = new ArrayList<>();
		Location playerLoc = p.getLocation();
		Block temp = null;

		for (Location loc : Sphere.generateSphere(playerLoc, radius, false)) {
			temp = loc.getBlock();
			blockList.add(temp);
		}

		return blockList.toArray(new Block[1]);
	}

	public static double getHeightAboveGround(Entity e) {
		if (e.isOnGround()) {
			return 0;
		}

		Location eLoc = e.getLocation();
		Block currentBlock = eLoc.getBlock();
		Block nextBlock;

		for (int i = 0; i < 2048; i++) {
			nextBlock = currentBlock.getRelative(BlockFace.DOWN);
			if (!nextBlock.isPassable()) {
				return eLoc.distance(currentBlock.getLocation());
			}
			currentBlock = nextBlock;
		}

		return -999;
	}

	public static boolean isCritical(Player p) {
		return (p.getVelocity().getY() + 0.0784000015258789) <= 0;
	}

	public static boolean hasArmor(Player p) {
		ItemStack[] armorContents = p.getInventory().getArmorContents();
		// System.out.println(p.getDisplayName() + " armor contents: " +
		// armorContents.toString());
		for (int i = 0; i < armorContents.length; i++) {
			if (armorContents[i] != null) {
				if (MaterialLists.ARMOR.getMaterials().contains(armorContents[i].getType()))
					return true;
			}
		}
		return false;
	}

	public static boolean isBloodVial(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if (!item.getType().equals(Material.POTION))
			return false;

		if (!(meta instanceof PotionMeta))
			return false;

		PotionMeta pm = (PotionMeta) meta;
		PotionMeta bloodMeta = (PotionMeta) BLOOD_VIAL.getItemMeta();

		if (!pm.getColor().equals(bloodMeta.getColor()))
			return false;

		if (!pm.getDisplayName().equalsIgnoreCase(bloodMeta.getDisplayName()))
			return false;

		return true;
	}

	// write this
	public static ItemStack generateRandomWitchesBrew() {
		return null;
	}

	public static ItemStack getBloodVial() {
		return BLOOD_VIAL;
	}

	public static ItemStack getWitchesBrew() {
		return WITCHES_BREW;
	}
}
