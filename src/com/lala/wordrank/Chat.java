package com.lala.wordrank;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

import de.bananaco.permissions.interfaces.PermissionSet;

public class Chat extends PlayerListener{
	private WordRank plugin;
	private Config config;
	public Chat(WordRank plugin){
		this.plugin = plugin;
		this.config = plugin.config;
	}
	public boolean has(CommandSender sender, String permission) {
		return sender.hasPermission(permission);
	}
	public boolean inGroup(Player player, String group) {
		PermissionSet ps = plugin.wpm.getPermissionSet(player.getWorld());
		List<String> groups = ps.getGroups(player);
		return groups.contains(group);
	}
	public String[] getGroups(Player player) {
		PermissionSet ps = plugin.wpm.getPermissionSet(player.getWorld());
		String[] groupArray;
		List<String> groups = ps.getGroups(player);
		groupArray = new String[groups.size()];
		groupArray = groups.toArray(groupArray);
		return groupArray;
	}
	public void onPlayerChat(PlayerChatEvent event){
		if (config.exists(event.getMessage()) && has(event.getPlayer(), "WordRank.say") || config.exists(event.getMessage()) && has(event.getPlayer(), "WordRank." + event.getMessage())){
			Player player = event.getPlayer();
			if (inGroup(player, config.getWordGroup(event.getMessage()))){
				player.sendMessage(ChatColor.RED + "You can't use a magic word for a group you are already in!");
				return;
			}else{
				String[] g = getGroups(player);				
				for (int i = g.length; i > 0; i--){
					config.removeParent(player, g[i - 1], event.getMessage());					
				}
				config.addGroup(event.getPlayer(), config.getWordGroup(event.getMessage()), event.getMessage());
				player.sendMessage(ChatColor.GOLD + config.getCongratsMsg().replaceAll("%player%", event.getPlayer().getDisplayName()).replaceAll("%group%", config.getWordGroup(event.getMessage())));
				event.setCancelled(true);
				return;
			}
		}else{
			if (config.exists(event.getMessage())){
				event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to do that!");
				return;
			}
		}
	}
}
