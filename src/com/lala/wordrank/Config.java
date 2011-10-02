package com.lala.wordrank;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

import de.bananaco.permissions.interfaces.PermissionSet;

public class Config {
	private final WordRank plugin;

	public Config(WordRank plugin){
		this.plugin = plugin;
	}
	private Configuration getYML(){
	return plugin.getConfiguration();	
	}

	public void addWord(String word, String group, String world, boolean set){
		final Configuration yml = getYML();
		yml.setProperty("config.wordlist." + word + ".group", group);
		yml.setProperty("config.wordlist." + word + ".world", world);
		yml.setProperty("config.wordlist." + word + ".setgroup", set);
		yml.save();
	}
	public World getWordWorld(String word){
		final Configuration yml = getYML();
		String y = (String) yml.getProperty("config.wordlist." + word + ".world");
		World w =  plugin.getServer().getWorld(y);
		return w;
	}
	public boolean exists(String word){
		final Configuration yml = getYML();
		Object j;
		j = yml.getProperty("config.wordlist." + word);
		if (j == null){
			return false;
		}else{
			return true;
		}
	}
	public void removeall(){
		final Configuration yml = getYML();
		yml.removeProperty("config.wordlist");
		yml.save();
	}
	public void remove(String word){
		final Configuration yml = getYML();
		yml.removeProperty("config.wordlist." + word);
		yml.save();
		return;
	}
	public boolean getSetGroup(String word){
		final Configuration yml = getYML();
		return yml.getBoolean("config.wordlist."+word+".setgroup", true);
	}
	public String getWordGroup(String word){
		final Configuration yml = getYML();
		String f;
		f = yml.getString("config.wordlist." + word + ".group");
		if (f != null){
			return f;
		}else{
			return null;
		}
	}
	public void loadPluginSettings(){
		final Configuration yml = getYML();
		yml.getString("config.congrats-msg", "Congrats %player%! You are now in the group %group%");
		yml.save();
	}
	public String getCongratsMsg(){
		final Configuration yml = getYML();
		String c = yml.getString("config.congrats-msg", "Congrats %player%! You are now in the group %group%");
		return c;
	}
	public boolean groupExists(String group, World world){
		PermissionSet ps = plugin.wpm.getPermissionSet(world);
		List<String> groups = ps.getAllCachedGroups();
		return groups.contains(group);
	}
	public void addGroup(Player player, String group, String word) {
		PermissionSet ps = plugin.wpm.getPermissionSet(getWordWorld(word));
		ps.addGroup(player, group);
	}
	public void removeParent(Player player, String group, String word) {		
		PermissionSet ps = plugin.wpm.getPermissionSet(getWordWorld(word));
		ps.removeGroup(player, group);	
	}
}