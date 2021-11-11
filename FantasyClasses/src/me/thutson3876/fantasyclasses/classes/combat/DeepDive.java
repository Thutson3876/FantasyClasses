package me.thutson3876.fantasyclasses.classes.combat;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class DeepDive extends AbstractAbility {

	private float power = 3.0F;
	private double dropSpeed = 2.0;
	private double projectedEntitiesSpeed = 1.2;

	public DeepDive(Player p) {
		super(p);
	}

	@Override
	public void setDefaults() {
		this.coolDowninTicks = 10 * 20;
		this.displayName = "Deep Dive";
		this.skillPointCost = 1;
		this.maximumLevel = 1;

		this.createItemStack(Material.QUARTZ);
	}

	@Override
	public boolean trigger(Event event) {
		if (!(event instanceof PlayerToggleSneakEvent)) {
			return false;
		}

		PlayerToggleSneakEvent e = (PlayerToggleSneakEvent) event;
		Player p = e.getPlayer();

		if (!this.player.equals(p))
			return false;

		if (AbilityUtils.getHeightAboveGround(p) < 3.0) {
			return false;
		}
		if (this.isOnCooldown()) {
			return false;
		}
		Material mainHand = player.getInventory().getItemInMainHand().getType();
		Material offHand = player.getInventory().getItemInOffHand().getType();
		
		if (!(MaterialLists.AXE.getMaterials().contains(mainHand) || MaterialLists.AXE.getMaterials().contains(offHand))
				&& !(MaterialLists.HOE.getMaterials().contains(mainHand) || MaterialLists.HOE.getMaterials().contains(offHand)))
			return false;

		return tempGroundPound(p, projectedEntitiesSpeed);
	}

	private boolean tempGroundPound(Player p, double speed) {
		Location pLoc = p.getLocation();

		double height = AbilityUtils.getHeightAboveGround(p);

		p.setFallDistance(-(float) height);
		AbilityUtils.moveToward(p, p.getLocation().subtract(0, height, 0), dropSpeed);

		World world = p.getWorld();
		Block[] blocksUnderPlayer = AbilityUtils.getBlocksAroundPlayer(p, 5);

		if (blocksUnderPlayer[0] == null) {
			return false;
		}
		Random rng = new Random();

		Vector yAxis = new Vector(0, 1, 0);

		BukkitRunnable task = new BukkitRunnable() {

			@Override
			public void run() {
				Location bLoc = null;

				for (Block b : blocksUnderPlayer) {
					if (b != null) {
						bLoc = b.getLocation();
						FallingBlock temp = world.spawnFallingBlock(bLoc, b.getBlockData());

						double x = bLoc.getX() - pLoc.getX();
						double y = bLoc.getY() - pLoc.getY();
						double z = bLoc.getZ() - pLoc.getZ();
						double angle = (Math.PI / 2) + 0.4 * rng.nextDouble() - 0.2;
						double yBoost = 1.0;

						Vector velocity = new Vector(x, y + yBoost, z).normalize().rotateAroundAxis(yAxis, angle)
								.multiply(-speed);

						b.setType(Material.AIR);
						temp.setVelocity(velocity);
					}
				}
				p.setInvulnerable(true);
				// System.out.println("Invulnerable!");
				world.createExplosion(p.getLocation(), power, false, true, p);
				p.setInvulnerable(false);
				// System.out.println("No Longer Invulnerable!");
			}
		};

		task.runTaskLater(plugin, Math.round(height - (dropSpeed * 1.2)));

		return true;
	}

	@Override
	public String getInstructions() {
		return "Crouch while mid-air and holding an axe or hoe";
	}

	@Override
	public String getDescription() {
		return "Dive downwards and cause an explosion where you land";
	}

	@Override
	public boolean getDealsDamage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		// TODO Auto-generated method stub

	}
}
