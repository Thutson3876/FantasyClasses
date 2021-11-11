package me.thutson3876.fantasyclasses.gui;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public class SkillTreeGUI extends BasicGUI implements Listener {

	private static ItemStack levelUpItem;

	Skill skill;

	public SkillTreeGUI(Player p, String title, Skill skill, AbstractGUI back, List<GuiItem> items) {
		super(p, title, 27, null, back, items);
		this.skill = skill;

		ItemStack item = new ItemStack(Material.RED_MUSHROOM);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatUtils.chat("&3Level Up!"));
		item.setItemMeta(meta);
		levelUpItem = item;
	}

	@Override
	public void initializeItems() {
		List<GuiItem> itemList = this.items;

		int listSize = itemList.size();
		if (this.back != null) {
			listSize--;
		}
		if (this.forward != null) {
			listSize--;
		}
		
		boolean isEven = (listSize % 2) == 0;
		int start;
		if(isEven) {
			start = 8 + (5 - listSize + (listSize / 2));
		}
		else {
			start = 9 + (5 - listSize + (listSize / 2));
		}
		
		for (GuiItem item : itemList) {
			if (item.equals(this.back))
				continue;
			inv.setItem(start, item.getItem());
			Skill itemSkill = skill.getMatchingSkill(item.getItem());
			Skill prev = itemSkill.getPrev();
			if ((prev == null || prev.getAbility().getCurrentLevel() > 0) && itemSkill.getAbility().getCurrentLevel() < itemSkill.getAbility().getMaxLevel()) {
				inv.setItem(start + 9, levelUpItem);
			}
			else if(inv.getItem(start + 9) == null || inv.getItem(start + 9).equals(levelUpItem))
				inv.setItem(start + 9, getDefaultFiller());
				
			
			if(item.getLinkedInventory() != null)
				item.getLinkedInventory().initializeItems();
			start++;
			if(start == 13 && isEven)
				start++;
		}
	}

	@Override
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		Inventory inventory = e.getInventory();
		if (inventory.equals(inv)) {
			e.setCancelled(true);
		} else {
			return;
		}
		final ItemStack clickedItem = e.getCurrentItem();

		if (clickedItem == null || clickedItem.getType().isAir())
			return;

		final Player p = (Player) e.getWhoClicked();
		p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 0.4F, 1F);

		for (GuiItem item : items) {
			if (clickedItem.equals(item.getItem())) {
				if (item.getLinkedInventory() == null)
					break;
				p.openInventory(item.getLinkedInventory().inv);
				break;
			}
		}

		if (!clickedItem.isSimilar(levelUpItem)) {
			return;
		}
		ItemStack item = inventory.getItem(e.getSlot() - 9);
		if (item == null) {
			return;
		}

		Ability abil = this.skill.getMatchingAbility(item);

		if (abil == null)
			return;

		if (abil.getCurrentLevel() >= abil.getMaxLevel()) {
			this.player.getPlayer().sendMessage(ChatUtils.chat("&4Error: Maximum Level has been reached"));
			return;
		}

		if (this.player.spendSkillPoints(abil.getSkillPointCost())) {
			List<Ability> abilities = this.player.getAbilities();
			if (abilities.contains(abil)) {
				this.player.removeAbility(abil);
			}

			abil.levelUp();
			this.player.addAbility(abil);
			this.skill.replaceSkillAbility(abil);

			ItemStack newItem = abil.getItemStack();
			for (GuiItem guiItem : this.items) {
				if (item.equals(guiItem.getItem())) {
					guiItem.setItem(newItem);
					break;
				}
			}

			this.inv.setItem(e.getSlot() - 9, newItem);
			refresh();
		} else {
			e.getWhoClicked().sendMessage(ChatUtils.chat("&4Error: Not enough skillpoints"));
		}

		
	}
}
