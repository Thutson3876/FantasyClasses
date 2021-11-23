package me.thutson3876.fantasyclasses.abilities;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;
import me.thutson3876.fantasyclasses.playermanagement.FantasyPlayer;

@SerializableAs("Ability")
public interface Ability extends ConfigurationSerializable {

	void setDefaults();

	boolean trigger(Event event);

	long getCooldown();

	Player getPlayer();

	ItemStack getItemStack();

	void setPlayer(Player p);
	
	void setFantasyPlayer(FantasyPlayer p);

	String getName();

	String getInstructions();

	String getDescription();

	String getCommandName();

	boolean getDealsDamage();

	boolean isEnabled();

	void resetLevel();

	int getCurrentLevel();
	
	int getMaxLevel();

	void setLevel(int level);

	void levelUp();

	int getSkillPointCost();

	void applyLevelModifiers();

	void triggerCooldown();

	void writeToConfig(ConfigurationSection section);

	@Override
	Map<String, Object> serialize();

	static Ability deSerialize(Map<String, Object> map, FantasyPlayer p) {
		String name = null;
		int level = -1;
		String type = null;
		
		if (map.containsKey("name")) {
			name = (String) map.get("name");
		}
		if (map.containsKey("level")) {
			level = ((Integer) map.get("level")).intValue();
		}
		if (map.containsKey("type")) {
			type = ((String) map.get("type"));
		}
		
		if (name == null || level < 0) {
			return null;
		}

		Ability abil = null;
		for (AbstractFantasyClass clazz : p.getFantasyClasses()) {
			for (Skill s : clazz.getSkillTree()) {
				abil = s.getAbility();
				if (abil.getCommandName().toLowerCase().equals(name)) {
					abil.setLevel(level);
					if(abil instanceof Bindable) {
						if(type == null || type.equalsIgnoreCase("null")) {
							((Bindable)abil).setBoundType(null);
						}
						else {
							((Bindable)abil).setBoundType(Material.getMaterial(type));
						}
						
					}
					else if(abil instanceof Scalable) {
						((Scalable)abil).setScalableValue((Integer) map.get(((Scalable)abil).getScalableValueName()));
					}
						
					s.replaceSkillAbility(abil);
					return abil;
				}
			}
		}

		return abil;
	}
}
