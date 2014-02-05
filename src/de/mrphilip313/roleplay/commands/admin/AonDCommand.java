package de.mrphilip313.roleplay.commands.admin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.commands.RoleplayCommand;
import de.mrphilip313.roleplay.core.chat.ChatUtils;
import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.enums.Adminlevel;
import de.mrphilip313.roleplay.data.saved.Players;

public class AonDCommand extends RoleplayCommand{

	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
		if(cmdSender instanceof Player){
			Player player = (Player) cmdSender;
			PlayerInformation pInfo = Players.getPlayerEntry(player);
			if (pInfo.isLoggedIn()) {
				if (pInfo.getAdminlevel() > Adminlevel.USER) {
					if (pInfo.isAdminOnDuty()) {
						pInfo.setAdminOnDuty(false);
						ChatUtils.sendMessageToPlayers(ChatColor.GREEN + player.getName() + " hat sich als " + Adminlevel.getName(pInfo.getAdminlevel()) + " abgemeldet.");
					} else {
						pInfo.setAdminOnDuty(true);
						ChatUtils.sendMessageToPlayers(ChatColor.GREEN + player.getName() + " hat sich als " + Adminlevel.getName(pInfo.getAdminlevel()) + " angemeldet.");
					}
				} else {
					player.sendMessage(sendPlayerAdminRightsFailure(Adminlevel.MODERATOR));
				}
			} else {
				player.sendMessage(sendPlayerCommandError("Du musst dafür angemeldet sein"));
			}
		}
		return true;
	}

	@Override
	public String getPrefix() {
		return "[Admin]";
	}

	@Override
	public String getBaseCommand() {
		return "aond";
	}

}
