package com.lala.wordrank.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.ChatColor;

import com.lala.wordrank.Word;
import com.lala.wordrank.WordRank;

public class SQLWord {

	private Word word;
	private WordRank plugin;
	
	private SQLite sqlite;
	private MySQL mysql;
	
	private SQLType sqltype;
	
	public SQLWord(WordRank plugin, Word word){
		this.plugin = plugin;
		this.word = word;
		this.sqltype = plugin.sqtype;
		if (sqltype.equals(SQLType.SQLite))
			sqlite = plugin.sqlite;
		if (sqltype.equals(SQLType.MySQL))
			mysql = plugin.mysql;
	}
	
	public String addToDB(){
		if (wordExists()){
			return String.valueOf(ChatColor.RED + "The word "+ChatColor.GOLD+word.getName()+ChatColor.RED+" already exists!");
		}else{
			try {
				Connection con = null;
				if (sqltype.equals(SQLType.SQLite))
					con = sqlite.getConnection();
				if (sqltype.equals(SQLType.MySQL))
					con = mysql.getConnection();
				PreparedStatement p = con.prepareStatement(Query.INSERT_INTO.value());
				p.setString(1, word.getName());
				p.setString(2, word.getGroup());
				p.addBatch();
				con.setAutoCommit(false);
				p.executeBatch();
				con.setAutoCommit(true);
				return String.valueOf(ChatColor.GREEN + "Word "+ChatColor.GOLD+word.getName()+ChatColor.GREEN+" has been successfully added!");
			} catch (SQLException e){
				plugin.sendErr("Error while adding the word "+word.getName()+" to the database. Error message: "+e.getMessage()+ " ERROR CODE: "+e.getErrorCode());
				e.printStackTrace();
				return String.valueOf(ChatColor.RED + "Error adding the word "+ChatColor.GOLD+word.getName()+ChatColor.RED+". Please check the console for more info.");
			} catch (Exception e){
				plugin.sendErr("Unknown error while adding the word "+word.getName()+" to the database. Stacktrace:");
				e.printStackTrace();
				return String.valueOf(ChatColor.RED + "Error adding the word "+ChatColor.GOLD+word.getName()+ChatColor.RED+". Please check the console for more info.");
			}
		}
	}
	
	public String removeFromDB(){
		if (!wordExists()){
			return String.valueOf(ChatColor.RED + "The word "+ChatColor.GOLD+word.getName()+ChatColor.RED+" does not exist!");
		}else{
			if (sqltype.equals(SQLType.SQLite))
				sqlite.query(Query.DELETE_FROM.value()+"name='"+word.getName()+"'");
			
			if (sqltype.equals(SQLType.MySQL))
				mysql.query(Query.DELETE_FROM.value()+"name='"+word.getName()+"'");
			
			return String.valueOf(ChatColor.GREEN + "Word "+ChatColor.GOLD+word.getName()+ChatColor.GREEN+" has been deleted.");
		}
	}
	
	public boolean wordExists(){
		ArrayList<String> w = getWords();
		if (w.equals(null)) return false;
		if (w.contains(word.getName()))
			return true;
		else
			return false;		
	}
	
	public ArrayList<String> getWords(){
		ResultSet rs = null;
		ArrayList<String> w = new ArrayList<String>();
		
		if (sqltype.equals(SQLType.SQLite))
			rs = sqlite.query(Query.SELECT_NAME.value());
		if (sqltype.equals(SQLType.MySQL))
			rs = mysql.query(Query.SELECT_NAME.value());
		try {
			while (rs.next()){
				w.add(rs.getString(1));
			}
			return w;
		}catch (SQLException e){
			if (e.getMessage().contains("Illegal operation on empty result"))
				return null;
			plugin.send("SQLException while getting words from the database. Error message: "+e.getMessage()+" ERROR CODE: "+e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}
	
	public String getWordGroup(){
		ResultSet rs = null;
		
		if (sqltype.equals(SQLType.SQLite))
			rs = sqlite.query(Query.SELECT_GROUPNAME.value()+" name='"+word.getName()+"'");
		if (sqltype.equals(SQLType.MySQL))
			rs = mysql.query(Query.SELECT_GROUPNAME.value()+"name='"+word.getName()+"'");
		try {
			String s = null;
			while (rs.next()){
				s = rs.getString(1);
			}
			return s;
		} catch (SQLException e){
			plugin.sendErr("SQLException while getting the word "+word.getName()+"'s group from the database. Error message: "+e.getMessage()+" ERROR CODE: "+e.getErrorCode());
			e.printStackTrace();
			return null;
		}
	}
}
