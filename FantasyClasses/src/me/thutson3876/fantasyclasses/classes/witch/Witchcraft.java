package me.thutson3876.fantasyclasses.classes.witch;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;

public class Witchcraft extends AbstractFantasyClass {

	public Witchcraft(Player p) {
		super(p);

		name = "Witchcraft";

		this.setItemStack(Material.NETHER_WART, name, "A class for utilizing deep-rooted magical abilities");

		this.skillTree = new Skill(new WitchHunt(p));
		this.skillTree.addChild(new WitchesCauldron(p));
		this.skillTree.addChild(new NoBroomNeeded(p));
		this.skillTree.addChild(new MagicalTolerance(p));
		
		Skill wand = new Skill(new WitchWand(p));
		wand.addChild(new FireballWand(p));
		wand.addChild(new ConfusionWand(p));
		wand.addChild(new VexWand(p));
		
		skillTree.addChild(wand);
	}
}
