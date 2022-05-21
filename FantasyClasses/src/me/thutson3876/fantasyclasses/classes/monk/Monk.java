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
		palm.addChild(new Disarm(p));
		Skill kyo = palm.addChild(new KyoketsuShoge(p));
		kyo.addChild(new WayOfTheBlade(p));
		skillTree.addChild(palm);
		
		Skill wind = new Skill(new WindWalker(p));
		Skill weave = wind.addChild(new BalancedLanding(p)).addChild(new WindWeaver(p));
		weave.addChild(new AncientTechnique(p)).addChild(new SpinningMixer(p));
		skillTree.addChild(wind);
		
		Skill brew = new Skill(new BrewMaster(p));
		Skill belly = brew.addChild(new FulfillingMead(p)).addChild(new BeerBelly(p));
		belly.addChild(new UnarmoredDexterity(p));
		skillTree.addChild(brew);
		
		setSkillInMap(4, skillTree);
		setSkillInMap(9 + 2, palm);
		setSkillInMap(9 + 1, palm.getNext().get(0));
		setSkillInMap(18 + 2, kyo);
		setSkillInMap(27 + 2, kyo.getNext().get(0));
		setSkillInMap(9 + 4, wind);
		setSkillInMap(18 + 4, wind.getNext().get(0));
		setSkillInMap(27 + 4, weave);
		setSkillInMap(36 + 4, weave.getNext().get(0));
		setSkillInMap(45 + 4, weave.getNext().get(0).getNext().get(0));
		setSkillInMap(18 + 6, brew);
		setSkillInMap(27 + 6, brew.getNext().get(0));
		setSkillInMap(36 + 6, belly);
		setSkillInMap(36 + 7, belly.getNext().get(0));
		
		this.setPrerequisites();
	}

}
