package me.thutson3876.fantasyclasses.classes.highroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.AllBuffs;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.AllDebuffs;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.Cleanse;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.CowSplosion;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.DeathByDiamonds;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.Explosion;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.GodMode;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.MidasTouch;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.RandomAbility;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.RandomTeleport;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.SelfExplosion;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.SelfSmite;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.SmiteAll;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.SpawnHorde;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.SpawnWither;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.Speed;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.Spook;
import me.thutson3876.fantasyclasses.classes.highroller.randomabilities.SpookReal;

public class WondrousTable {

	private final static Random rng = new Random();
	private static List<RandomAbility> abilities = new ArrayList<>();
	
	static {
		abilities.add(new SelfExplosion());
		abilities.add(new SelfSmite());
		abilities.add(new Explosion());
		abilities.add(new SpawnWither());
		abilities.add(new SpawnHorde());
		abilities.add(new Cleanse());
		abilities.add(new AllBuffs());
		abilities.add(new AllDebuffs());
		abilities.add(new Spook());
		abilities.add(new SpookReal());
		abilities.add(new SmiteAll());
		abilities.add(new GodMode());
		abilities.add(new Speed());
		abilities.add(new DeathByDiamonds());
		abilities.add(new CowSplosion());
		abilities.add(new RandomTeleport());
		abilities.add(new MidasTouch());
	}
	
	public static void roll(Player p) {
		int roll = rng.nextInt(abilities.size());
		
		if(roll == abilities.size() - 1) {
			roll(p);
			roll(p);
			return;
		}
		
		abilities.get(roll).run(p);
	}
	
}
