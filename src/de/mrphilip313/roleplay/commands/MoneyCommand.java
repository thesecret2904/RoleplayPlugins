package de.mrphilip313.roleplay.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.saved.Players;
import de.mrphilip313.roleplay.data.saved.Variables;

public class MoneyCommand extends RoleplayCommand {

	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
		if(cmdSender instanceof Player){
			Player player = (Player) cmdSender;
			PlayerInformation pInfo = Players.getPlayerEntry(player);
			if (pInfo.isLoggedIn()) {
				player.sendMessage(sendPlayerCommandSuccses("In deinem Portemonnaie befinden sich " + pInfo.getMoney() + Variables.WAEHRUNG + "."));
			}
			else { player.sendMessage(sendPlayerLoginNeeded());
			
			}

		}
	return true;
	}
			
			

	@Override
	public String getPrefix() {
		return "[Economy]";
	}

	@Override
	public String getBaseCommand() {
		return "money";
	}

}
