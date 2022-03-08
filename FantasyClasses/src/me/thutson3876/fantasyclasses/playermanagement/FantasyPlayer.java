	package me.thutson3876.fantasyclasses.playermanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.abilities.Scalable;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;
import me.thutson3876.fantasyclasses.classes.combat.Combat;
import me.thutson3876.fantasyclasses.classes.druid.Druid;
import me.thutson3876.fantasyclasses.classes.dungeoneer.Dungeoneer;
import me.thutson3876.fantasyclasses.classes.highroller.HighRoller;
import me.thutson3876.fantasyclasses.classes.monk.Monk;
import me.thutson3876.fantasyclasses.classes.seaguardian.SeaGuardian;
import me.thutson3876.fantasyclasses.classes.witch.Witchcraft;
import me.thutson3876.fantasyclasses.gui.ClassSelectionGUI;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public class FantasyPlayer {

	protected static final FantasyClasses plugin = FantasyClasses.getPlugin();

	private Player bukkitPlayer;
	private List<Ability> abilities = new ArrayList<>();
	private List<AbstractFantasyClass> classes = new ArrayList<>();
	private Map<String, Integer> scalables = new HashMap<>();

	private int skillPoints = 0;
	private int level = 0;
	private long skillExp = 0;

	public FantasyPlayer(Player p) {
		bukkitPlayer = p;
		String uuid = p.getUniqueId().toString();
		classes.add(new Combat(p));
		classes.add(new Monk(p));
		classes.add(new Druid(p));
		classes.add(new Witchcraft(p));
		classes.add(new HighRoller(p));
		classes.add(new Dungeoneer(p));
		classes.add(new SeaGuardian(p));
		
		FileConfiguration config = plugin.getConfig();
		if(!config.contains("players." + uuid)) {
			config.set("players." + uuid + ".name", p.getDisplayName());
			config.set("players." + uuid + ".skillpoints", this.skillPoints);
			config.set("players." + uuid + ".exp", this.skillExp);
			config.set("players." + uuid + ".abilities", new ArrayList<String>());
			config.getConfigurationSection("players." + uuid + ".abilities");
	        
		}
		else {
			this.loadFromConfig();
		}
		
		plugin.saveConfig();
	}

	public void addSkillPoints(int amt) {
		this.skillPoints += amt;
	}

	public boolean spendSkillPoints(int amt) {
		int newAmt = this.skillPoints - amt;
		if (newAmt < 0) {
			return false;
		}

		this.skillPoints = newAmt;
		return true;
	}
	
	public long getSkillExp() {
		return skillExp;
	}
	
	public void addSkillExp(int amt) {
		skillExp += amt;
		int newLevel = calculateLevel();
		if(newLevel > level) {
			skillPoints += newLevel - level;
			level = newLevel;
			
			bukkitPlayer.playSound(bukkitPlayer.getLocation(), Sound.ENTITY_WANDERING_TRADER_YES, 1.0f, 1.3f);
			bukkitPlayer.sendMessage(ChatUtils.chat("%6Level Up! Your new level is &3" + newLevel));
			bukkitPlayer.sendMessage(ChatUtils.chat("%6Use &3/chooseclass &6to spend your new skillpoint!"));
		}
	}
	
	public int getPlayerLevel() {
		return level;
	}

	private int calculateLevel() {
		//7000 xp = lvl 50
		return (int) (Math.sqrt((0.4 * skillExp) + 9) - 3);
	}
	
	/*private int calculateLevel(int exp) {
		return (int) (Math.sqrt((0.4 * exp) + 9) - 3);
	}*/
	
	public long calculateCurrentLevelExpCost() {
		return Math.round((Math.pow(((level) + 3), 2) - 9) / 0.4);
	}
	
	public long calculateNextLevelExpCost() {
		return Math.round((Math.pow(((level + 1) + 3), 2) - 9) / 0.4);
	}
	
	public Player getPlayer() {
		return bukkitPlayer;
	}

	public int getSkillPoints() {
		return skillPoints;
	}

	public List<AbstractFantasyClass> getFantasyClasses() {
		return classes;
	}
	
	public List<Ability> getAbilities(){
		return this.abilities;
	}
	
	public int getScalableValue(String name) {
		Integer value = scalables.get(name);
		if(value == null)
			return 0;
		
		return value;
	}
	
	public void setScalableValue(String name, int amt) {
		scalables.put(name, amt);
	}

	public void openClassGui() {
		ClassSelectionGUI gui = new ClassSelectionGUI(bukkitPlayer);
		gui.openInventory(bukkitPlayer);
	}
	
	public void resetAllAbilities() {
		List<Ability> newAbils = new ArrayList<>();
		for(Ability abil : abilities) {
			abil.deInit();
			abil.setLevel(0);
			if(abil instanceof Scalable) {
				newAbils.add(abil);
				continue;
			}
		}
		
		this.skillPoints = this.level;
		
		this.abilities = newAbils;
	}

	public void removeAbility(Ability abil) {
		this.abilities.remove(abil);
	}
	
	public void removeAndResetAbility(Ability abil) {
		for(AbstractFantasyClass clazz : this.classes) {
			clazz.resetSkillTreeAbility(abil);
		}
		this.abilities.remove(abil);
	}

	public void addAbility(Ability abil) {
		abil.setFantasyPlayer(this);
		for(AbstractFantasyClass clazz : this.classes) {
			clazz.replaceSkillTreeAbility(abil);
		}
		
		Ability toReplace = null;
		
		for(Ability a : abilities) {
			if(a.getCommandName().equalsIgnoreCase(abil.getCommandName())) {
				toReplace = a;
				break;
			}
		}
		
		if(toReplace == null) {
			this.abilities.add(abil);
		}
		else {
			abilities.remove(toReplace);
			abilities.add(abil);
		}
		
	}
	
	private void loadFromConfig() {
		String uuid = this.bukkitPlayer.getUniqueId().toString();
		FileConfiguration config = plugin.getConfig();
		this.skillPoints = config.getInt("players." + uuid + ".skillpoints");
		this.skillExp = config.getInt("players." + uuid + ".exp");
        List<?> abilList = config.getList("players." + uuid + ".abilities");
        
        this.level = calculateLevel();
        
        List<Ability> abilities = new ArrayList<>();
        for(Object o : abilList) {
        	@SuppressWarnings("unchecked")
			Ability temp = Ability.deSerialize((Map<String, Object>) o, this);
        	if(temp != null) {
        		abilities.add(temp);
        	}
        }
        
        for(Ability abil : abilities) {
        	if(abil == null) {
        		System.out.println("Ability from config was null for " + bukkitPlayer.getDisplayName());
				continue;
        	}
        	
        	if(abil instanceof Scalable) {
        		scalables.put(((Scalable)abil).getScalableValueName(), ((Scalable)abil).getScalableValue());
        	}
        	
        	addAbility(abil);
        }
	}
	
	public void deInit() {
		String uuid = bukkitPlayer.getUniqueId().toString();
		List<Map<String, Object>> list = new ArrayList<>();
		FileConfiguration config = plugin.getConfig();
		config.set("players." + uuid + ".skillpoints", this.skillPoints);
		config.set("players." + uuid + ".exp", this.skillExp);
		for(Ability abil : this.abilities) {
			if(abil == null)
				continue;
			abil.deInit();
			list.add(abil.serialize());
		}
		config.set("players." + uuid + ".abilities", list);
		plugin.saveConfig();
		
		removeAttributeModifiers(bukkitPlayer, Attribute.GENERIC_ATTACK_SPEED);
		removeAttributeModifiers(bukkitPlayer, Attribute.GENERIC_MOVEMENT_SPEED);
	}
	
	private static void removeAttributeModifiers(Player p, Attribute att) {
		Collection<AttributeModifier> mods = p.getAttribute(att).getModifiers();
		
		if(mods == null || mods.isEmpty())
			return;
		
		for(AttributeModifier mod : mods) {
			p.getAttribute(att).removeModifier(mod);
		}
	}
}
