package me.thutson3876.fantasyclasses.abilities.skills;

import me.thutson3876.fantasyclasses.classes.FantasyClass;
import me.thutson3876.fantasyclasses.classes.combat.Combat;

public enum ClassTree {

	COMBAT(new Combat(null));
	
	private ClassTree(FantasyClass clazz) {
		
	}
	
}
