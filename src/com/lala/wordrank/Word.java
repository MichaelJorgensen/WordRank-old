package com.lala.wordrank;

public class Word {

	private String name;
	private String group;
	
	public Word(String name, String group){
		this.name = name;
		this.group = group;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getGroup(){
		return this.group;
	}
	
	public void setGroup(String groupname){
		this.group = groupname;
	}
}
