package com.lala.wordrank.Listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import com.lala.wordrank.Config;
import com.lala.wordrank.Word;
import com.lala.wordrank.WordRank;
import com.lala.wordrank.misc.PermHandle;
import com.lala.wordrank.misc.RedeemType;
import com.lala.wordrank.sql.SQLWord;

public class PlayerListen extends PlayerListener{

	private WordRank plugin;
	
	public PlayerListen(WordRank plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void onPlayerChat(PlayerChatEvent event){
		if (plugin.redeemtype.equals(RedeemType.Command) || plugin.redeemtype.equals(RedeemType.Unknown)) return;
		Player player = event.getPlayer();
		String msg = event.getMessage();
		
		if (player.hasPermission("WordRank.say") || player.hasPermission("WordRank."+msg)){
			Word w = new Word(msg, "unknown");
			SQLWord sw = new SQLWord(plugin, w);
			ArrayList<String> wordlist = sw.getWords();
			if (wordlist.contains(msg)){
				Config config = new Config(plugin);
				PermHandle ph = new PermHandle(plugin, config.getPerms(), player);
				String groupname = sw.getWordGroup();
				
				w.setGroup(groupname);
				ph.setGroup(groupname);
				
				player.sendMessage(ChatColor.GREEN+"Congrats! You have been promoted to the group "+ChatColor.YELLOW+w.getGroup()+ChatColor.GREEN+"!");
				event.setCancelled(true);
				plugin.send(player.getName()+" has been promoted to "+w.getGroup()+" by WordRank.");
				return;
			}
		}else{
			return;
		}
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if (player.hasPermission("WordRank.update"))
			if (plugin.update)
				player.sendMessage(ChatColor.GREEN+"Update for WordRank availabe! Use '/wordrank update' to update!");
	}
}
