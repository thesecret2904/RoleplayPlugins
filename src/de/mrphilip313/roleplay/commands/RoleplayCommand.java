package de.mrphilip313.roleplay.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class RoleplayCommand implements CommandExecutor{

	@Override
	public abstract boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3);
	
	public abstract String getPrefix();
	
	public abstract String getBaseCommand();
	
	public String sendPlayerCommandError(String error){
		return ChatColor.DARK_PURPLE + getPrefix() + " " + ChatColor.RED + error;
	}
	
	public String sendPlayerCommandSuccses(String succses){
		return ChatColor.DARK_PURPLE + getPrefix() + " " + ChatColor.GREEN + succses;
	}
	
	public String sendPlayerSyntax(String subcommand, String args){
		String sep = args == null ? "" : " ";
		return ChatColor.DARK_PURPLE + getPrefix() + ChatColor.GOLD + " /" + getBaseCommand() + sep + subcommand + ChatColor.GREEN + " " + args;
	}
}
