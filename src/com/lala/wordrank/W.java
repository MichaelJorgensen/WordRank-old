package com.lala.wordrank;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class W implements CommandExecutor{
	@SuppressWarnings("unused")
	private WordRank plugin;
	public W(WordRank plugin){
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("You need to be a player!");
			return true;
		}else{
			if (args.length <= 0){
				return false;
			}else{ // /w add [word] [group] [world] /w remove [word] /w removeall
				if (args[0].equalsIgnoreCase("add") && args.length >= 3 && WordRank.permissionHandler.has((Player) sender, "WordRank.add")){
					Player pl = (Player) sender;
					if (Config.groupExists(args[2], pl.getWorld()) == false){
						pl.sendMessage(ChatColor.RED + "That group doesn't exist!");
						return true;
					}
					if (Config.exists(args[1])){
						sender.sendMessage(ChatColor.RED + "That word already exists!");
						return true;						
					}				
					else{
						Player player = (Player) sender;
						String word = args[1];
						String group = args[2];
						String world = player.getWorld().getName();
						if (args.length >= 4) world = args[3];
						Config.addWord(word, group, world);
						player.sendMessage(ChatColor.GREEN + "Word added! (" + word + " gives group " + group + " for world" + world + ")");
						return true;
					}
				}
				else if (args[0].equalsIgnoreCase("remove") && args.length >= 2 && WordRank.permissionHandler.has((Player) sender, "WordRank.remove")){
					String word = args[1];
					if (Config.exists(word)){
						Config.remove(word);
						sender.sendMessage(ChatColor.GREEN + "Word removed!");
						return true;
					}else{
						sender.sendMessage(ChatColor.RED + "That word doesn't exist!");
						return true;
					}
				}
				else if (args[0].equalsIgnoreCase("removeall") && args.length >= 1 && WordRank.permissionHandler.has((Player) sender, "WordRank.remove.all")){
					Config.removeall();
					sender.sendMessage(ChatColor.GREEN + "All words removed!");
					return true;
				}else{
					return false;
				}
			}
		}
	}
}
