package com.ssm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.dao.TestRecordDao;
import com.ssm.entity.Question;
import com.ssm.entity.TestGrade;
import com.ssm.entity.TestRecord;
import com.ssm.entity.User;

/**
 * Function:试题服务类
 * @author Aaron
 * Date:2019.4.17
 */
@Service("testRecordService")
public class TestRecordService {
	@Resource(name="testRecordDao")
	TestRecordDao dao;
	/*
	 * 查询所有试卷
	 */
	public List<TestRecord> selectTestRecord() {
		// TODO Auto-generated method stub
		List<TestRecord> list=dao.SelectTestRecord();
		return list;
	}
	public void addTestRecord(TestRecord tr) {
		// TODO Auto-generated method stub
		dao.AddTestRecord(tr);
	}
	public Question findQuestionBytid(int tid) {
		// TODO Auto-generated method stub
		Question question=dao.FindQuestionBytid(tid);
		return question;
	}
	public void updateTestRecord(int testid, String libraryid, String testscore) {
		// TODO Auto-generated method stub
		dao.UpdateTestRecord(testid,libraryid,testscore);
		
	}
	public void deleteTestRecordByid(int id) {
		// TODO Auto-generated method stub
		dao.DeleteTestRecordByid(id);
	}
	public void updatePublishByid(int id, String publish) {
		// TODO Auto-generated method stub
		dao.UpdatePublishByid(id,publish);
		
	}
	public TestRecord findTestRecordByid(int id) {
		// TODO Auto-generated method stub
		TestRecord test=dao.FindTestRecordByid(id);
		return test;
	}
	public void updateSetlibraryid(int id, String libraryid) {
		// TODO Auto-generated method stub
		dao.UpdateSetlibraryid(id,libraryid);
	}
	public List<User> findUserBytype(String type) {
		// TODO Auto-generated method stub
		List<User> list=dao.FindUserBytype(type);
		return list;
	}
	public void addTestGrade(TestGrade testgrade) {
		// TODO Auto-generated method stub
		dao.AddTestGrade(testgrade);
	}
	public void deleteTestGrade(int id) {
		// TODO Auto-generated method stub
		dao.DeleteTestGrade(id);
	}
	public List<TestGrade> selectTestGrade(int testid) {
		// TODO Auto-generated method stub
		List<TestGrade> list=dao.SelectTestGrade(testid);
		return list;
	}
	public List<Question> selectQuestion() {
		// TODO Auto-generated method stub
		List<Question> list=dao.SelectQuestion();
		return list;
	}

}
