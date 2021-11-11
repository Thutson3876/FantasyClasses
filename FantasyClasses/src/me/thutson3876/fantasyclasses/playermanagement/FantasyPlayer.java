package me.thutson3876.fantasyclasses.playermanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;
import me.thutson3876.fantasyclasses.classes.alchemy.Alchemy;
import me.thutson3876.fantasyclasses.classes.combat.Combat;
import me.thutson3876.fantasyclasses.classes.druid.Druid;
import me.thutson3876.fantasyclasses.classes.highroller.HighRoller;
import me.thutson3876.fantasyclasses.classes.monk.Monk;
import me.thutson3876.fantasyclasses.classes.witch.WitchHunt;
import me.thutson3876.fantasyclasses.classes.witch.Witchcraft;
import me.thutson3876.fantasyclasses.gui.ClassSelectionGUI;

public class FantasyPlayer {

	protected static final FantasyClasses plugin = FantasyClasses.getPlugin();

	protected Player bukkitPlayer;
	protected String name;
	protected List<Ability> abilities = new ArrayList<>();
	protected List<AbstractFantasyClass> classes = new ArrayList<>();

	private int skillPoints = 0;

	public FantasyPlayer(Player p) {
		bukkitPlayer = p;
		String uuid = p.getUniqueId().toString();
		classes.add(new Combat(p));
		classes.add(new Alchemy(p));
		classes.add(new HighRoller(p));
		classes.add(new Monk(p));
		classes.add(new Druid(p));
		classes.add(new Witchcraft(p));
		
		if(!plugin.getConfig().contains("players." + uuid)) {
	        plugin.getConfig().set("players." + uuid + ".name", p.getDisplayName());
	        plugin.getConfig().set("players." + uuid + ".skillpoints", this.skillPoints);
	        plugin.getConfig().set("players." + uuid + ".abilities", new ArrayList<String>());
	        plugin.getConfig().getConfigurationSection("players." + uuid + ".abilities");
	        
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

	public void openClassGui() {
		ClassSelectionGUI gui = new ClassSelectionGUI(bukkitPlayer);
		gui.openInventory(bukkitPlayer);
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
	
	public int getMagicka() {
		for(Ability abil : this.getAbilities()) {
			if(abil instanceof WitchHunt)
				return ((WitchHunt)abil).getMagicka();
		}
		
		return 0;
	}
	
	private void loadFromConfig() {
		String uuid = this.bukkitPlayer.getUniqueId().toString();
		this.skillPoints = plugin.getConfig().getInt("players." + uuid + ".skillpoints");
        List<?> abilList = plugin.getConfig().getList("players." + uuid + ".abilities");
        
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
        	
        	addAbility(abil);
        }
	}
	
	public void deInit() {
		String uuid = bukkitPlayer.getUniqueId().toString();
		List<Map<String, Object>> list = new ArrayList<>();
		plugin.getConfig().set("players." + uuid + ".skillpoints", this.skillPoints);
		for(Ability abil : this.abilities) {
			if(abil == null)
				continue;
			list.add(abil.serialize());
		}
		plugin.getConfig().set("players." + uuid + ".abilities", list);
		plugin.saveConfig();
	}
}
