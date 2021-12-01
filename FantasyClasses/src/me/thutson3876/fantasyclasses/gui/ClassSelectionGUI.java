package me.thutson3876.fantasyclasses.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public class ClassSelectionGUI extends AbstractGUI {
	
	public ClassSelectionGUI(Player p) {
		super(p, "Choose a Class", 27, null, null);
	}

	@Override
	public void initializeItems() {
		List<AbstractFantasyClass> classes = player.getFantasyClasses();
		for(int i = 0; i < classes.size(); i++) {
			GuiItem temp = player.getFantasyClasses().get(i).asGuiItem(this);
			items.add(temp);
			inv.setItem(10 + i, temp.getItem());
		}
		for(int i = 0; i < 9; i++) {
			inv.setItem(i, getDefaultFiller());
		}
		generateExpBar();

		fillGaps(createGuiItem(null, Material.BLACK_STAINED_GLASS_PANE, " ").getItem());
	}

	private void generateExpBar() {
		long currentExp = player.getSkillExp() - player.calculateCurrentLevelExpCost();
		long neededExp = player.calculateNextLevelExpCost() - player.calculateCurrentLevelExpCost();
		int progressPercent = (int) Math.round(((double) currentExp / neededExp) * 10) - 1;
		List<String> lore = new ArrayList<>();
		lore.add(ChatUtils.chat("Exp: &6" + currentExp + " &r/ &6" + neededExp));
		
		ItemStack item = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatUtils.chat("&3Player Level: &6" + player.getPlayerLevel()));
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		for(int i = 0; i < 9; i++) {
			if(i > progressPercent) {
				item.setType(Material.STONE);
			}
			
			inv.setItem(i, item);
		}
	}
}
