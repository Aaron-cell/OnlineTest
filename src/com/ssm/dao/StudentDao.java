package com.ssm.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssm.entity.Question;
import com.ssm.entity.TestGrade;
import com.ssm.entity.TestRecord;
import com.ssm.entity.User;

/**
 * Function:学生端接口
 * @author Aaron
 * Date：2019.4.21
 */
@Repository("studentDao")
public interface StudentDao {

	List<TestRecord> FindTestRecord();

	List<TestRecord> FindTestRecordBypublish(String publish);

	TestGrade FindTestGradeBytestid(int testid,String username);

	TestRecord SelectTestRecordByid(int id);

	Question FindQuestionBytid(int tid);

	void UpdateTestGrade(int testid, String name, int score, String flag, Timestamp submitdate);

	List<TestGrade> InquiryTestGradeByusername(String username);

	User FindUserByusername(String username);

	void UserUpdate(String username, String newPassword);
	
}
