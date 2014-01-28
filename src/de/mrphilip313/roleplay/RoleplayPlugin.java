package de.mrphilip313.roleplay;

import java.io.File;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import de.mrphilip313.roleplay.commands.AccountCommand;
import de.mrphilip313.roleplay.commands.BlockedCommands;
import de.mrphilip313.roleplay.commands.NameCommand;
import de.mrphilip313.roleplay.commands.WarpCommand;
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
import de.mrphilip313.roleplay.core.listener.RoleplayListener;
import de.mrphilip313.roleplay.core.security.ChatlogFilter;

public class RoleplayPlugin extends JavaPlugin{
	private static final Logger log = Bukkit.getLogger();
	
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
		
//		this.database = new MySQL(this, "localhost", "3306", "ni151053_1_DB", "ni151053_1_DB", "A9zCHILP");
		this.database = new MySQL(this, "localhost", "3306", "roleplay", "root", "");
		con = this.database.openConnection();
		
		plugins = getDataFolder();
		
		this.protocolManager = ProtocolLibrary.getProtocolManager();
		this.protocolManager.addPacketListener(
				new PacketAdapter(this, PacketType.Play.Server.TAB_COMPLETE) {

					@Override
					public void onPacketSending(PacketEvent event) {
						PacketContainer packet = event.getPacket();
						for (int i = 0; i < packet.getStrings().size(); i++) {
							System.out.println(i + ": " + packet.getStrings().read(i));
						}
						super.onPacketSending(event);
					}
					
				});

		scheduler = Bukkit.getServer().getScheduler();
		//scheduler.scheduleSyncRepeatingTask(this, new PaydayHandler(), 20L, 20L);
		scheduler.scheduleSyncRepeatingTask(this, new BanHandler(), 60 * 20L, 60 * 20L);
		
		WarpManager.loadWarps();
		
//		getServer().getPluginManager().registerEvents(new PlayerJoinLeaveListener(), this);
//		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
		getServer().getPluginManager().registerEvents(new RoleplayListener(), this);
		
		getCommand("account").setExecutor(new AccountCommand());
		getCommand("warp").setExecutor(new WarpCommand());
		getCommand("name").setExecutor(new NameCommand());
		getCommand("payday").setExecutor(null);
		
		getCommand("effect").setExecutor(new BlockedCommands());
		getCommand("kill").setExecutor(new BlockedCommands());
		
		getCommand("me").setExecutor(new MeChatCommand());
		getCommand("rp").setExecutor(new RpChatCommand());
		getCommand("s").setExecutor(new SChatCommand());
		getCommand("l").setExecutor(new LChatCommand());
		getCommand("w").setExecutor(new WChatCommand());
		getCommand("o").setExecutor(new OChatCommand());
		getCommand("b").setExecutor(new BChatCommand());
		
		
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
	

}
