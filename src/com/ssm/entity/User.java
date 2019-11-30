package com.ssm.entity;

import java.sql.Timestamp;
/**
 * Function:用户实体类
 * @author Aaron
 * Date:2019.4.11
 */
public class User {
	private String username;
	private String password;
	private String type;
	private Timestamp redate;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String username, String password, String type, Timestamp redate) {
		super();
		this.username = username;
		this.password = password;
		this.type = type;
		this.redate = redate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getRedate() {
		return redate;
	}
	public void setRedate(Timestamp redate) {
		this.redate = redate;
	}
	
}
