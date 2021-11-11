package me.thutson3876.fantasyclasses.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.abilities.AbstractAbility;
import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.combat.Momentum;
import me.thutson3876.fantasyclasses.gui.AbstractGUI;
import me.thutson3876.fantasyclasses.gui.GuiItem;
import me.thutson3876.fantasyclasses.gui.SkillTreeGUI;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public abstract class AbstractFantasyClass implements FantasyClass {

	private ItemStack item;
	protected String name;
	protected String description;
	protected Skill skillTree;
	protected Player p;

	public AbstractFantasyClass(Player p) {
		this.p = p;
	}

	@Override
	public Skill getSkillTree() {
		return skillTree;
	}

	@Override
	public void addPlayerToAll(Player p) {
		for (Skill s : skillTree) {
			s.getAbility().setPlayer(p);
		}
	}

	public GuiItem skillsAsGuiItem() {
		return skillTree.asGuiItem(null);
	}

	public GuiItem asGuiItem(AbstractGUI currentInv) {
		List<GuiItem> items = new ArrayList<>();
		SkillTreeGUI skillTreeGui = new SkillTreeGUI(p, name, this.skillTree, currentInv, items);
		items.add(skillTree.asGuiItem(skillTreeGui));
		skillTreeGui.setItems(items);
		skillTreeGui.defaultFillGaps(Material.BLACK_STAINED_GLASS_PANE);
		GuiItem guiItem = new GuiItem(item, currentInv, skillTreeGui);

		return guiItem;
	}

	public String getName() {
		return name;
	}

	protected ItemStack setItemStack(Material mat, String name, String... lore) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatUtils.chat("&6" + name));
		List<String> list = Arrays.asList(lore);
		meta.setLore(ChatUtils.splitStringAtLength(list, 33));
		item.setItemMeta(meta);
		this.item = item;

		return item;
	}

	public String getConfigName() {
		String configName = name.toLowerCase();
		configName.replaceAll("\\s", "");
		return configName;
	}

	public boolean replaceSkillTreeAbility(Ability abil) {
		if (abil == null)
			return false;
		for (Skill s : this.skillTree) {
			if (s.getAbility().getClass().equals(abil.getClass())) {
				s.replaceSkillAbility(abil);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean resetSkillTreeAbility(Ability abil) {
		if (abil == null)
			return false;
		for (Skill s : this.skillTree) {
			Ability ability = s.getAbility();
			if (ability.getClass().equals(abil.getClass())) {
				ability.setLevel(0);
				s.replaceSkillAbility(ability);
				return true;
			}
		}
		
		return false;
	}
	
	public static List<Class<? extends AbstractAbility>> getAbilityClassList() {
		List<Class<? extends AbstractAbility>> abilityClasses = new ArrayList<>();
		abilityClasses.add(Momentum.class);
		
		return abilityClasses;
	}
}
