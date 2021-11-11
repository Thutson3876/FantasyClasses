package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;

public class Druid extends AbstractFantasyClass {

	public Druid(Player p) {
		super(p);
		name = "Druid";

		this.setItemStack(Material.AZALEA, name, "A class based on revering nature and its gifts");

		skillTree = new Skill(new Forager(p));
		skillTree.addChild(new BeastMaster(p));
		
	}
}
