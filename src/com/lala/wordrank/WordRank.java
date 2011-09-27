package com.lala.wordrank;

import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

import de.bananaco.permissions.Permissions;
import de.bananaco.permissions.worlds.WorldPermissionsManager;

public class WordRank extends JavaPlugin{
	public WorldPermissionsManager wpm;
	public Config config = new Config(this);
	private W w = new W(this);
	public Server server;
	public Permissions p = new Permissions();
	public void onEnable(){
		getServer().getPluginManager().registerEvent(Type.PLAYER_CHAT, new Chat(this), Priority.Normal, this);		
		getCommand("w").setExecutor(w);
		setupPermissions();
		server = getServer();
		System.out.println("[WordRank] Enabled!");
	}
	public void onDisable(){
		System.out.println("[WordRank] Disabled!");
	}
	private void setupPermissions() {
	    try {
	    	wpm = Permissions.getWorldPermissionsManager();
	    } catch (Exception e) {
	    	System.err.println("bPermissions not detected.");
	    	this.setEnabled(false);
	    }
	}
}
