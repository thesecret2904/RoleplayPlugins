package de.mrphilip313.roleplay.core.database.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.mrphilip313.roleplay.RoleplayPlugin;
import de.mrphilip313.roleplay.core.security.HashAlgorithm;
import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.enums.Fraktion;
import de.mrphilip313.roleplay.data.enums.Jobs;
import de.mrphilip313.roleplay.data.saved.Locations;
import de.mrphilip313.roleplay.data.saved.Players;
import de.mrphilip313.roleplay.data.utils.InventorySerializer;

public class BaseDBFunctions {
	public static String STATEMENT_INSERT_USER_BASE = "INSERT INTO user_base (username, pos_x, pos_y, pos_z, pos_yaw, pos_pitch, level, exp, rpname) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE pos_x=VALUES(pos_x), pos_y=VALUES(pos_y), pos_z=VALUES(pos_z), pos_yaw=VALUES(pos_yaw), pos_pitch=VALUES(pos_pitch), level=VALUES(level), exp=VALUES(exp), rpname=VALUES(rpname)";
	public static String STATEMENT_INSERT_USER_CONTENT = "INSERT INTO user_content (username, job, fraktion, isLeader, adminLevel, money, moneyBank, payday) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE job=VALUES(job), fraktion=VALUES(fraktion), isLeader=VALUES(isLeader), adminLevel=VALUES(adminLevel), money=VALUES(money), moneyBank=VALUES(moneyBank), payday=VALUES(payday)";
	public static String STATEMENT_INSERT_INVENTORY = "INSERT INTO inventory (username, content, armor, ender) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE content=VALUES(content), armor=VALUES(armor), ender=VALUES(ender)";
	public static String STATEMENT_INSERT_BANNED = "INSERT INTO banned (username, banned, permanent, reason, banner, time) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE banned=VALUES(banned), permanent=VALUES(permanent), reason=VALUES(reason), banner=VALUES(banner), time=VALUES(time)";
	public static String STATEMENT_INSERT_WARP = "INSERT INTO warp (name, pos_x, pos_y, pos_z, pos_yaw, pos_pitch) VALUES (?, ?, ?, ?, ?, ?);";

	public static void loadPlayer(Player player){
		ResultSet result;
		String query;
		
		PlayerInformation pInfo = new PlayerInformation();
		String name = player.getName();


		try {
			query = "SELECT pos_x, pos_y, pos_z, pos_yaw, pos_pitch, level, exp, rpname FROM user_base WHERE username='" + name + "';";
			result = executeQuery(query);
			if(result.next()){
				pInfo.setUsername(name);
				pInfo.setLevel(result.getInt("level"));
				pInfo.setExp(result.getFloat("exp"));
				
				Location location = player.getLocation();
				location.setX(result.getDouble("pos_x"));
				location.setY(result.getDouble("pos_y"));
				location.setZ(result.getDouble("pos_z"));
				location.setYaw(result.getFloat("pos_yaw"));
				location.setPitch(result.getFloat("pos_pitch"));
				
				Players.rpnames.put(name, result.getString("rpname"));
				
				pInfo.setLocation(location);				
			}
			else
			{
				player.kickPlayer("Fehler beim auslesen der Daten 1");
				return;
			}
			
			query = "SELECT job, fraktion, isLeader, adminLevel, money, moneyBank, payday FROM user_content WHERE username='" + name + "';";
			result = executeQuery(query);
			if(result.next()){
				pInfo.setJob(Jobs.values()[result.getInt("job")]);
				pInfo.setFraktion(Fraktion.values()[result.getInt("fraktion")]);
				pInfo.setFrakLeader(result.getBoolean("isLeader"));
				pInfo.setAdminlevel(result.getInt("adminLevel"));
				pInfo.setMoney(result.getDouble("money"));
				pInfo.setMoneyInBank(result.getDouble("moneyBank"));
				pInfo.setPayday(result.getInt("payday"));
			}
			else
			{
				player.kickPlayer("Fehler beim auslesen der Daten 2");
				return;
			}
			
			query = "SELECT banned, permanent, reason, banner, time FROM banned WHERE username='" + name + "';";
			result = executeQuery(query);
			if(result.next()){
				pInfo.setBanned(result.getBoolean("banned"));
				pInfo.setPermBanned(result.getBoolean("permanent"));
				pInfo.setBanReason(result.getString("reason"));
				pInfo.setBanner(result.getString("banner"));
				pInfo.setTimeBanned(result.getInt("time"));
			}
			else
			{
				player.kickPlayer("Fehler beim auslesen der Daten 3");
				return;
			}
			
			query = "SELECT content, armor, ender FROM inventory WHERE username='" + name + "';";
			result = executeQuery(query);
			if(result.next()){
				ItemStack[] inventory = InventorySerializer.unpackInventory(result.getString("content"));
				ItemStack[] armor = InventorySerializer.unpackInventory(result.getString("armor"));
				ItemStack[] ender = InventorySerializer.unpackInventory(result.getString("ender"));
				
				pInfo.setInventory(inventory);
				pInfo.setArmor(armor);
				pInfo.setEnder(ender);
				
				player.getInventory().setContents(inventory);
				player.getInventory().setArmorContents(armor);
				player.getEnderChest().setContents(ender);
			}
			else
			{
				player.kickPlayer("Fehler beim auslesen der Daten 4");
				return;
			}
			pInfo.setLoggedIn(true);
			Players.addPlayerEntry(name, pInfo);
			Players.setUnfreezed(name);
		} catch (SQLException e) {
			e.printStackTrace();
			player.kickPlayer("Fehler beim auslesen der Daten");
		}
	}
	
