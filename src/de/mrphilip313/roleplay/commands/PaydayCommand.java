package de.mrphilip313.roleplay.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.saved.Players;

public class PaydayCommand extends RoleplayCommand {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player){
			Player player = (Player) arg0;
			PlayerInformation pInfo = Players.getPlayerEntry(player.getName());
			if(pInfo.isLoggedIn()){
				player.sendMessage(ChatColor.DARK_PURPLE + getPrefix() + ChatColor.AQUA + " Du hast in " + pInfo.getPayday() + " Sekunden Payday");
			} else {
				player.sendMessage(sendPlayerCommandError("Du musst dafür angemeldet sein"));
			}
		}
		
		return true;
	}

	@Override
	public String getPrefix() {
		return "[Payday]";
	}

	@Override
	public String getBaseCommand() {
		return "payday";
	}

}
