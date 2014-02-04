package de.mrphilip313.roleplay.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.commands.RoleplayCommand;
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

					}
				} else {
					player.sendMessage(sendPlayerAdminRightsFailure(Adminlevel.MODERATOR_N));
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
