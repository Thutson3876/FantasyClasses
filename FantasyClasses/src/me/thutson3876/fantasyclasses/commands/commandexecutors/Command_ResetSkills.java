package me.thutson3876.fantasyclasses.commands.commandexecutors;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.fantasyclasses.commands.AbstractCommand;
import me.thutson3876.fantasyclasses.playermanagement.FantasyPlayer;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public class Command_ResetSkills extends AbstractCommand implements Listener {
	
	private static final int COST = 1;
	
	public Command_ResetSkills() {
		super("resetskills", new String[] { "rskills" });
	}

	@Override
	protected boolean onInternalCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatUtils.chat("&4Error: Must be a player to use this command"));
			return true;
		}
		if (args.length != 0) {
			sender.sendMessage(ChatUtils.chat("&4Error: Improper use of command"));
			return false;
		}
		
		Player player = (Player) sender;
		
		ItemStack item = player.getInventory().getItemInMainHand();
		FantasyPlayer fplayer = plugin.getPlayerManager().getPlayer(player);
		if(fplayer == null) {
			sender.sendMessage(ChatUtils.chat("&4Error: Must be a player to use this command"));
			return false;
		}
		
		int cost = COST * (fplayer.getPlayerLevel() / 5);
		if(item != null && item.getType().equals(Material.DIAMOND)) {
			if(item.getAmount() >= cost) {
				item.setAmount(item.getAmount() - cost);
				player.getInventory().setItemInMainHand(item);
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1.2f, 1.2f);
				fplayer.resetAllAbilities();
				player.sendMessage(ChatUtils.chat("&5Successfully reset skills!"));
				return true;
			}
		}
		
		sender.sendMessage(ChatUtils.chat("&4Error: Must pay a diamond to reset per 5 player levels. Hold the diamonds in main hand."));
		sender.sendMessage(ChatUtils.chat("&3Current Cost: &6" + cost + " diamonds"));
		
		
		return true;
	}
}
