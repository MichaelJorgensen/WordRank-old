package com.lala.wordrank.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lala.wordrank.WordRank;

/**
 * This API was based off of PatPeter's SQL Library
 * It has been altered to be more efficient
 * and work better with WordRank.
 */

public class MySQL extends SQLAPI{

	private String hostname = "localhost";
	private String portnumber = "3306";
	private String database = "minecraft";
	private String username = "minecraft";
	private String password = "password";
	
	private Connection con;
	
	private boolean connected = false;
	
	public MySQL(WordRank plugin, String hostname, String portnumber, String database, String username, String password){
		super(plugin);
		this.hostname = hostname;
		this.portnumber = portnumber;
		this.database = database;
		this.username = username;
		this.password = password;
		this.open();
	}
	
	public ResultSet query(String query){
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT CURTIME()");
			
			if (query.toLowerCase().contains("delete")){
				st.executeUpdate(query);
				return rs;
			} else {
				rs = st.executeQuery(query);
				return rs;
			}
		} catch (SQLException e){
			sendErr("SQLException while using MySQL with query in: "+getClass().getPackage().getName());
			sendErr("Query: "+"SELECT CURTIME() :: "+query);
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet query(Query query){
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT CURTIME()");
			
			if (query.value().toLowerCase().contains("delete")){
				st.executeUpdate(query.value());
				return rs;
			} else {
				rs = st.executeQuery(query.value());
				return rs;
			}
		} catch (SQLException e){
			sendErr("SQLException while using MySQL with query in: "+getClass().getPackage().getName());
			sendErr("Query: "+"SELECT CURTIME() :: "+query.value());
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet query(Query query, String extension){
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT CURTIME()");
			
			if (query.value().toLowerCase().contains("delete") || extension.toLowerCase().contains("delete")){
				st.executeUpdate(query.value()+extension);
				return rs;
			} else {
				rs = st.executeQuery(query.value()+extension);
				return rs;
			}
		} catch (SQLException e){
			sendErr("SQLException while using MySQL with query in: "+getClass().getPackage().getName());
			sendErr("Query: "+"SELECT CURTIME() :: "+query.value()+extension);
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean createTable(Query tablecreatetype){
		Statement st = null;
		try {
			if (tablecreatetype.equals(Query.CREATE_TABLE)){
				st = con.createStatement();
				st.execute(Query.CREATE_TABLE.value());
				return true;
			}
			else if (tablecreatetype.equals(Query.CREATE_TABLE_IF_NOT_EXISTS)){
				st = con.createStatement();
				st.execute(Query.CREATE_TABLE_IF_NOT_EXISTS.value());
				return true;
			}else{
				return false;
			}
		} catch (SQLException e){
			sendErr("SQLException while creating a table with MySQL in: "+getClass().getPackage().getName());
			if (tablecreatetype.equals(Query.CREATE_TABLE))
				sendErr("Query: "+Query.CREATE_TABLE.value());
			if (tablecreatetype.equals(Query.CREATE_TABLE_IF_NOT_EXISTS))
				sendErr("Query: "+Query.CREATE_TABLE_IF_NOT_EXISTS.value());
			else
				sendErr("Unknown query");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean tableExists(String tablename){
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM "+tablename);
			
			if (!rs.equals(null))
				return true;
			else
				return false;
		} catch (SQLException e){
			sendErr("SQLException while checking if a table exists with MySQL in: "+getClass().getPackage().getName());
			sendErr("Query: SELECT * FROM "+tablename);
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean connect(){
		String url = null;
		try {
			url = "jdbc:mysql://"+hostname+":"+portnumber+"/"+database;
			this.con = DriverManager.getConnection(url, username, password);
			this.connected = true;
			return true;
		} catch (SQLException e){
			sendErr("SQLException while connecting to MySQL database. Error message: "+e.getMessage()+" ERROR CODE: "+e.getErrorCode());
			this.con = null;
			this.connected = false;
		}
		return false;
	}
	
	public boolean open(){
		if (connect())
			return true;
		else
			return false;
	}
	
	public boolean close(){
		try {
			con.close();
			this.connected = false;
			return true;
		} catch (SQLException e){
			sendErr("SQLException while disconnection from MySQL database. Error message: "+e.getMessage()+" ERROR CODE: "+e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}
	
	public Connection getConnection(){
		return this.con;
	}
	
	public boolean connected(){
		return this.connected;
	}
	
	public String getHostname(){
		return this.hostname;
	}
	
	public String getPort(){
		return this.portnumber;
	}
	
	public String getDatabaseName(){
		return this.database;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
}
