package me.thutson3876.fantasyclasses.commands;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import me.thutson3876.fantasyclasses.FantasyClasses;
import me.thutson3876.fantasyclasses.commands.commandexecutors.Command_BindAbility;
import me.thutson3876.fantasyclasses.commands.commandexecutors.Command_ChooseClass;
import me.thutson3876.fantasyclasses.commands.commandexecutors.Command_Help;
import me.thutson3876.fantasyclasses.commands.commandexecutors.Command_ResetSkills;

public class CommandManager {
	private List<AbstractCommand> commands = new LinkedList<>();
	
	public CommandManager() {
		commands.add(new Command_ChooseClass());
		commands.add(new Command_BindAbility());
		commands.add(new Command_ResetSkills());
		commands.add(new Command_Help());
		
		this.registerCommands();
	}
	
	
	public void registerCommands() {
		FantasyClasses plugin = FantasyClasses.getPlugin();
		for(AbstractCommand command : this.commands) {
			plugin.getCommand(command.getCommandName()).setDescription(command.getDescription());
			plugin.getCommand(command.getCommandName()).setAliases(Arrays.asList(command.getAliases()));
			plugin.getCommand(command.getCommandName()).setTabCompleter(command);
			plugin.getCommand(command.getCommandName()).setExecutor(command);
		}
	}
}
