package com.ssm.entity;

public class Error {
	private int id;
	private String hint;
	public Error() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Error(int id, String hint) {
		super();
		this.id = id;
		this.hint = hint;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	
}
