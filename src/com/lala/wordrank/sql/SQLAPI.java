package com.lala.wordrank.sql;

import java.sql.Connection;
import java.sql.ResultSet;

import com.lala.wordrank.WordRank;

/**
 * This API was based off of PatPeter's SQL Library
 * It has been altered to be more efficient
 * and work better with WordRank.
 */

public abstract class SQLAPI {

	private WordRank plugin;
	
	public SQLAPI(WordRank plugin){
		this.plugin = plugin;
	}
	
	/**
	 * Prints out the message to the console
	 * @param message
	 */
	public void send(String message){
		plugin.send(message);
	}
	
	/**
	 * Prints out the message as an error to the console
	 * @param message
	 */
	public void sendErr(String message){
		plugin.sendErr(message);
	}
	
	/**
	 * Queries the string
	 * @param query
	 * @return Query's result set
	 */
	public abstract ResultSet query(String query);
	
	/**
	 * Queries the given Query enum value
	 * @param query
	 * @return Query's result set
	 */
	public abstract ResultSet query(Query query);
	
	/**
	 * Queries the given Query enum value plus the string extension
	 * @param query
	 * @param extension
	 * @return Querie's result set
	 */
	public abstract ResultSet query(Query query, String extension);
	
	/**
	 * Creates a table based on the Query enum's value and the string tablename
	 * @param tablecreatetype
	 * @param tablename
	 * @return true if successful
	 */
	public abstract boolean createTable(Query tablecreatetype);
	
	/**
	 * Checks if the given tablename exists in the database
	 * @param tablename
	 * @return true if table exists
	 */
	public abstract boolean tableExists(String tablename);
	
	/**
	 * Returns the currently used connection
	 * @return Connection
	 */
	public abstract Connection getConnection();
	
	/**
	 * Returns if the plugin is currently connected to the database
	 * @return true if plugin is connected
	 */
	public abstract boolean connected();
	
	/**
	 * Re-opens the SQL connection
	 * @return true if connection was successful
	 */
	public abstract boolean open();
	/**
	 * Closes the SQL connection
	 * @return true if connection closing was successful
	 */
	public abstract boolean close();
}
