package com.ssm.entity;

import java.sql.Timestamp;

public class TestGrade {
	private int testid;
	private String testname;
	private String username;
	private int grade;
	private String flag;
	private Timestamp submitdate;
	public TestGrade() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TestGrade(int testid, String testname, String username, int grade, String flag, Timestamp submitdate) {
		super();
		this.testid = testid;
		this.testname = testname;
		this.username = username;
		this.grade = grade;
		this.flag = flag;
		this.submitdate = submitdate;
	}
	public int getTestid() {
		return testid;
	}
	public void setTestid(int testid) {
		this.testid = testid;
	}
	public String getTestname() {
		return testname;
	}
	public void setTestname(String testname) {
		this.testname = testname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Timestamp getSubmitdate() {
		return submitdate;
	}
	public void setSubmitdate(Timestamp submitdate) {
		this.submitdate = submitdate;
	}
	
}
