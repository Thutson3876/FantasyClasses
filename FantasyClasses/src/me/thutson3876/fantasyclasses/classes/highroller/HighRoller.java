package me.thutson3876.fantasyclasses.classes.highroller;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;

public class HighRoller extends AbstractFantasyClass {

	public HighRoller(Player p) {
		super(p);
		name = "High Roller";

		this.setItemStack(Material.BELL, name, "A class based entirely on luck");

		skillTree = new Skill(new TwentySides(p));
		skillTree.addChild(new TwentyMore(p));
		skillTree.addChild(new AnimalHandling(p));
		skillTree.addChild(new ConstitutionSavingThrow(p));
		skillTree.addChild(new CharismaCheck(p));
		skillTree.addChild(new WondrousMagic(p));
	}

}
