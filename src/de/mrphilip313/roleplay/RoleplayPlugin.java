package de.mrphilip313.roleplay;

import java.io.File;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import de.mrphilip313.roleplay.commands.AccountCommand;
import de.mrphilip313.roleplay.commands.BlockedCommands;
import de.mrphilip313.roleplay.commands.NameCommand;
import de.mrphilip313.roleplay.commands.WarpCommand;
import de.mrphilip313.roleplay.commands.admin.AonDCommand;
import de.mrphilip313.roleplay.commands.admin.BanSystem;
import de.mrphilip313.roleplay.commands.chat.BChatCommand;
import de.mrphilip313.roleplay.commands.chat.LChatCommand;
import de.mrphilip313.roleplay.commands.chat.MeChatCommand;
import de.mrphilip313.roleplay.commands.chat.OChatCommand;
import de.mrphilip313.roleplay.commands.chat.RpChatCommand;
import de.mrphilip313.roleplay.commands.chat.SChatCommand;
import de.mrphilip313.roleplay.commands.chat.WChatCommand;
import de.mrphilip313.roleplay.core.database.WarpManager;
import de.mrphilip313.roleplay.core.database.core.MySQL;
import de.mrphilip313.roleplay.core.handler.BanHandler;
import de.mrphilip313.roleplay.core.listener.MapListener;
import de.mrphilip313.roleplay.core.listener.RoleplayListener;
import de.mrphilip313.roleplay.core.security.ChatlogFilter;
import de.mrphilip313.roleplay.data.saved.MapLocations;

public class RoleplayPlugin extends JavaPlugin{
	private static final Logger log = Bukkit.getServer().getLogger();
	
	public static RoleplayPlugin plugin = null;	
	private static Connection con = null;
	
	public ProtocolManager protocolManager = null;
	
	private MySQL database = null;

	public static File plugins;
	
	private BukkitScheduler scheduler;
	
	@Override
	public void onEnable() {
		super.onEnable();
		plugin = this;
		
		getLogger().info("Plugins gestartet");
		
		this.database = new MySQL(this, "vweb09.nitrado.net", "3306", "ni151053_3sql2", "ni151053_3sql2", "9c5e3f1b");
//		this.database = new MySQL(this, "localhost", "3306", "roleplay", "root", "");
		con = this.database.openConnection();
		
		plugins = getDataFolder();
		
		this.protocolManager = ProtocolLibrary.getProtocolManager();

		scheduler = Bukkit.getServer().getScheduler();
		//scheduler.scheduleSyncRepeatingTask(this, new PaydayHandler(), 20L, 20L);
		scheduler.scheduleSyncRepeatingTask(this, new BanHandler(), 60 * 20L, 60 * 20L);
		
		WarpManager.loadWarps();
		MapLocations.load();
		
//		getServer().getPluginManager().registerEvents(new PlayerJoinLeaveListener(), this);
//		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
		getServer().getPluginManager().registerEvents(new RoleplayListener(), this);
		getServer().getPluginManager().registerEvents(new MapListener(), this);
		
		
		getCommand("account").setExecutor(new AccountCommand());
		getCommand("name").setExecutor(new NameCommand());
		getCommand("payday").setExecutor(null);
		getCommand("kill").setExecutor(new BlockedCommands());
		
		getCommand("me").setExecutor(new MeChatCommand());
		getCommand("rp").setExecutor(new RpChatCommand());
		getCommand("s").setExecutor(new SChatCommand());
		getCommand("l").setExecutor(new LChatCommand());
		getCommand("w").setExecutor(new WChatCommand());
		getCommand("o").setExecutor(new OChatCommand());
		getCommand("b").setExecutor(new BChatCommand());
		
		registerAdminCommands();
		log.setFilter(new ChatlogFilter());
		
	}
	
	
	@Override
	public void onDisable() {
		super.onDisable();
		getLogger().info("Plugin deaktiviert");
		this.database.closeConnection();
	}

	public static Connection getConnection() {
		return con;
	}
	
	public static void log(String log){
		Logger.getLogger("Roleplay").log(Level.INFO, log);
	}
	
	private void registerAdminCommands(){
		getCommand("warp").setExecutor(new WarpCommand());
		
		getCommand("aond").setExecutor(new AonDCommand());
		
		getCommand("ban").setExecutor(new BanSystem());
		getCommand("timeban").setExecutor(new BanSystem());
		getCommand("unban").setExecutor(new BanSystem());
	}
	

}
