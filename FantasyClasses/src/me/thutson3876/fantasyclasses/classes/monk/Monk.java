package me.thutson3876.fantasyclasses.classes.monk;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;

public class Monk extends AbstractFantasyClass {

	public Monk(Player p) {
		super(p);
		name = "Monk";

		this.setItemStack(Material.BAMBOO, name, "A class based on martial arts and letting go of the material world");
		
		skillTree = new Skill(new UnarmoredMovement(p));
		Skill palm = new Skill(new OpenPalm(p));
		palm.addChild(new KyoketsuShoge(p));
		skillTree.addChild(palm);
		
		Skill wind = new Skill(new WindWalker(p));
		wind.addChild(new WindWeaver(p));
		skillTree.addChild(wind);
		
		Skill brew = new Skill(new BrewMaster(p));
		skillTree.addChild(brew);
		
		skillTree.addChild(new UnarmoredDexterity(p));
	}

}
