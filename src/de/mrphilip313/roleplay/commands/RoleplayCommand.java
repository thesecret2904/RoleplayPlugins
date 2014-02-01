package de.mrphilip313.roleplay.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class RoleplayCommand implements CommandExecutor{

	@Override
	public abstract boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args);
	
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
	
	public String sendPlayerSyntax(String command, String subcommand, String args){
		String sep = args == null ? "" : " ";
		return ChatColor.DARK_PURPLE + getPrefix() + ChatColor.RED + " Syntax: " + ChatColor.GOLD + " /" + command + sep + subcommand + ChatColor.GREEN + " " + args;
	}
	
	public String sendPlayerSyntaxNOSB(String command, String args){
		return ChatColor.DARK_PURPLE + getPrefix() + ChatColor.RED + " Syntax: " + ChatColor.GOLD + " /" + command + ChatColor.GREEN + " " + args;
	}
	
	public String sendPlayerAdminRightsFailure(String rangname){
		return ChatColor.DARK_PURPLE + getPrefix() + " " + ChatColor.RED + "Dein Adminrang ist zu niedrig. Benötigter Rang: " + ChatColor.GOLD + ChatColor.BOLD + rangname;
	}
}
