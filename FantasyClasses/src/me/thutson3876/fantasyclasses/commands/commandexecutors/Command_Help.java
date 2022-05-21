package me.thutson3876.fantasyclasses.commands.commandexecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import me.thutson3876.fantasyclasses.commands.AbstractCommand;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public class Command_Help extends AbstractCommand implements Listener {

	public Command_Help() {
		super("fantasyhelp");
	}

	@Override
	protected boolean onInternalCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(ChatUtils.chat("&2&lFantasy Classes Help:"));
		sender.sendMessage(ChatUtils.chat("&6&nGetting Started: "));
		sender.sendMessage(ChatUtils.chat(
				"&3Use command &f/chooseclass &3to open the menu. At the top is your Skill Experience bar that shows how far you must progress to the next level, and how many skillpoints you have available. Click on the class icons to see their respective skill trees."));
		sender.sendMessage(ChatUtils.chat("&6&nSpending Skillpoints: "));
		sender.sendMessage(ChatUtils.chat("&3You can spend your skillpoints by leveling up abilities in the class skill trees. You can mix and match class abilities however you wish. There is no limit on multi-classing; in-fact it is encouraged. Get crazy with it!"));
		sender.sendMessage(ChatUtils.chat("&6&nSkill Trees: "));
		sender.sendMessage(ChatUtils.chat("&3You can navigate a skill tree by clicking on the icon of a skill. Inside, you will find the skills' sub-skills. In order to level up a sub-skill, you must have at least one level in its previous skill. Skills have varying costs and maximum levels."));
		sender.sendMessage(ChatUtils.chat("&6&nObtaining Skillpoints: "));
		sender.sendMessage(ChatUtils.chat("&3You can obtain skillpoints completing many typical minecraft tasks such as killing mobs, mining ores, smelting, fishing, breeding animals, and gaining achievements."));
		sender.sendMessage(ChatUtils.chat("&6&nScaling: "));
		sender.sendMessage(ChatUtils.chat("&3As you progress, your Player Skill Level will increase. As it does, your damage to mobs will be reduced very slightly per level. The change is nearly unnoticable until the later levels. Mobs also deal increased damage to you at the same scaling rate."));
		
		return true;
	}

}