	public static void savePlayer(Player player){
		String name = player.getName();
		PlayerInformation pInfo = Players.getPlayerEntry(name);
		
		if(pInfo == null){
			return;
		}
		PreparedStatement statement;
		
		Location location;
		if(!pInfo.isAdminOnDuty())
			location = player.getLocation();
		else
			location = pInfo.getLocation();	

		try {
			if (pInfo.isLoggedIn()) {
				statement = RoleplayPlugin.getConnection().prepareStatement(STATEMENT_INSERT_USER_BASE);
				statement.setString(1, name);
				statement.setDouble(2, location.getX());
				statement.setDouble(3, location.getY());
				statement.setDouble(4, location.getZ());
				statement.setFloat(5, location.getYaw());
				statement.setFloat(6, location.getPitch());
				statement.setInt(7, player.getLevel());
				statement.setFloat(8, player.getExp());
				statement.setString(9, Players.rpnames.get(name));
				statement.execute();
				
				statement = RoleplayPlugin.getConnection().prepareStatement(STATEMENT_INSERT_USER_CONTENT);
				statement.setString(1, name);
				statement.setInt(2, pInfo.getJob().ordinal());
				statement.setInt(3, pInfo.getFraktion().ordinal());
				statement.setBoolean(4, pInfo.isFrakLeader());
				statement.setInt(5, pInfo.getAdminlevel());
				statement.setDouble(6, pInfo.getMoney());
				statement.setDouble(7, pInfo.getMoneyInBank());
				statement.setInt(8, pInfo.getPayday());
				statement.execute();
				
				statement = RoleplayPlugin.getConnection().prepareStatement(STATEMENT_INSERT_INVENTORY);
				statement.setString(1, name);
				if (pInfo.isAdminOnDuty()) {
					statement.setString(2, InventorySerializer.packInventory(pInfo.getInventory()));
					statement.setString(3, InventorySerializer.packInventory(pInfo.getArmor()));
					statement.setString(4, InventorySerializer.packInventory(pInfo.getEnder()));
				} else {
					statement.setString(2, InventorySerializer.packInventory(player.getInventory().getContents()));
					statement.setString(3, InventorySerializer.packInventory(player.getInventory().getArmorContents()));
					statement.setString(4, InventorySerializer.packInventory(player.getEnderChest().getContents()));
				}
				statement.execute();
				
				statement = RoleplayPlugin.getConnection().prepareStatement(STATEMENT_INSERT_BANNED);
				statement.setString(1, name);
				statement.setBoolean(2, pInfo.isBanned());
				statement.setBoolean(3, pInfo.isPermBanned());
				statement.setString(4, pInfo.getBanReason());
				statement.setString(5, pInfo.getBanner());
				statement.setInt(6, pInfo.getTimeBanned());
				statement.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void registerPlayer(Player player, String password){
		try {
			PreparedStatement statement = RoleplayPlugin.getConnection().prepareStatement("INSERT INTO phpbb_users (username, username_clean, user_password, user_permissions, user_sig, user_occ, user_interests) VALUES (?, ?, ?, ?, ?, ?, ?)");
			statement.setString(1, player.getName());
			statement.setString(2, player.getName().toLowerCase());
			statement.setString(3, HashAlgorithm.doubleSHAHash(password));
			statement.setString(4, "");
			statement.setString(5, "");
			statement.setString(6, "");
			statement.setString(7, "");
			statement.execute();
			
			int uID = 0;
			String query = "SELECT user_id FROM phpbb_users WHERE username='" + player.getName() + "';";
			ResultSet result = executeQuery(query);
			if(result.next()){
				uID = result.getInt("user_id");
			}
			
			statement = RoleplayPlugin.getConnection().prepareStatement("INSERT INTO phpbb_user_group (user_id, group_id) VALUES (?, ?)");
			statement.setInt(1, uID);
			statement.setInt(2, 10);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isBanned(String username){
		try {
			ResultSet result = executeQuery("SELECT banned FROM banned WHERE username='" + username + "';");
			if(result.next()){
				return result.getBoolean("banned");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isPasswordCorrect(String username, String password){
		try {
			ResultSet result = executeQuery("SELECT user_password FROM phpbb_users WHERE username='" + username + "';");
			if(result.next()){
				if(result.getString("user_password").equals(HashAlgorithm.doubleSHAHash(password))) return true;
				else return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isUserRegistered(String username){
		ResultSet result = executeQuery("SELECT username FROM phpbb_users WHERE username='" + username + "';");
		try {
			if(result.next() && result.getString("username").equals(username))
				return true;
		} catch (SQLException e) {
		}
		return false;
	}
	
	public static boolean doesRPNameExist(String rpname){
		ResultSet result = executeQuery("SELECT rpname FROM user_base WHERE rpname='" + rpname + "';");
		try {
			if(result.next() && result.getString("rpname").equals(rpname))
				return true;
		} catch (SQLException e) {
		}
		return false;
	}
	
	
	
	public static void writeWarp(String name, Location location){
		try {
			PreparedStatement statement = RoleplayPlugin.getConnection().prepareStatement(STATEMENT_INSERT_WARP);
			statement.setString(1, name);
			statement.setDouble(2, location.getX());
			statement.setDouble(3, location.getY());
			statement.setDouble(4, location.getZ());
			statement.setFloat(5, location.getYaw());
			statement.setFloat(6, location.getPitch());
			executePreparedStatement(statement);
		} catch (SQLException e) {
			System.out.println(888);
			e.printStackTrace();
		}
	}
	
	public static void deleteWarp(String name){
		executeUpdate("DELETE FROM warp WHERE name='" + name + "';");
	}
	
	public static void loadWarps(){
		ResultSet result = executeQuery("SELECT * FROM warp");
		try {
			while (result.next()) {
				Location location = new Location(Locations.getWorld(), result.getDouble("pos_x"), result.getDouble("pos_y"), result.getDouble("pos_z"), result.getFloat("pos_yaw"), result.getFloat("pos_pitch"));
				Locations.addWarp(result.getString("name"), location);
			}
		} catch (SQLException e) {
			System.out.println(87565);
			e.printStackTrace();
		}
	}
	
	public static boolean doesUsernameExist(String username){
		ResultSet result = executeQuery("SELECT username FROM user_base WHERE username='" + username + "';");
		try {
			if(result != null && result.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void banUser(String user, String banner, String reason){
		banUser(user, banner, reason, true, 0);
	}
	
	public static void banUser(String user, String banner, String reason, int time){
		banUser(user, banner, reason, false, time);
	}
	
	public static void banUser(String user, String banner, String reason, boolean isPerm, int time){
		try {
			PreparedStatement statement = RoleplayPlugin.getConnection().prepareStatement(STATEMENT_INSERT_BANNED);
			statement.setString(1, user);
			statement.setBoolean(2, true);
			statement.setBoolean(3, isPerm);
			statement.setString(4, reason);
			statement.setString(5, banner);
			statement.setInt(6, time);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void unBanUser(String user){
		try {
			PreparedStatement statement = RoleplayPlugin.getConnection().prepareStatement(STATEMENT_INSERT_BANNED);
			statement.setString(1, user);
			statement.setBoolean(2, false);
			statement.setBoolean(3, false);
			statement.setString(4, "");
			statement.setString(5, "");
			statement.setInt(6, 0);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public static void executePreparedStatement(PreparedStatement statement){
		try {
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(867);
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeQuery(String query){
		ResultSet result = null;
		try {
			result = RoleplayPlugin.getConnection().createStatement().executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void executeUpdate(String query){
		try {
			RoleplayPlugin.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

