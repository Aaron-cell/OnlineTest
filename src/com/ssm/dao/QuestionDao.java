package com.ssm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssm.entity.Question;
import com.ssm.entity.TestRecord;
/**
 * Function:Ìâ¿â½Ó¿Ú
 * @author Aaron
 * Date:2019.4.15
 */
@Repository("questionDao")
public interface QuestionDao {

	List<Question> SelectQuestion();

	Question FindQuestionBytid(int tid);

	void DeleteQuestionBytid(int tid);

	void AddQuestion(Question question);

	List<Question> FindQuestionByType(String type);

	List<Question> FindQuestionBytt(String title, String type);

	List<Question> FindQuestionBytitle(String title);

	List<Question> FindQuestionByDate(String start, String ends);

	List<Question> SelectQuestionLimit(int i, int j);

	List<Question> LookQuestionBytype(String type, int i, int j);

	List<Question> LookQuestionBytitle(String title, int i, int j);

	List<Question> LookQuestion(String title, String type, int i, int j);

	List<Question> LookLibraryLimit(String start, String ends, int i, int j);

	TestRecord SelectTestRecordByid(int id);

	void UpdateTestRecordBytestid(int testid, String libraryid);

	void UpdateQuestionBytid(Question question);
	
}
