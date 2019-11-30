package com.ssm.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.dao.StudentDao;
import com.ssm.entity.Question;
import com.ssm.entity.TestGrade;
import com.ssm.entity.TestRecord;
import com.ssm.entity.User;

/**
 * Function:学生端服务类
 * @author Aaron
 * Date:2019.4.21
 */
@Service("studentService")
public class StudentService {
	@Resource(name="studentDao")
	StudentDao dao;

	public List<TestRecord> findTestRecord() {
		// TODO Auto-generated method stub
		List<TestRecord> list=dao.FindTestRecord();
		return list;
	}

	public List<TestRecord> findTestRecordBypublish(String publish) {
		// TODO Auto-generated method stub
		List<TestRecord> list=dao.FindTestRecordBypublish(publish);
		return list;
	}

	public TestGrade findTestGradeBytestid(int testid,String username) {
		// TODO Auto-generated method stub
		TestGrade testgrade=dao.FindTestGradeBytestid(testid,username);
		return testgrade;
	}

	public TestRecord selectTestRecordByid(int id) {
		// TODO Auto-generated method stub
		TestRecord testrecord=dao.SelectTestRecordByid(id);
		return testrecord;
	}

	public Question findQuestionBytid(int tid) {
		// TODO Auto-generated method stub
		Question question=dao.FindQuestionBytid(tid);
		return question;
	}

	public void updateTestGrade(int testid, String name, int score, String flag, Timestamp submitdate) {
		// TODO Auto-generated method stub
		dao.UpdateTestGrade(testid,name,score,flag,submitdate);
	}

	public List<TestGrade> inquiryTestGradeByusername(String username) {
		// TODO Auto-generated method stub
		List<TestGrade> list=dao.InquiryTestGradeByusername(username);
		return list;
	}

	public User findUserByusername(String username) {
		// TODO Auto-generated method stub
		User user=dao.FindUserByusername(username);
		return user;
	}

	public void userUpdate(String username, String newPassword) {
		// TODO Auto-generated method stub
		dao.UserUpdate(username,newPassword);
	}
}
