package de.mrphilip313.roleplay.data;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import de.mrphilip313.roleplay.data.enums.Adminlevel;
import de.mrphilip313.roleplay.data.enums.Fraktion;
import de.mrphilip313.roleplay.data.enums.Jobs;
import de.mrphilip313.roleplay.data.saved.Locations;

public class PlayerInformation{
	// Allgemein
	private String username;
	private Location Location = Locations.gameSpawn;
	private boolean isLoggedIn = false;
	private int payday = 3600;
	
	// EXP
	private int level = 0;
	private float exp = 0.0F;
	
	// Money
	private Double money = 0.0;
	private Double moneyInBank = 0.0;
	
	// Inventorys
	private ItemStack[] inventory;
	private ItemStack[] armor;
	private ItemStack[] ender;
	
	// Bannsystem
	private boolean isBanned = false;
	private boolean isPermBanned = false;
	private String banner = "";
	private String banReason = "";
	private int timeBanned = 0;
	
	// Jobs
	private Jobs job = Jobs.ARBEITSLOS;
	
	// Fraktion
	private Fraktion fraktion = Fraktion.ZIVILIST;
	private boolean isFrakOnDuty = false;
	private boolean isFrakLeader = false;

	// Admin
	private int adminlevel = Adminlevel.USER;
	private boolean isAdminOnDuty = false;
	
	public PlayerInformation(){}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public boolean isPermBanned() {
		return isPermBanned;
	}

	public void setPermBanned(boolean isPermBanned) {
		this.isPermBanned = isPermBanned;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getBanReason() {
		return banReason;
	}

	public void setBanReason(String banReason) {
		this.banReason = banReason;
	}

	public int getTimeBanned() {
		return timeBanned;
	}

	public void setTimeBanned(int timeBanned) {
		this.timeBanned = timeBanned;
	}

	public Jobs getJob() {
		return job;
	}

	public void setJob(Jobs job) {
		this.job = job;
	}

	public Fraktion getFraktion() {
		return fraktion;
	}

	public void setFraktion(Fraktion fraktion) {
		this.fraktion = fraktion;
	}

	public boolean isFrakOnDuty() {
		return isFrakOnDuty;
	}

	public void setFrakOnDuty(boolean isFrakOnDuty) {
		this.isFrakOnDuty = isFrakOnDuty;
	}

	public boolean isFrakLeader() {
		return isFrakLeader;
	}

	public void setFrakLeader(boolean isFrakLeader) {
		this.isFrakLeader = isFrakLeader;
	}

	public int getAdminlevel() {
		return adminlevel;
	}

	public void setAdminlevel(int adminlevel) {
		this.adminlevel = adminlevel;
	}

	public boolean isAdminOnDuty() {
		return isAdminOnDuty;
	}

	public void setAdminOnDuty(boolean isAdminOnDuty) {
		this.isAdminOnDuty = isAdminOnDuty;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public float getExp() {
		return exp;
	}

	public void setExp(float exp) {
		this.exp = exp;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getMoneyInBank() {
		return moneyInBank;
	}

	public void setMoneyInBank(Double moneyInBank) {
		this.moneyInBank = moneyInBank;
	}

	public ItemStack[] getInventory() {
		return inventory;
	}

	public void setInventory(ItemStack[] inventory) {
		this.inventory = inventory;
	}

	public ItemStack[] getArmor() {
		return armor;
	}

	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}

	public ItemStack[] getEnder() {
		return ender;
	}

	public void setEnder(ItemStack[] ender) {
		this.ender = ender;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public Location getLocation() {
		return Location;
	}

	public void setLocation(Location location) {
		Location = location;
	}

	public int getPayday() {
		return payday;
	}

	public void setPayday(int payday) {
		this.payday = payday;
	}
	
	public boolean processPayday(){
		if(this.payday < 1){
			this.payday = 3600;
		} else {
			--this.payday;
			return false;
		}
		return false;
	}
	
	public boolean processTimeBanned(){
		if(this.timeBanned >= 1){
			--this.timeBanned;
			return false;
		} else {
			return true;
		}
	}
	
	public boolean unBan(){
		if(this.isBanned && !this.isPermBanned && (this.timeBanned <= 1)){
			this.timeBanned = 0;
			this.banReason = "";
			this.banner = "";
			this.isPermBanned = false;
			this.isBanned = false;
			return true;
		}
		return false;
	}
}
