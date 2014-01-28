package de.mrphilip313.roleplay.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockedCommands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if(arg0 instanceof Player){
			Player player = (Player)arg0;
			Bukkit.getServer().broadcastMessage(ChatColor.RED + player.getName() + " hat versucht, den bösen Befehl \"/" + arg1.getName() + "\" zu benutzen");
		}
		return false;
	}

}
