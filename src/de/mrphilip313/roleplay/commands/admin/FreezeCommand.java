package de.mrphilip313.roleplay.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.commands.RoleplayCommand;
import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.enums.Adminlevel;
import de.mrphilip313.roleplay.data.saved.Players;

public class FreezeCommand extends RoleplayCommand{

	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
		if(cmdSender instanceof Player){
			Player sender = (Player) cmdSender;
			PlayerInformation pInfo = Players.getPlayerEntry(sender);
			if(pInfo.getAdminlevel() > Adminlevel.USER){
				if(pInfo.isAdminOnDuty()){
					if(args != null && args.length == 1){
						if(Bukkit.getServer().getPlayer(args[0]) != null){
							Player player = Bukkit.getServer().getPlayer(args[0]);
							String username = player.getName();
							if(Players.isFreezed(username)){
								Players.setUnfreezed(username);
								player.sendMessage(sendPlayerCommandSuccses("Du wurdest von " + sender.getName() + " unfreezed."));
							} else {
								Players.setFreezed(username);
								player.sendMessage(sendPlayerCommandSuccses("Du wurdest von " + sender.getName() + " gefreezed."));								
							}
						} else {
							sender.sendMessage(sendPlayerCommandError("Der Spieler ist nicht online!"));
						}
					} else {
						sender.sendMessage(sendPlayerSyntaxNOSB(getBaseCommand(), "[Spieler]"));
					}
				} else {
					sender.sendMessage(sendPlayerCommandError("Du musst dafür OnDuty sein!"));
				}
			} else {
				sender.sendMessage(sendPlayerAdminRightsFailure(Adminlevel.MODERATOR));
			}
		}
		return true;
	}

	@Override
	public String getPrefix() {
		return "[Freeze]";
	}

	@Override
	public String getBaseCommand() {
		return "freeze";
	}

}
