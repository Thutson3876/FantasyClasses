package me.thutson3876.fantasyclasses.gui.treegui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.gui.GuiItem;

public class SkillGUI extends GuiItem {

	public SkillGUI(Skill skill, ItemStack item) {
		super(item, null);
	}

	List<Skill> skills = new ArrayList<>();
	
	
}
