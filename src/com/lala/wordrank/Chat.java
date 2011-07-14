package com.lala.wordrank;

import org.bukkit.event.player.PlayerListener;

public class Chat extends PlayerListener{
	@SuppressWarnings("unused")
	private WordRank plugin;
	public Chat(WordRank plugin){
		this.plugin = plugin;
	}
	
}
