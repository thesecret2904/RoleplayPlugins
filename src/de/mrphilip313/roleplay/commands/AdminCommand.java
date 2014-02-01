package de.mrphilip313.roleplay.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.core.database.core.BaseDBFunctions;
import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.enums.Adminlevel;
import de.mrphilip313.roleplay.data.saved.Players;
import de.mrphilip313.roleplay.data.utils.Utils;

public class AdminCommand extends RoleplayCommand{

	public AdminCommand(){}
	
	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {

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
