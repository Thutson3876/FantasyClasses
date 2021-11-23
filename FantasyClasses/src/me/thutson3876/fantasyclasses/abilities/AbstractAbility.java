package me.thutson3876.fantasyclasses.abilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.playermanagement.FantasyPlayer;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public abstract class AbstractAbility implements Ability, Listener {

	protected static final FantasyClasses plugin = FantasyClasses.getPlugin();

	protected FantasyPlayer fplayer;
	protected Player player;

	protected String displayName;

	protected int coolDowninTicks = 0;

	protected int currentLevel = 0;
	protected int maximumLevel = 90000000;

	protected int skillPointCost = 1;

	protected boolean isEnabled = false;

	protected ItemStack itemStack;

	public AbstractAbility(Player p) {
		setDefaults();
		if (p != null) {
			player = p;
			fplayer = plugin.getPlayerManager().getPlayer(p);
		}
	}

	public AbstractAbility() {
		setDefaults();
	}

	@Override
	public Player getPlayer() {
		return player;
	}
	
	protected FantasyPlayer getFantasyPlayer() {
		return fplayer = plugin.getPlayerManager().getPlayer(player);
	}

	@Override
	public void setPlayer(Player p) {
		if (p != null) {
			player = p;
			fplayer = plugin.getPlayerManager().getPlayer(p);
		}
	}
	
	@Override
	public void setFantasyPlayer(FantasyPlayer p) {
		if (p != null) {
			fplayer = p;
		}
	}

	@Override
	public String getCommandName() {
		return this.getName().replaceAll(" ", "_");
	}
	
	@Override
	public String getName() {
		return displayName;
	}

	@Override
	public int getCurrentLevel() {
		return this.currentLevel;
	}

	@Override
	public void setLevel(int level) {
		this.currentLevel = level;
		this.applyLevelModifiers();
		this.updateItemStack();
	}

	@Override
	public void levelUp() {
		this.currentLevel++;
		applyLevelModifiers();
		this.updateItemStack();
		this.player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.6F, 1F);
	}

	@Override
	public void resetLevel() {
		disable();
	}
	
	@Override
	public boolean isEnabled() {
		return currentLevel > 0;
	}

	protected void enable() {
		this.isEnabled = true;
	}

	protected void disable() {
		this.setLevel(0);
		applyLevelModifiers();
		plugin.getPlayerManager().getPlayer(this.player).removeAbility(this);
	}

	protected ItemStack createItemStack(Material mat) {
		ItemStack item = new ItemStack(mat);
		List<String> lore = new ArrayList<>();
		lore.add("Level: " + this.currentLevel + "/" + this.maximumLevel);
		lore.add("Cost: " + this.skillPointCost);
		lore.add(this.getDescription());
		lore.add(this.getInstructions());
		
		if(this instanceof Bindable) {
			Bindable bindable = (Bindable)this;
			if(bindable.getBoundType() == null) {
				lore.add("&bBindable");
			}
			else {
				lore.add("&bBound to: &6" + bindable.getBoundType().name());
			}
		}

		List<String> loreFinal = new ArrayList<>();
		lore = ChatUtils.splitStringAtLength(lore, 30);
		
		for(String s : lore) {
			loreFinal.add(ChatUtils.chat(s));
		}

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatUtils.chat("&6" + this.displayName));
		meta.setLore(loreFinal);
		item.setItemMeta(meta);
		this.itemStack = item;
		return this.itemStack;
	}

	public int getMaxLevel() {
		return this.maximumLevel;
	}

	@Override
	public int getSkillPointCost() {
		return this.skillPointCost;
	}

	public int getCooldownInTicks() {
		return this.coolDowninTicks;
	}

	public double getCooldownInSeconds() {
		return this.coolDowninTicks / 20;
	}
	
	@Override
	public long getCooldown() {
		return this.coolDowninTicks;
	}

	@Override
	public ItemStack getItemStack() {
		updateItemStack();
		return itemStack;
	}

	private void updateItemStack() {
		this.itemStack = createItemStack(this.itemStack.getType());
	}

	public void triggerCooldown() {
		plugin.getCooldownManager().setCooldown(player, this, this.coolDowninTicks);
	}

	public int getMaxCooldown() {
		return this.coolDowninTicks;
	}

	public boolean isOnCooldown() {
		return (-1 != plugin.getCooldownManager().stillHasCooldown(player, this));
	}

	@Override
	public void writeToConfig(ConfigurationSection section) {
		section.set(this.getCommandName().toLowerCase() + "level", this.currentLevel);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", this.getCommandName().toLowerCase());
		map.put("level", Integer.valueOf(this.currentLevel));
		if(this instanceof Bindable) {
			if(((Bindable)this).getBoundType() == null) {
				map.put("type", "null");
			}
			else {
				map.put("type", ((Bindable)this).getBoundType().name());
			}
		}
		else if(this instanceof Scalable) {
			map.put(((Scalable)this).getScalableValueName(), ((Scalable)this).getScalableValue());
		}
			
		

		return map;
	}

	@Override
	public String toString() {
		return "\nName: " + this.getCommandName().toLowerCase() + "\nLevel: " + this.currentLevel + "\n";
	}
}
