package me.thutson3876.fantasyclasses.classes.alchemy;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;

public class Alchemy extends AbstractFantasyClass {

	public Alchemy(Player p) {
		super(p);
		
		name = "Alchemist";
		
		this.setItemStack(Material.POTION, name, "A class for making powerful potions");
		
		this.skillTree = new Skill(new ReagantHarvest(p));
		
		Skill potent = new Skill(new PotentBrewing(p));
		potent.addChild(new PotentSplash(p));
		potent.addChild(new EnhancedRepitoire(p));
		skillTree.addChild(potent);
		
		Skill poison = new Skill(new DeadlyPoison(p));
		poison.addChild(new SunderingSplash(p));
		poison.addChild(new LiquidDeath(p));
		skillTree.addChild(poison);
		
		skillTree.addChild(new Immunology(p));
	}

}
