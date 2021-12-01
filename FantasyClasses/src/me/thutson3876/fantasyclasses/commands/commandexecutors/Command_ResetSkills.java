package me.thutson3876.fantasyclasses.commands.commandexecutors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private static Map<Material, Integer> costs = new HashMap<>();
	
	static {
		costs.put(Material.DIAMOND, 1);
		costs.put(Material.BLAZE_ROD, 6);
		costs.put(Material.ENDER_PEARL, 2);
		costs.put(Material.IRON_BLOCK, 2);
		costs.put(Material.GOLD_BLOCK, 1);
		costs.put(Material.REDSTONE_BLOCK, 2);
		costs.put(Material.SADDLE, 1);
		costs.put(Material.GOLDEN_CARROT, 3);
		costs.put(Material.GLISTERING_MELON_SLICE, 3);
		costs.put(Material.GOLDEN_APPLE, 1);
		costs.put(Material.PRISMARINE_CRYSTALS, 4);
		costs.put(Material.BELL, 1);
	}
	
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
				
		Integer cost = costs.get(item.getType());
		if(cost != null) {
			cost *= (fplayer.getPlayerLevel() / 5);
			if(item.getAmount() >= cost) {
				item.setAmount(item.getAmount() - cost);
				player.getInventory().setItemInMainHand(item);
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1.0f, 1.2f);
				fplayer.resetAllAbilities();
				return true;
			}
			
		}
		
		sender.sendMessage(ChatUtils.chat("&4Error: Must pay for the cost to reset per 5 player levels. Hold out item type in main hand in an amount equal to the presented cost. Payment options shown below:"));
		List<Material> keyList = new ArrayList<>(costs.keySet());
		for(int i = 0; i < keyList.size(); i++) {
			sender.sendMessage(ChatUtils.chat("&6" + keyList.get(i).name() + "&5: &6" + (costs.get(keyList.get(i)) * (fplayer.getPlayerLevel() / 5))));
		}
		
		
		return true;
	}
}
