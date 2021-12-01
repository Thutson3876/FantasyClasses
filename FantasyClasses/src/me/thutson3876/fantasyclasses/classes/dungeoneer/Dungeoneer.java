package me.thutson3876.fantasyclasses.classes.dungeoneer;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;

public class Dungeoneer extends AbstractFantasyClass {

	public Dungeoneer(Player p) {
		super(p);
		name = "Dungeoneer";

		this.setItemStack(Material.TORCH, name, "A class based on diving into the mysterious depths of the world");

		skillTree = new Skill(new BuiltToLast(p));
		
		Skill heat = new Skill(new HotHeaded(p));
		heat.addChild(new HeatedTip(p));
		heat.addChild(new HotHands(p));
		skillTree.addChild(heat);
		
		Skill mine = new Skill(new DiggyDiggyHole(p));
		mine.addChild(new Excavation(p));
		mine.addChild(new VeinFinder(p));
		skillTree.addChild(mine);
		
		Skill stone = new Skill(new StoneSkin(p));
		stone.addChild(new DungeonDelver(p));
		skillTree.addChild(stone);
	}

}
