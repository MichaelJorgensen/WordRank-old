package com.lala.wordrank;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class Chat extends PlayerListener{
	@SuppressWarnings("unused")
	private WordRank plugin;
	public Chat(WordRank plugin){
		this.plugin = plugin;
	}
	public void onPlayerChat(PlayerChatEvent event){
		if (Config.exists(event.getMessage()) && WordRank.permissionHandler.has(event.getPlayer(), "WordRank.say")){
			Player player = event.getPlayer();			
			Config.addParent(event.getPlayer(), Config.getWordGroup(event.getMessage()));
			player.sendMessage(ChatColor.GOLD + Config.getCongratsMsg().replaceAll("%player%", event.getPlayer().getDisplayName()).replaceAll("%group%", Config.getWordGroup(event.getMessage())));
			event.setCancelled(true);
			return;
		}
	}
}
