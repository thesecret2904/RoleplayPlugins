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

public class SChatCommand extends RoleplayCommand{

	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
		if(cmdSender instanceof Player){
			Player sender = (Player) cmdSender;
			if (Players.getPlayerEntry(sender.getName()).isLoggedIn()) {
				if (args != null && args.length >= 1) {
					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
						StringBuilder string = new StringBuilder();
						boolean inNear = true;
						Double distance = sender.getLocation().distance(
								player.getLocation());
						if (distance <= ChatDistances.STATE1
								* ChatDistances.S_MULTIPLIER)
							string.append(ChatColor.WHITE);
						else if (distance <= ChatDistances.STATE2
								* ChatDistances.S_MULTIPLIER)
							string.append(ChatColor.GRAY);
						else if (distance <= ChatDistances.STATE3
								* ChatDistances.S_MULTIPLIER)
							string.append(ChatColor.DARK_GRAY);
						else if (distance <= ChatDistances.STATE4
								* ChatDistances.S_MULTIPLIER)
							string.append(ChatColor.BLACK);
						else
							inNear = false;
						if (inNear) {
							string.append(Players.getRPName(sender));
							string.append(" schreit: ");
							string.append(Utils.buildString(args, 0));
							string.append("!");
							player.sendMessage(string.toString());
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
		return "/s";
	}

}
