package me.thutson3876.fantasyclasses.classes.druid;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.Scalable;
import me.thutson3876.fantasyclasses.util.ChatUtils;
import me.thutson3876.fantasyclasses.util.DamageCauseList;
import me.thutson3876.fantasyclasses.util.MaterialLists;

public class Survivalist extends AbstractAbility implements Scalable {

	private final double enviroMod = 1.5;
	private final float foodMod = 0.5f;
	private int bonusMod = 0;
	private double prevMaxHp = 20.0;
	
	public Survivalist(Player p) {
		super(p);
	}
	
	@Override
	public void setDefaults() {
		this.coolDowninTicks = 30;
		this.displayName = "Survivalist";
		this.skillPointCost = 1;
		this.maximumLevel = 1;

		this.createItemStack(Material.APPLE);	
	}

	@Override
	public boolean trigger(Event event) {
		if(!isEnabled())
			return false;
		
		//Penalty Events
		if(event instanceof FoodLevelChangeEvent) {
			FoodLevelChangeEvent e = (FoodLevelChangeEvent) event;
			
			if(!e.getEntity().equals(player))
				return false;
			
			int change = player.getFoodLevel() - e.getFoodLevel();
			if(change >= 0) {
				e.setFoodLevel((int) (e.getFoodLevel() + (Math.round(change * (bonusValue() / 500.0)))));
				return false;
			}
			
			Random rng = new Random();
			if(rng.nextDouble() < 0.5)
				bonusMod++;
				
			
			e.setFoodLevel(e.getFoodLevel() - (Math.round(change * foodMod)));
			
			return false;
		}
		else if(event instanceof EntityExhaustionEvent) {
			EntityExhaustionEvent e = (EntityExhaustionEvent)event;
			
			if(!e.getEntity().equals(player))
				return false;
			
			e.setExhaustion(e.getExhaustion() * foodMod);
			
			return false;
		}
		else if(event instanceof EntityDamageEvent) {
			EntityDamageEvent e = (EntityDamageEvent)event;
			
			if(!e.getEntity().equals(player))
				return false;
			
			double maxHp = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
			if(e.getDamage() >= player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / 2.0)
				player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHp - 2.0);
			
			if(!DamageCauseList.ENVIRONMENTAL.getDamageCauseList().contains(e.getCause()))
				return false;
			
			double newDmg = e.getDamage() * (enviroMod - (bonusValue() / 500.0));
			e.setDamage(newDmg);
			
			return false;
		}
		else if(event instanceof PlayerItemDamageEvent) {
			PlayerItemDamageEvent e = (PlayerItemDamageEvent)event;
			
			if(!e.getPlayer().equals(player))
				return false;
		
			if(MaterialLists.TOOL.getMaterials().contains(e.getItem().getType())) {
				double chance = (new Random()).nextDouble() + (bonusValue() / 1000.0);
				if(chance > 0.5)
					e.setDamage(e.getDamage() + 1);
			}
			
			return false;
		}
		//Bonus Events
		else if(event instanceof PlayerExpChangeEvent) {
			PlayerExpChangeEvent e = (PlayerExpChangeEvent)event;
			
			if(!e.getPlayer().equals(player))
				return false;
			
			int newAmt = (int) Math.round(e.getAmount() * (1 + (bonusValue() / 1000.0)));
			e.setAmount(newAmt);
			
			return false;
		}
		else if(event instanceof EntityRegainHealthEvent) {
			EntityRegainHealthEvent e = (EntityRegainHealthEvent)event;
			
			if(!e.getEntity().equals(player))
				return false;
			
			if(!e.getRegainReason().equals(RegainReason.SATIATED) && !e.getRegainReason().equals(RegainReason.EATING))
				return false;
			
			double newAmt = e.getAmount() * (1 + (bonusValue() / 1000.0));
			e.setAmount(newAmt);
			return false;
		}
		else if(event instanceof PlayerBedEnterEvent) {
			PlayerBedEnterEvent e = (PlayerBedEnterEvent)event;
			
			if(!e.getPlayer().equals(player))
				return false;
			
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(prevMaxHp);
			player.playSound(player.getLocation(), Sound.MUSIC_MENU, 0.3f, 1.0f);
			player.sendMessage(ChatUtils.chat("&5You begin to feel well rested..."));
			
			return false;
		}
		else if(event instanceof EntityTameEvent) {
				EntityTameEvent e = (EntityTameEvent)event;
				if(e.getOwner().equals(player))
					bonusMod++;
		}
		
		return false;
	}

	@Override
	public void deInit() {
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(prevMaxHp);
	}
	
	@Override
	public String getInstructions() {
		return "Survive";
	}

	@Override
	public String getDescription() {
		return "Survival is harder. In return you grow stronger...";
	}

	@Override
	public boolean getDealsDamage() {
		return false;
	}

	@Override
	public void applyLevelModifiers() {
		this.prevMaxHp = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
	}
	
	private double bonusValue() {
		//soft-cap at around X = 2000 Y = 250
		return (4 * Math.sqrt(2 * bonusMod));
	}
	
	@Override
	public int getScalableValue() {
		return bonusMod;
	}

	@Override
	public void setScalableValue(int amt) {
		bonusMod = amt;
	}
	
	@Override
	public String getScalableValueName() {
		return "survivalist";
	}
}
