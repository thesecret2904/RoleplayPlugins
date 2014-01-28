package de.mrphilip313.roleplay.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.commands.RoleplayCommand;
import de.mrphilip313.roleplay.data.saved.Players;
import de.mrphilip313.roleplay.data.utils.Utils;

public class WChatCommand extends RoleplayCommand{

	@Override
	public boolean onCommand(CommandSender cmdSender, Command arg1, String arg2, String[] args) {
		if(cmdSender instanceof Player){
			Player sender = (Player) cmdSender;
			if (Players.getPlayerEntry(sender.getName()).isLoggedIn()) {
				if (args != null && args.length >= 2) {
					if(Bukkit.getServer().getPlayer(args[0]) != null){
						Player receip = Bukkit.getServer().getPlayer(args[0]);
						for (Player player : Bukkit.getServer().getOnlinePlayers()) {
							if(player.getName().equals(sender.getName()) || player.getName().equals(sender.getName())){
								player.sendMessage(ChatColor.GRAY + Players.getRPName(sender) + "flüstert: " + Utils.buildString(args, 1));
							} else {
								player.sendMessage(ChatColor.GRAY + Players.getRPName(sender) + " flüster " + Players.getRPName(receip) + " etwas zu");
							}
						}
					} else {
						sender.sendMessage(ChatColor.DARK_PURPLE + "[Chat] " + ChatColor.RED + "Der Spieler ist nicht online");						
					}
				}			
			} else {
				sender.sendMessage(ChatColor.DARK_PURPLE + "[Chat] " + ChatColor.RED + "Du musst angemeldet sein");
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
