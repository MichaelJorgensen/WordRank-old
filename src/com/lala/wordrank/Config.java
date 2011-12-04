package com.lala.wordrank;

import org.bukkit.configuration.file.FileConfiguration;

import com.lala.wordrank.misc.Perms;
import com.lala.wordrank.misc.RedeemType;

public class Config {
	
	private FileConfiguration yml;
	
	public Config(WordRank plugin){
		this.yml = plugin.getConfig();
		yml.options().copyDefaults(true);
		plugin.saveConfig();
	}
	
	public Perms getPerms(){
		if (permPlugin().equalsIgnoreCase("bPermissions"))
			return Perms.bPermissions;
		else if (permPlugin().equalsIgnoreCase("PEX"))
			return Perms.PEX;
		else if (permPlugin().equalsIgnoreCase("GroupManager"))
			return Perms.GroupManager;
		else
			return Perms.Unknown;
	}
	
	public RedeemType getRedeemType(){
		if (redeemType().equalsIgnoreCase("Chat"))
			return RedeemType.Chat;
		else if (redeemType().equalsIgnoreCase("Command"))
			return RedeemType.Command;
		else
			return RedeemType.Unknown;
	}
	
	public String permPlugin(){
		return yml.getString("perm-plugin");
	}
	
	public String redeemType(){
		return yml.getString("redeem-type");
	}
}