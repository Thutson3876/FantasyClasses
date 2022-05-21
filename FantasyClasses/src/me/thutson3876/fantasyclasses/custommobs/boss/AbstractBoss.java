package me.thutson3876.fantasyclasses.custommobs.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.thutson3876.fantasyclasses.custommobs.AbstractCustomMob;
import me.thutson3876.fantasyclasses.util.AbilityUtils;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public abstract class AbstractBoss extends AbstractCustomMob implements Boss {

	protected List<MobAbility> abilities = new ArrayList<>();

	protected BossBar bar = null;
	
	protected final String name;

	protected boolean requiresTarget = true;
	
	int abilityDelay = 10 * 20;
	
	int prevAbilIndex = -1;

	public AbstractBoss(Location loc, String name) {
		super(loc);

		this.name = name;
		ent.setCustomName(ChatUtils.chat(name));
		ent.setCustomNameVisible(true);
	}

	@Override
	protected void applyDefaults() {

	}
	
	@Override
	public void deInit() {
		removeBossBar();
	}

	@Override
	protected void tookDamage(EntityDamageByEntityEvent e) {
		broadcastDamageDealt(e);
	}

	protected void startAbilityTick() {
		if (abilities.isEmpty())
			return;

		Random rng = new Random();

		new BukkitRunnable() {

			@Override
			public void run() {
				if (ent == null || ent.isDead()) {
					this.cancel();
					return;
				}
				
				//Doesn't run if entity has no targets
				if(requiresTarget && ent instanceof Mob && ((Mob)ent).getTarget() == null)
					return;

				int abilsLength = abilities.size();
				int abilIndex = rng.nextInt(abilsLength);
				while(abilIndex == prevAbilIndex) {
					abilIndex = rng.nextInt(abilsLength);
				}
				prevAbilIndex = abilIndex;
				
				abilities.get(abilIndex).run((Mob) ent);
				
				//Broadcasts ability name to nearby players
				for(Player p : AbilityUtils.getNearbyPlayers(ent, 20)){
					p.sendMessage(ChatUtils.chat(name + " &8cast &4" + abilities.get(abilIndex).getName()));
				}
			}

		}.runTaskTimer(plugin, abilityDelay, abilityDelay);
	}

	@Override
	public BossBar getBossBar() {
		return bar;
	}

	protected void setBossBar(String title, BarColor color, BarStyle style, BarFlag... flags) {
		this.bar = Bukkit.createBossBar(ChatUtils.chat(title), color, style, flags);
		double maxHealth = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		this.bar.setVisible(true);

		new BukkitRunnable() {

			@Override
			public void run() {
				if (!ent.isDead()) {
					List<Player> barPlayers = new ArrayList<>();
					barPlayers.addAll(bar.getPlayers());
					List<Player> nearbyPlayers = new ArrayList<>();
					for (Entity e : ent.getNearbyEntities(20, 20, 20)) {
						if (e instanceof Player) {
							nearbyPlayers.add((Player) e);
						}
					}
					
					if(barPlayers != null && !barPlayers.isEmpty()) {
						
						for (Player p : nearbyPlayers) {
							if(barPlayers.contains(p))
								barPlayers.remove(p);
						}
						
						for(Player p : barPlayers) {
							bar.removePlayer(p);
						}
					}
					
					for(Player p : nearbyPlayers) {
						bar.addPlayer(p);
					}
					
					bar.setProgress(ent.getHealth() / maxHealth);
				} else {
					List<Player> players = bar.getPlayers();
					for (Player player : players) {
						bar.removePlayer(player);
					}
					bar.setVisible(false);
					cancel();
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}

	protected void addAbility(MobAbility abil) {
		abilities.add(abil);
	}
	
	protected void removeBossBar() {
		if(bar != null) {
			bar.setVisible(false);
			bar.removeAll();
		}
	}
	
	protected void broadcastDamageDealt(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		Player player = null;
		
		
		if(damager instanceof Player) {
			player = (Player) damager;
		}
		else if(damager instanceof Projectile) {
			Projectile proj = (Projectile) damager;
			if(proj.getShooter() instanceof Player) {
				player = (Player) proj.getShooter();
			}
		}
		
		if(player != null)
			player.sendMessage(ChatUtils.chat("&7You dealt &c" + AbilityUtils.doubleRoundToXDecimals(e.getFinalDamage(), 2) + " &7damage"));
	}
	
	@Override
	protected void dealtDamage(EntityDamageByEntityEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void tookDamage(EntityDamageEvent e) {
		
	}
	
	@Override
	protected void targeted(EntityTargetEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void healed(EntityRegainHealthEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void died(EntityDeathEvent e) {
		// TODO Auto-generated method stub
		
	}
}
