package com.lala.wordrank;

import java.io.File;

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
}