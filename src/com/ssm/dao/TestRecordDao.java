package com.ssm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssm.entity.Question;
import com.ssm.entity.TestGrade;
import com.ssm.entity.TestRecord;
import com.ssm.entity.User;

/**
 * Function: ‘Ã‚Ω”ø⁄
 * @author Aaron
 * Date:2019.4.17
 */
@Repository("testRecordDao")
public interface TestRecordDao {

	List<TestRecord> SelectTestRecord();

	void AddTestRecord(TestRecord tr);

	Question FindQuestionBytid(int tid);

	void UpdateTestRecord(int testid, String libraryid, String testscore);

	void DeleteTestRecordByid(int id);

	void UpdatePublishByid(int id, String publish);

	TestRecord FindTestRecordByid(int id);

	void UpdateSetlibraryid(int id, String libraryid);

	List<User> FindUserBytype(String type);

	void AddTestGrade(TestGrade testgrade);

	void DeleteTestGrade(int id);

	List<TestGrade> SelectTestGrade(int testid);

	List<Question> SelectQuestion();
	
}
