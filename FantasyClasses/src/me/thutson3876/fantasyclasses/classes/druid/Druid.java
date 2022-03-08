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
		
		Skill beast = new Skill(new BestFriend(p));
		beast.addChild(new BeastMaster(p));
		beast.addChild(new Stampede(p));
		beast.addChild(new BirdSinger(p));
		skillTree.addChild(beast);
		
		Skill rejuv = new Skill(new Rejuvination(p));
		rejuv.addChild(new Druidcraft(p));
		rejuv.addChild(new Barkskin(p));
		rejuv.addChild(new NaturesBlessing(p));
		rejuv.addChild(new Tranquility(p));
		skillTree.addChild(rejuv);
		
		Skill surv = new Skill(new Survivalist(p));
		surv.addChild(new TreeFeller(p));
		surv.addChild(new SurvivalInstincts(p));
		surv.addChild(new DruidicInscription(p));
		skillTree.addChild(surv);
	}
}
