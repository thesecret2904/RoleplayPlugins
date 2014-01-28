package de.mrphilip313.roleplay.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.commands.RoleplayCommand;
import de.mrphilip313.roleplay.data.saved.ChatDistances;
import de.mrphilip313.roleplay.data.saved.Players;
import de.mrphilip313.roleplay.data.utils.Utils;

public class BChatCommand extends RoleplayCommand {

	@Override
	public boolean onCommand(CommandSender cmdSender, Command arg1, String arg2, String[] args) {
		if(cmdSender instanceof Player){
			Player sender = (Player) cmdSender;
			if (Players.getPlayerEntry(sender.getName()).isLoggedIn()) {
				if (args != null && args.length >= 1) {
					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
						if(player.getLocation().distance(sender.getLocation()) <= ChatDistances.STATE4){
							player.sendMessage(ChatColor.GRAY + "( " + Players.getRPName(sender) + ": " + Utils.buildString(args, 0) + " )");						
						}
					}
				}				
			} else {
				cmdSender.sendMessage(ChatColor.DARK_PURPLE + "[Chat] " + ChatColor.RED + "Du musst angemeldet sein");
			}
		}
		return true;
	}
	
	@Override
	public String getPrefix() {
		return "[Chat]";
	}

	@Override
	public String getBaseCommand() {
		return "";
	}

}
