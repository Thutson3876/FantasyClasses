package me.thutson3876.fantasyclasses.classes.druid;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;

public class Druid extends AbstractFantasyClass {

	public Druid(Player p) {
		super(p);
		name = "Druid";

		this.setItemStack(Material.RABBIT_FOOT, name, "A class based on revering nature and its gifts");

		skillTree = new Skill(new Forager(p));
		
		Skill rejuv = new Skill(new Rejuvination(p));
		Skill bark = rejuv.addChild(new Druidcraft(p)).addChild(new Barkskin(p));
		bark.addChild(new NaturesBlessing(p));
		bark.addChild(new Tranquility(p));
		skillTree.addChild(rejuv);
		rejuv.addChild(new GreenThumb(p)).addChild(new Regrowth(p));
		
		Skill beast = new Skill(new BestFriend(p));
		Skill stamp = beast.addChild(new BeastMaster(p)).addChild(new Stampede(p));
		stamp.addChild(new BirdSinger(p));
		stamp.addChild(new FelinesGrace(p));
		skillTree.addChild(beast);
		
		Skill tree = new Skill(new TreeFeller(p));
		Skill surv = new Skill(new Survivalist(p));
		tree.addChild(new DruidicInscription(p)).addChild(surv);
		surv.addChild(new SurvivalInstincts(p));
		skillTree.addChild(tree);
		
		setSkillInMap(4, skillTree);
		setSkillInMap(9 + 1, rejuv.getNext().get(1));
		setSkillInMap(9 + 0, rejuv.getNext().get(1).getNext().get(0));
		setSkillInMap(9 + 2, rejuv);
		setSkillInMap(18 + 2, rejuv.getNext().get(0));
		setSkillInMap(27 + 2, bark);
		setSkillInMap(36 + 2, bark.getNext().get(0));
		setSkillInMap(27 + 1, bark.getNext().get(1));
		setSkillInMap(9 + 4, beast);
		setSkillInMap(18 + 4, stamp);
		setSkillInMap(27 + 4, stamp.getNext().get(0));
		setSkillInMap(27 + 5, stamp.getNext().get(1));
		setSkillInMap(9 + 6, tree);
		setSkillInMap(9 + 7, tree.getNext().get(0));
		setSkillInMap(18 + 7, surv);
		setSkillInMap(18 + 8, surv.getNext().get(0));
		
		this.setPrerequisites();
	}
}
