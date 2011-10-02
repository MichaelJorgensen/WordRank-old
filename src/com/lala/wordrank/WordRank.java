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
	public void onEnable(){
		getServer().getPluginManager().registerEvent(Type.PLAYER_CHAT, new Chat(this), Priority.Normal, this);		
		getCommand("wordrank").setExecutor(w);
		setupPermissions();
		server = getServer();
		send("Enabled!");
	}
	public void onDisable(){
		send("Disabled!");
	}
	private void setupPermissions() {
	    try {
	    	wpm = Permissions.getWorldPermissionsManager();
	    } catch (Exception e) {
	    	System.err.println("[WordRank] bPermissions not detected - WordRank disabling.");
	    	this.setEnabled(false);
	    }
	}
	public void send(String message){
		System.out.println("[WordRank] "+message);
	}
}