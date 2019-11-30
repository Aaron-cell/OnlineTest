package com.ssm.contorller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssm.entity.ExcelImpl;
import com.ssm.entity.Question;
import com.ssm.entity.RandomTest;
import com.ssm.entity.TestGrade;
import com.ssm.entity.TestRecord;
import com.ssm.entity.User;
import com.ssm.service.TestRecordService;

/**
 * Function�����������
 * @author Aaron
 * Date:2019.4.17
 */
@Controller
public class TestRecordContorller {
	
	@Resource(name="testRecordService")
	
	TestRecordService service;
	TestRecord testrecord;
	int pagecount=0;//��ҳ��
	/*
	 * ��ѯ�Ծ���������
	 */
	@RequestMapping("TestRecordManager.do")
	public String doTestManager(HttpServletRequest request)throws Exception{
		request.setCharacterEncoding("utf-8");
		List<TestRecord> list1=new ArrayList<TestRecord>();
		list1=service.selectTestRecord();
		/*
		 * ������Ҫ�ǽ��һ���������������������ӵ��Ծ����������ɾ���󣬺�������������
		 */
		for( int i = 0 ; i < list1.size() ; i++) {
			int Testid=list1.get(i).getTestid();
			String libraryid=list1.get(i).getLibraryid();
			if(!"".equals(libraryid)) {
				int value=0;
			    String[] str=libraryid.split(",");
			    for(int j=0;j<str.length;j++) {
			    	int tid=Integer.parseInt(str[j]);
			    	Question question=service.findQuestionBytid(tid);
			    	if(question!=null) {
			    		value+=Integer.parseInt(question.getScore());
			    	}else {
			    		String str1=str[j]+",";
			    		libraryid=libraryid.replace(str1,"");
			    	}
					String testscore=String.valueOf(value);
					service.updateTestRecord(Testid,libraryid,testscore);
			    }
			}else {
				service.updateTestRecord(Testid,libraryid,"0");
			}
				
		}
		List<TestRecord> list=new ArrayList<TestRecord>();
		list=service.selectTestRecord();
		request.setAttribute("list", list);
		return "TestRecordList";
	}
	/*
	 * �����Ծ����
	 */
	@RequestMapping("AddTestRecord.do")
	public String doAddTestRecord(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		TestRecord tr;
		String testname=request.getParameter("testname");
		String description=request.getParameter("description");
		String start=request.getParameter("start");
		String ends=request.getParameter("ends");
		//�ַ���ת����timestamp��
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Timestamp st=new Timestamp(dateFormat.parse(start).getTime());
		Timestamp ed=new Timestamp(dateFormat.parse(ends).getTime());
		//�ȿ�ʼʱ��ͽ���ʱ���С
		if(dateFormat.parse(start).getTime()<dateFormat.parse(ends).getTime()) {
			if("".equals(description)) {
				tr=new TestRecord(0,testname,"null",st,ed,"","δ����","0");
			}else {
				tr=new TestRecord(0,testname,description,st,ed,"","δ����","0");
			}
			service.addTestRecord(tr);
			return"forward:/TestRecordManager.do";
		}else {
			request.setAttribute("error", "��ʼʱ�䲻����Ƚ���ʱ����");
		}
		request.setAttribute("testname",testname);
		request.setAttribute("description", description);
		return "TestRecordAdd";
	}
	/*
	 * ɾ���Ծ�
	 */
	@RequestMapping("DeleteTestRecord.do")
	public String doDeleteTestRecord(HttpServletRequest request,int id)throws Exception{
		request.setCharacterEncoding("utf-8");
		service.deleteTestRecordByid(id);
		service.deleteTestGrade(id);
		return "forward:/TestRecordManager.do";
	}
	/*
	 * �����Ծ�
	 */
	@RequestMapping("PublishTestRecord.do")
	public String doPublishTestRecord(HttpServletRequest request,int id)throws Exception{
		request.setCharacterEncoding("utf-8");
		User user;
		TestGrade testgrade;
		TestRecord testrecord=service.findTestRecordByid(id);
		String testname=testrecord.getTestname();
		Date date=new Date();
		Timestamp st=new Timestamp(date.getTime());
		if("δ����".equals(testrecord.getPublish())) {
			if(st.before(testrecord.getStart())) {
				//true st��ʱ���startʱ����
				service.updatePublishByid(id,"�ѷ���");
				//��ѧ���û����ص��ɼ���
				List<User> list=new ArrayList<User>();
				list=service.findUserBytype("student");
				for(int i=0;i<list.size();i++) {
					testgrade=new TestGrade(id,testname,list.get(i).getUsername(),0,"δ���",st);
					service.addTestGrade(testgrade);
				}
			}else {
				request.setAttribute("hint", "����ʱ�䲻�ܱȿ�ʼʱ���������´����Ծ�");
			}
		}else {
			request.setAttribute("hint", "�Ծ��ѷ����������ٴη���");
		}
		
		return "forward:/TestRecordManager.do";
	}
	/*
	 * �����޸��Ծ�ҳ��
	 */
	@RequestMapping("AlterTestRecord.do")
	public String doAlterTestRecord(HttpServletRequest request,int id,int currentpage)throws Exception{
		request.setCharacterEncoding("utf-8");
		int count=0;
		TestRecord test1=service.findTestRecordByid(id);
		List<Question> list1=new ArrayList<Question>();
		System.out.println(test1.getLibraryid());
		if(!"".equals(test1.getLibraryid())) {
			String[] str1=test1.getLibraryid().split(",");
			for(int i=0;i<str1.length;i++) {
				int tid=Integer.parseInt(str1[i]);
				Question question=service.findQuestionBytid(tid);
				list1.add(question);
			}
		}
		//����list���������ܺ�
		for(int i=1;i<=list1.size();i++) {
			count=i;
		}
		if(count%10==0) {
			pagecount=count/10-1;
		}else {
			pagecount=count/10;
		}
		//��������ȽϿ��ܻ�����±�Խ��
		if(currentpage*10>count-1) {
			List<Question> list=new ArrayList<>();
			request.setAttribute("list", list);
		}else {
			if((currentpage+1)*10>=count) {
				List<Question> list=list1.subList(currentpage*10,count);
				request.setAttribute("list", list);
			}else {
				List<Question> list=list1.subList(currentpage*10,(currentpage+1)*10 );
				request.setAttribute("list", list);
			}
		}
		boolean flag=false;
		if("δ����".equals(test1.getPublish())) {
			flag=true;
		}
		request.setAttribute("flag",flag);
		request.setAttribute("id", id);
		request.setAttribute("pagecount", pagecount);
		request.setAttribute("currentpage", currentpage);
		request.setAttribute("record", currentpage*10);
		
		request.setAttribute("page", "AlterTestRecord.do?");		
		return "TestRecordView";
	}
	/*
	 * ɾ���Ծ��е�����
	 */
	@RequestMapping("deleteTest.do")
	public String dodeleteTest(HttpServletRequest request,int tid,int id)throws Exception{
		request.setCharacterEncoding("utf-8");
		System.out.println(tid);
		TestRecord test2=service.findTestRecordByid(id);
		String libraryid1=test2.getLibraryid();
		Question question=service.findQuestionBytid(tid);
		String tid1=String.valueOf(question.getTid());
		String str1=tid1+",";
		libraryid1=libraryid1.replace(str1,"");
		service.updateSetlibraryid(id, libraryid1);
		
		String url="forward:/AlterTestRecord.do?id="+String.valueOf(id)+"&currentpage=0";
		
		return url;
	}
	/*
	 * �ɼ�����ҳ��
	 */
	@RequestMapping("GradeManager.do")
	public String doGradeManager(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		List<TestRecord> list1=new ArrayList<TestRecord>();
		list1=service.selectTestRecord();
		/*
		 * ������Ҫ�ǽ��һ���������������������ӵ��Ծ����������ɾ���󣬺�������������
		 */
		for( int i = 0 ; i < list1.size() ; i++) {
			int Testid=list1.get(i).getTestid();
			String libraryid=list1.get(i).getLibraryid();
			if(!"".equals(libraryid)) {
				int value=0;
			    String[] str=libraryid.split(",");
			    for(int j=0;j<str.length;j++) {
			    	int tid=Integer.parseInt(str[j]);
			    	Question question=service.findQuestionBytid(tid);
			    	if(question!=null) {
			    		value+=Integer.parseInt(question.getScore());
			    	}else {
			    		String str1=str[j]+",";
			    		libraryid=libraryid.replace(str1,"");
			    	}
					String testscore=String.valueOf(value);
					service.updateTestRecord(Testid,libraryid,testscore);
			    }
			}else {
				service.updateTestRecord(Testid,libraryid,"0");
			}
				
		}
		List<TestRecord> list=new ArrayList<TestRecord>();
		list=service.selectTestRecord();
		request.setAttribute("list", list);
		return "GradeExport";		
	}
	/*
	 * ���ɼ�����excel
	 */
	@RequestMapping("ExportExcel.do")
	public String doExportExcel(HttpServletResponse response,@RequestParam("testid") int testid,@RequestParam("testname") String testname)throws Exception {
		response.setContentType("application/binary;charset=UTF-8");
		
		response.setCharacterEncoding("utf-8");  
	
	      try{
	          ServletOutputStream out=response.getOutputStream();
	          try {
	              //�����ļ�ͷ�����һ�����������������ļ���
	              response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(testid+".xls", "UTF-8"));
	          } catch (UnsupportedEncodingException e1) {
	              e1.printStackTrace();
	          }
	       
	          String[] titles = { "�Ծ�id", "�Ծ�����", "�û���","�ɼ�","�Ƿ����", "�ύʱ��" }; 
	          List<TestGrade> list=new ArrayList<>();
	          list=service.selectTestGrade(testid);
	          ExcelImpl  excleimpl=new ExcelImpl();
	          excleimpl.export(titles,out,list);      
	          return "success";
	      } catch(Exception e){
	          e.printStackTrace();
	          return "������Ϣʧ��";
	      }
	  }
	/*
	 * ʵ��������
	 */
	@RequestMapping("RandomTest.do")
	public String doRandomTest(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		TestRecord tr;
		String testname=request.getParameter("testname");
		String description=request.getParameter("description");
		String start=request.getParameter("start");
		String ends=request.getParameter("ends");
		String score=request.getParameter("score");
		int sc=Integer.parseInt(score);
		int count=sc/5;
		List<Question> list=new ArrayList<>();
		list=service.selectQuestion();
		String libraryid=new RandomTest().Test(list,count);
		//�ַ���ת����timestamp��
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Timestamp st=new Timestamp(dateFormat.parse(start).getTime());
		Timestamp ed=new Timestamp(dateFormat.parse(ends).getTime());
		//�ȿ�ʼʱ��ͽ���ʱ���С
		if(dateFormat.parse(start).getTime()<dateFormat.parse(ends).getTime()) {
			if("".equals(description)) {
				tr=new TestRecord(0,testname,"null",st,ed,libraryid,"δ����","0");
			}else {
				tr=new TestRecord(0,testname,description,st,ed,libraryid,"δ����","0");
			}
			service.addTestRecord(tr);
			return"forward:/TestRecordManager.do";
		}else {
			request.setAttribute("error", "��ʼʱ�䲻����Ƚ���ʱ����");
		}
		request.setAttribute("testname",testname);
		request.setAttribute("description", description);
		return "TestRecordRandom";
	}
	
}
	

