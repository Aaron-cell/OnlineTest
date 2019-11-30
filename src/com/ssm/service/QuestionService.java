package com.ssm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.dao.QuestionDao;
import com.ssm.entity.Question;
import com.ssm.entity.TestRecord;

/**
 * Function:题库服务类
 * @author Aaron
 * Date:2019.4.15
 */
@Service("questionService")
public class QuestionService {
	@Resource(name="questionDao")
	QuestionDao dao;

	public List<Question> selectQuestion() {
		// TODO Auto-generated method stub
		List<Question> list=dao.SelectQuestion();
		return list;
	}

	public Question findQuestionBytid(int tid) {
		// TODO Auto-generated method stub
		Question question=dao.FindQuestionBytid(tid);
		return question;
	}

	public void deleteQuestionBytid(int tid) {
		// TODO Auto-generated method stub
		dao.DeleteQuestionBytid(tid);
	}

	public void addQuestion(Question question) {
		// TODO Auto-generated method stub
		dao.AddQuestion(question);
	}

	public List<Question> findQuestionByType(String type) {
		// TODO Auto-generated method stub
		List<Question> list=dao.FindQuestionByType(type);
		return list;
	}

	public List<Question> findQuestionBytt(String title, String type) {
		// TODO Auto-generated method stub
		List<Question> list=dao.FindQuestionBytt(title,type);
		return list;
	}

	public List<Question> findQuestionBytitle(String title) {
		// TODO Auto-generated method stub
		List<Question> list=dao.FindQuestionBytitle(title);
		return list;
	}

	public List<Question> findQuestionByDate(String start, String ends) {
		// TODO Auto-generated method stub
		List<Question> list=dao.FindQuestionByDate(start,ends);
		return list;
	}

	public List<Question> selectQuestionLimit(int i, int j) {
		// TODO Auto-generated method stub
		List<Question> list=dao.SelectQuestionLimit(i,j);
		return list;
	}

	public List<Question> lookQuestionBytype(String type, int i, int j) {
		// TODO Auto-generated method stub
		List<Question> list=dao.LookQuestionBytype(type,i,j);
		return list;
	}

	public List<Question> lookQuestionBytitle(String title, int i, int j) {
		// TODO Auto-generated method stub
		List<Question> list=dao.LookQuestionBytitle(title,i,j);
		return list;
	}

	public List<Question> lookQuestion(String title, String type, int i, int j) {
		// TODO Auto-generated method stub
		List<Question> list=dao.LookQuestion(title,type,i,j);
		return list;
	}

	public List<Question> lookLibraryLimit(String start, String ends, int i, int j) {
		// TODO Auto-generated method stub
		List<Question> list=dao.LookLibraryLimit(start,ends,i,j);
		return list;
	}

	public TestRecord selectTestRecordByid(int id) {
		// TODO Auto-generated method stub
		TestRecord tr=dao.SelectTestRecordByid(id);
		return tr;
	}

	public void updateTestRecordBytestid(int testid, String libraryid) {
		// TODO Auto-generated method stub
		dao.UpdateTestRecordBytestid(testid,libraryid);
	}

	public void updateQuestionBytid(Question question) {
		// TODO Auto-generated method stub
		dao.UpdateQuestionBytid(question);
	}

}
