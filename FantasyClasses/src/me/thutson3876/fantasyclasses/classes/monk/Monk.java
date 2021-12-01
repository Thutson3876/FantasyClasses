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
		palm.addChild(new WayOfTheBlade(p));
		skillTree.addChild(palm);
		
		Skill wind = new Skill(new WindWalker(p));
		wind.addChild(new WindWeaver(p));
		wind.addChild(new SpinningMixer(p));
		skillTree.addChild(wind);
		
		Skill brew = new Skill(new BrewMaster(p));
		brew.addChild(new BeerBelly(p));
		brew.addChild(new FulfillingMead(p));
		brew.addChild(new UnarmoredDexterity(p));
		skillTree.addChild(brew);
	}

}
