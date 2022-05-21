package me.thutson3876.fantasyclasses.gui.treegui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.thutson3876.fantasyclasses.abilities.Ability;
import me.thutson3876.fantasyclasses.abilities.skills.Skill;
import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;
import me.thutson3876.fantasyclasses.gui.AbstractGUI;
import me.thutson3876.fantasyclasses.gui.GuiItem;
import me.thutson3876.fantasyclasses.util.ChatUtils;

public class ClassGUI extends AbstractGUI {

	AbstractFantasyClass clazz;
	private static final ItemStack BUFFER_ITEM;

	static {
		ItemStack buffer = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		ItemMeta meta = buffer.getItemMeta();
		meta.setDisplayName(" ");
		buffer.setItemMeta(meta);

		BUFFER_ITEM = buffer;
	}

	public ClassGUI(Player p, AbstractGUI back, AbstractFantasyClass clazz) {
		super(p, clazz.getName(), 54, null, back);
		this.clazz = clazz;
		setMapInInventory();
		this.defaultFillGaps(Material.BLACK_STAINED_GLASS_PANE);
	}

	@Override
	protected void initializeItems() {
		// setTree(clazz.getSkillTree(), 0, 4);
		setMapInInventory();
		this.defaultFillGaps(Material.BLACK_STAINED_GLASS_PANE);
	}
	
	public void setMapInInventory() {
		Map<Integer, Skill> skillMap = clazz.getSkillMap();
		List<GuiItem> items = new ArrayList<>();
		for(Entry<Integer, Skill> entry : skillMap.entrySet()) {
			Skill s = entry.getValue();
			if(s == null)
				getInv().setItem(entry.getKey(), ClassGUI.getBufferItem());
			else {
				GuiItem guiItem = s.asGuiItem(null);
				items.add(guiItem);
				getInv().setItem(entry.getKey(), guiItem.getItem());
			}
		}
		items.add(this.back);
		this.setJustItems(items);
	}

	public static ItemStack getBufferItem() {
		return BUFFER_ITEM;
	}

	/*
	 * private void setTree(Skill skill, int depth, int columnSize) {
	 * if(skill.getNext().size() == 0) { setNode(skill, depth,columnSize); } else {
	 * //inv.setItem((depth + 1) * 9, bufferItem()); setNode(skill, depth,
	 * columnSize);
	 * 
	 * setTree(skill.getNext().get(0), depth + 1, columnSize);
	 * 
	 * for(int i = 1; i < skill.getNext().size(); i++) {
	 * setTree(skill.getNext().get(i), depth + 1, columnSize); } } }
	 * 
	 * private void setNode(Skill skill, int depth, int columnSize) { GuiItem item =
	 * skill.asGuiItem(null); items.add(item); inv.setItem(depth * 9 + columnSize,
	 * item.getItem()); }
	 */

	@Override
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		Inventory inventory = e.getInventory();
		if (inventory.equals(getInv())) {
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
				if (item.equals(back)) {
					p.openInventory(back.getLinkedInventory().getInv());
					return;
				}
				Skill skill = clazz.getSkillTree().getMatchingSkill(item.getItem());
				if(skill == null)
					return;
				if(skill.getPrev() != null) {
					Ability a = skill.getPrev().getAbility();
					if (a.getCurrentLevel() < 1) {
						this.player.getPlayer().sendMessage(
								ChatUtils.chat("&4Error: You need at least one skillpoint in the prerequisite skill: &6"
										+ a.getCommandName()));
						return;
					}
				}
				
				Ability abil = skill.getAbility();

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
					clazz.getSkillTree().replaceSkillAbility(abil);

					ItemStack newItem = abil.getItemStack();
					for (GuiItem guiItem : this.items) {
						if (item.getItem().equals(guiItem.getItem())) {
							guiItem.setItem(newItem);
							break;
						}
					}

					this.getInv().setItem(e.getSlot(), newItem);
					refresh();
				} else {
					e.getWhoClicked().sendMessage(ChatUtils.chat("&4Error: Not enough skillpoints"));
				}
				break;
			}
		}
	}
}
