package de.mrphilip313.roleplay.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.RoleplayPlugin;

public class AdminCommand implements CommandExecutor{
	private RoleplayPlugin plugin;
	
	public AdminCommand(){
	}
	
	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
		if(cmdSender instanceof Player){
			
		}
		
		if(cmd.getName().equals("admin")){
			// TODO /admnis
		} else {
		}
		return false;
	}

}
