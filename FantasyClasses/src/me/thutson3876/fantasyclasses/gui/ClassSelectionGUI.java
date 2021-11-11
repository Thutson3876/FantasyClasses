package me.thutson3876.fantasyclasses.gui;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.thutson3876.fantasyclasses.classes.AbstractFantasyClass;

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

		fillGaps(createGuiItem(null, Material.BLACK_STAINED_GLASS_PANE, " ").getItem());
	}

}
