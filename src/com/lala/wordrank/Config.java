package com.lala.wordrank;

import java.io.File;

import org.bukkit.World;
import org.bukkit.util.config.Configuration;

public class Config extends Configuration{
	public Config(File file){
		super(file);		
	}
	private static Config getYML(){
		File d = WordRank.data;
		final File yaml = new File(d, "config.yml");
		if (!d.exists()){
			d.mkdirs();
		}
		final Config yml = new Config(yaml);
		if (yaml.exists()){
			yml.load();
		}
		return yml;	
	}
	private static Config getPYML(String world){
		File d = new File("/plugins/Permissions/" + world + "/users.yml");		
		if (!d.exists()){
			d.mkdirs();
		}
		final Config yml = new Config(d);
		if (d.exists()){
			yml.load();
		}
		return yml;	
	}
	private static Config getGYML(String world){
		File d = new File("/plugins/Permissions/" + world + "/groups.yml");		
		if (!d.exists()){
			d.mkdirs();
		}
		final Config yml = new Config(d);
		if (d.exists()){
			yml.load();
		}
		return yml;	
	}
	public static void setGroup(String p, World world, String group){
		final Config yml = getPYML(world.getName());
		yml.setProperty("users." + p + ".groups", group);
		yml.save();
		WordRank.permissionHandler.reload();
	}
	public static void addWord(String word, String group){
		final Config yml = getYML();
		yml.setProperty("config.wordlist." + word + ".group", group);
		yml.save();
	}
	public static boolean exists(String word){
		final Config yml = getYML();
		Object j;
		j = yml.getProperty("config.wordlist." + word);
		if (j == null){
			return false;
		}else{
			return true;
		}
	}
	public static void removeall(){
		final Config yml = getYML();
		yml.removeProperty("config.wordlist");
		yml.save();
	}
	public static void remove(String word){
		final Config yml = getYML();
		yml.removeProperty("config.wordlist.word");
		yml.save();
		return;
	}
	public static String getWordGroup(String word){
		final Config yml = getYML();
		String f;
		f = yml.getString("config.wordlist." + word + ".group");
		if (f != null){
			return f;
		}else{
			return null;
		}
	}
	public static void loadPluginSettings(){
		final Config yml = getYML();
		yml.getString("config.congrats-msg", "Congrats %player%! You are now in the group %group% for saying %word%");
		yml.save();
	}
	public static String getCongratsMsg(){
		final Config yml = getYML();
		String c = yml.getString("config.congrats-msg", "Congrats %player%! You are now in the group %group% for saying %word%");
		return c;
	}
	public static boolean groupExists(String group, World world){
		final Config yml = getGYML(world.getName());
		Object o = yml.getProperty("groups." + group);
		if (o != null){
			return true;
		}else{
			return false;
		}
	}
}