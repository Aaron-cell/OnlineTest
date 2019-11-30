package com.ssm.entity;

import java.sql.Timestamp;

/**
 * Function: ‘æÌ µÃÂ¿‡
 * @author Aaron
 * Date:2019.4.17
 */

public class TestRecord {
	private int testid;
	private String testname;
	private String description;
	private Timestamp start;
	private Timestamp ends;
	private String libraryid;
	private String publish;
	private String testscore;
	public TestRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TestRecord(int testid, String testname, String description, Timestamp start, Timestamp ends,
			String libraryid, String publish,String testscore) {
		super();
		this.testid = testid;
		this.testname = testname;
		this.description = description;
		this.start = start;
		this.ends = ends;
		this.libraryid = libraryid;
		this.publish = publish;
		this.testscore=testscore;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public Timestamp getEnds() {
		return ends;
	}
	public void setEnds(Timestamp ends) {
		this.ends = ends;
	}
	public String getLibraryid() {
		return libraryid;
	}
	public void setLibraryid(String libraryid) {
		this.libraryid = libraryid;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	public String getTestscore() {
		return testscore;
	}
	public void setTestscore(String testscore) {
		this.testscore = testscore;
	}
	
	
	
}
