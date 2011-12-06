package com.lala.wordrank.sql;

public enum Query {
	CREATE_TABLE, CREATE_TABLE_IF_NOT_EXISTS, INSERT_INTO, DELETE_FROM, SELECT_NAME, SELECT_GROUPNAME;
	
	public String value(){
		switch (this){
		case CREATE_TABLE: return "CREATE TABLE wordrank (name VARCHAR(254), groupname VARCHAR(254))";
		case CREATE_TABLE_IF_NOT_EXISTS: return "CREATE TABLE IF NOT EXISTS wordrank (name VARCHAR(254), groupname VARCHAR(254))";
		case INSERT_INTO: return "INSERT INTO wordrank VALUES (?, ?)";
		case DELETE_FROM: return "DELETE FROM wordrank WHERE ";
		case SELECT_NAME: return "SELECT name FROM wordrank";
		case SELECT_GROUPNAME: return "SELECT groupname FROM wordrank WHERE ";
		}
		return null;
	}
}
