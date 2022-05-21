package me.thutson3876.fantasyclasses.custommobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.thutson3876.fantasyclasses.collectible.Collectible;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public class DrownedMiner extends AbstractCustomMob {

	private static List<Material> dropMats = new ArrayList<>();
	
	private static final EntityType TYPE = EntityType.DROWNED; 
	
	static {
		dropMats.add(Material.PRISMARINE);
		dropMats.add(Material.DARK_PRISMARINE);
	}
	
	public DrownedMiner(Location loc) {
		super(loc);
		
		Random rng = new Random();
		Collection<ItemStack> drops = new ArrayList<>();
		for(Material mat : dropMats) {
			ItemStack item = new ItemStack(mat, rng.nextInt(13) + 4);
			
			drops.add(item);
		}
		
		EntityEquipment equip = ent.getEquipment();
		equip.setItemInMainHand(new ItemStack(Material.NETHERITE_PICKAXE));
		equip.setItemInMainHandDropChance(0.0f);
	}

	@Override
	protected void targeted(EntityTargetEvent e) {
		
	}
	
	@Override
	protected void applyDefaults() {
		this.setMaxHealth(30);
		this.setAttackDamage(8);
		this.setMoveSpeed(0.3f);
		this.setSkillExpReward(3);
	}

	@Override
	protected void healed(EntityRegainHealthEvent e) {
		
	}

	@Override
	protected void tookDamage(EntityDamageByEntityEvent e) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                ent.setVelocity(new Vector());
            }
        }, 1L);
	}

	@Override
	protected void dealtDamage(EntityDamageByEntityEvent e) {
		
	}

	@Override
	protected void died(EntityDeathEvent e) {
		Player killer = ent.getKiller();
		if(killer == null)
			return;
		
		killer.sendMessage(ChatUtils.chat(Collectible.ETCHED_GLASS.getRandomLore()));
	}

	@Override
	public String getMetadataTag() {
		return "drowned_miner";
	}

	@Override
	protected void tookDamage(EntityDamageEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected EntityType getEntityType() {
		return TYPE;
	}

}
