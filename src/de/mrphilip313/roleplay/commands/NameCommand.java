package de.mrphilip313.roleplay.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.data.saved.Players;

public class NameCommand extends RoleplayCommand{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player){
			Player sender = (Player) arg0;
			if(arg3.length == 1){
				Player target = Bukkit.getServer().getPlayer(arg3[0]);
				if(target != null){
					sender.sendMessage("-----------------------------------");
					sender.sendMessage("Spielername: " + target.getName());
					sender.sendMessage("RP-Name: " + Players.getRPName(target));
					sender.sendMessage("-----------------------------------");
				} else {
					sender.sendMessage(sendPlayerCommandError("Der Spieler ist nicht online"));
				}
			}
		}
		return true;
	}

	@Override
	public String getPrefix() {
		return "[Name]";
	}

	@Override
	public String getBaseCommand() {
		return "";
	}

}
