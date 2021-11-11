package me.thutson3876.fantasyclasses.classes.highroller.randomabilities;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.fantasyclasses.util.PotionList;

public class AllDebuffs implements RandomAbility {

	@Override
	public void run(Player p) {
		for(PotionEffectType t : PotionList.DEBUFF.getPotList()) {
			p.addPotionEffect(new PotionEffect(t, 600, 1));
		}
	}

}
