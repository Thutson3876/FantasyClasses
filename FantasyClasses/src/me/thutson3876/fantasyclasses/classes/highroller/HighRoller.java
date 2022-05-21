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
		skillTree.addChild(new GrandAppetite(p));
		skillTree.addChild(new HookLineAnd(p));
		skillTree.addChild(new WondrousMagic(p));
		skillTree.addChild(new Tolerance(p));
		skillTree.addChild(new Masochism(p));
	
		setSkillInMap(9 + 7, skillTree);
		setSkillInMap(27 + 0, skillTree.getNext().get(8));
		setSkillInMap(27 + 1, skillTree.getNext().get(0));
		setSkillInMap(27 + 2, skillTree.getNext().get(1));
		setSkillInMap(27 + 3, skillTree.getNext().get(2));
		setSkillInMap(27 + 4, skillTree.getNext().get(3));
		setSkillInMap(27 + 5, skillTree.getNext().get(4));
		setSkillInMap(27 + 6, skillTree.getNext().get(5));
		setSkillInMap(27 + 7, skillTree.getNext().get(6));
		setSkillInMap(27 + 8, skillTree.getNext().get(7));
		
		this.setPrerequisites();
	}

}
