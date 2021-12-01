package me.thutson3876.fantasyclasses.classes.combat;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;

public class Combat extends AbstractFantasyClass {
	
	public Combat(Player p) {
		super(p);
		name = "Combat Proficiency";
		
		this.setItemStack(Material.IRON_SWORD, name, "A class focused on improving your weaponry");
		
		skillTree = new Skill(new Momentum(p));
		
		Skill sword = new Skill(new Swordsman(p));
		sword.addChild(new DualWielding(p));
		sword.addChild(new Parry(p));
		sword.addChild(new HonedEdge(p));
		skillTree.addChild(sword);
		
		Skill axe = new Skill(new AxeWielder(p));
		axe.addChild(new Rage(p));
		axe.addChild(new DeepDive(p));
		axe.addChild(new HeavyBlow(p));
		skillTree.addChild(axe);
		
		Skill scythe = new Skill(new ScytheSmith(p));
		scythe.addChild(new Cripple(p));
		scythe.addChild(new LifeRip(p));
		scythe.addChild(new RaiseDead(p));
		skillTree.addChild(scythe);
		
	}
}
