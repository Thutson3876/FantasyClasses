package me.thutson3876.fantasyclasses.classes.witch;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;
import me.thutson3876.fantasyclasses.classes.alchemy.DeadlyPoison;
import me.thutson3876.fantasyclasses.classes.alchemy.EnhancedRepitoire;
import me.thutson3876.fantasyclasses.classes.alchemy.Immunology;
import me.thutson3876.fantasyclasses.classes.alchemy.LiquidDeath;
import me.thutson3876.fantasyclasses.classes.alchemy.PotentBrewing;
import me.thutson3876.fantasyclasses.classes.alchemy.PotentSplash;
import me.thutson3876.fantasyclasses.classes.alchemy.ReagantHarvest;
import me.thutson3876.fantasyclasses.classes.alchemy.SunderingSplash;

public class Witchcraft extends AbstractFantasyClass {

	public Witchcraft(Player p) {
		super(p);

		name = "Witchcraft";

		this.setItemStack(Material.NETHER_WART, name, "A class for utilizing deep-rooted magical abilities");

		this.skillTree = new Skill(new ReagantHarvest(p));
		Skill hunt = new Skill(new WitchHunt(p));
		hunt.addChild(new WitchesCauldron(p));
		hunt.addChild(new NoBroomNeeded(p));
		hunt.addChild(new MagicalTolerance(p));
		
		Skill wand = new Skill(new WitchWand(p));
		wand.addChild(new FireballWand(p));
		wand.addChild(new ConfusionWand(p));
		wand.addChild(new VexWand(p));
		wand.addChild(new WitherWand(p));
		
		hunt.addChild(wand);
		
		skillTree.addChild(hunt);
		
		Skill enhanced = new Skill(new EnhancedRepitoire(p));
		enhanced.addChild(new PotentSplash(p));
		enhanced.addChild(new PotentBrewing(p));
		enhanced.addChild(new Immunology(p));
		skillTree.addChild(enhanced);
		
		Skill poison = new Skill(new DeadlyPoison(p));
		poison.addChild(new SunderingSplash(p));
		poison.addChild(new LiquidDeath(p));
		skillTree.addChild(poison);
	}
}
