package com.lala.wordrank.sql;

import java.io.File;
import java.io.IOException;
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

public class SQLite extends SQLAPI{
	private String dbname;
	private String pathloc;
	
	private boolean connected = false;
	
	private File sqlFile;
	
	private Connection con;
	
	public SQLite(WordRank plugin, String dbname, String pathloc){
		super(plugin);
		this.dbname = dbname;
		this.pathloc = pathloc;
		File f = new File(pathloc);
		if (!f.exists())
			f.mkdir();
		this.sqlFile = new File(f.getAbsolutePath()+File.separator+dbname+".db");
		if (!sqlFile.exists()){
			try {
				sqlFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.open();
	}
	
	public ResultSet query(String query){
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			
			if (query.toLowerCase().contains("delete")){
				st.executeUpdate(query);
				return rs;
			} else {
				rs = st.executeQuery(query);
				return rs;
			}
		} catch (SQLException e){
			sendErr("SQLException while using SQLite with query in: "+getClass().getPackage().getName());
			sendErr("Query: "+query);
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet query(Query query){
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			
			if (query.value().contains("delete")){
				st.executeUpdate(query.value());
				return rs;
			} else {
				rs = st.executeQuery(query.value());
				return rs;
			}
		} catch (SQLException e){
			sendErr("SQLException while using SQLite with query in: "+getClass().getPackage().getName());
			sendErr("Query: "+query.value());
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet query(Query query, String extension){
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			
			if (query.value().toLowerCase().contains("delete") || extension.toLowerCase().contains("delete")){
				st.executeUpdate(query.value()+extension);
				return rs;
			} else {
				rs = st.executeQuery(query.value()+extension);
				return rs;
			}
		} catch (SQLException e){
			sendErr("SQLException while using SQLite with query in: "+getClass().getPackage().getName());
			sendErr("Query: "+query.value()+extension);
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
			sendErr("SQLException while creating a table with SQLite in: "+getClass().getPackage().getName());
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
			
			if (!rs.equals(null)){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e){
			if (e.getMessage().contains("missing database")){
				return false;
			}else{
				sendErr("SQLException while checking if a table exists with SQLite in: "+getClass().getPackage().getName());
				sendErr("Query: SELECT * FROM "+tablename);
				e.printStackTrace();
				return false;
			}
		}
	}
	
	private Connection startConnection(){
		try {
			return DriverManager.getConnection("jdbc:sqlite:"+sqlFile.getAbsolutePath());
		} catch (SQLException e){
			sendErr("SQLException while connecting to SQLite database. Error message: "+e.getMessage()+" ERROR CODE: "+e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean open(){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e){
			sendErr("JDBC class not found! :O Inbound errors! :'(");
			e.printStackTrace();
			return false;
		}
		Connection c = this.startConnection();
		if (!c.equals(null)){
			this.con = c;
			this.connected = true;
			return true;
		}else{
			this.con = null;
			this.connected = false;
			return false;
		}
	}
	
	public boolean close(){
		try {
			con.close();
			this.connected = false;
			return true;
		} catch (SQLException e){
			sendErr("SQLException while disconnecting from SQLite database. Error message: "+e.getMessage()+" ERROR CODE: "+e.getErrorCode());
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
	
	public String getDatabaseName(){
		return this.dbname;
	}
	
	public String getDatabasePath(){
		return String.valueOf(this.pathloc+this.dbname);
	}
	
	public File getSQLiteFile(){
		return this.sqlFile;
	}
}