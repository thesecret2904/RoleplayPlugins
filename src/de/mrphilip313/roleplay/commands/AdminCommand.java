package de.mrphilip313.roleplay.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.enums.Adminlevel;
import de.mrphilip313.roleplay.data.saved.Players;

public class AdminCommand extends RoleplayCommand{

	public AdminCommand(){}
	
	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
		if(cmdSender instanceof Player){
			Player sender = (Player) cmdSender;
			
			switch (cmd.getName()) {
			case "ban":
				if(args != null && args.length == 2){
					PlayerInformation pInfo = Players.getPlayerEntry(sender);
					if(pInfo.getAdminlevel() > Adminlevel.USER){
						
					} else {
						sender.sendMessage(sendPlayerAdminRightsFailure("Moderator"));
					}
				} else {
					sender.sendMessage(sendPlayerSyntaxNOSB("ban", "[player] [reason]"));
				}
				return true;

			default:
				// TODO
				return true;
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
		return null;
	}

}
