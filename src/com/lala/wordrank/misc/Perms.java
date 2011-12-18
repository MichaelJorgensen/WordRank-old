package com.lala.wordrank.misc;

import com.lala.wordrank.WordRank;

public enum Perms {
	bPermissions, PEX, Permissions_Bukkit, GroupManager, Unknown;
	
	public String value(){
		switch (this){
		case bPermissions: return "bPermissions";
		case PEX: return "PermissionsEx";
		case Permissions_Bukkit: return "PermissionsBukkit";
		case GroupManager: return "GroupManager";
		default:
			return null;
		}
	}
	
	public boolean isEnabled(){
		return WordRank.permplugins.get(this);
	}
}
