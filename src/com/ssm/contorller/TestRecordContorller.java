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
 * Function：试题控制器
 * @author Aaron
 * Date:2019.4.17
 */
@Controller
public class TestRecordContorller {
	
	@Resource(name="testRecordService")
	
	TestRecordService service;
	TestRecord testrecord;
	int pagecount=0;//总页数
	/*
	 * 查询试卷所有试题
	 */
	@RequestMapping("TestRecordManager.do")
	public String doTestManager(HttpServletRequest request)throws Exception{
		request.setCharacterEncoding("utf-8");
		List<TestRecord> list1=new ArrayList<TestRecord>();
		list1=service.selectTestRecord();
		/*
		 * 这里主要是解决一种情况：即将题库的试题添加到试卷后，题库的试题删除后，后续遗留的问题
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
	 * 创建试卷操作
	 */
	@RequestMapping("AddTestRecord.do")
	public String doAddTestRecord(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		TestRecord tr;
		String testname=request.getParameter("testname");
		String description=request.getParameter("description");
		String start=request.getParameter("start");
		String ends=request.getParameter("ends");
		//字符串转换成timestamp型
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Timestamp st=new Timestamp(dateFormat.parse(start).getTime());
		Timestamp ed=new Timestamp(dateFormat.parse(ends).getTime());
		//比开始时间和结束时间大小
		if(dateFormat.parse(start).getTime()<dateFormat.parse(ends).getTime()) {
			if("".equals(description)) {
				tr=new TestRecord(0,testname,"null",st,ed,"","未发布","0");
			}else {
				tr=new TestRecord(0,testname,description,st,ed,"","未发布","0");
			}
			service.addTestRecord(tr);
			return"forward:/TestRecordManager.do";
		}else {
			request.setAttribute("error", "开始时间不允许比结束时间晚！");
		}
		request.setAttribute("testname",testname);
		request.setAttribute("description", description);
		return "TestRecordAdd";
	}
	/*
	 * 删除试卷
	 */
	@RequestMapping("DeleteTestRecord.do")
	public String doDeleteTestRecord(HttpServletRequest request,int id)throws Exception{
		request.setCharacterEncoding("utf-8");
		service.deleteTestRecordByid(id);
		service.deleteTestGrade(id);
		return "forward:/TestRecordManager.do";
	}
	/*
	 * 发布试卷
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
		if("未发布".equals(testrecord.getPublish())) {
			if(st.before(testrecord.getStart())) {
				//true st的时间比start时间早
				service.updatePublishByid(id,"已发布");
				//将学生用户加载到成绩中
				List<User> list=new ArrayList<User>();
				list=service.findUserBytype("student");
				for(int i=0;i<list.size();i++) {
					testgrade=new TestGrade(id,testname,list.get(i).getUsername(),0,"未完成",st);
					service.addTestGrade(testgrade);
				}
			}else {
				request.setAttribute("hint", "发布时间不能比开始时间晚！请重新创建试卷！");
			}
		}else {
			request.setAttribute("hint", "试卷已发布，无需再次发布");
		}
		
		return "forward:/TestRecordManager.do";
	}
	/*
	 * 进入修改试卷页面
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
		//遍历list集合数据总和
		for(int i=1;i<=list1.size();i++) {
			count=i;
		}
		if(count%10==0) {
			pagecount=count/10-1;
		}else {
			pagecount=count/10;
		}
		//如果不做比较可能会出现下标越界
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
		if("未发布".equals(test1.getPublish())) {
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
	 * 删除试卷中的试题
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
	 * 成绩导出页面
	 */
	@RequestMapping("GradeManager.do")
	public String doGradeManager(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		List<TestRecord> list1=new ArrayList<TestRecord>();
		list1=service.selectTestRecord();
		/*
		 * 这里主要是解决一种情况：即将题库的试题添加到试卷后，题库的试题删除后，后续遗留的问题
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
	 * 将成绩导出excel
	 */
	@RequestMapping("ExportExcel.do")
	public String doExportExcel(HttpServletResponse response,@RequestParam("testid") int testid,@RequestParam("testname") String testname)throws Exception {
		response.setContentType("application/binary;charset=UTF-8");
		
		response.setCharacterEncoding("utf-8");  
	
	      try{
	          ServletOutputStream out=response.getOutputStream();
	          try {
	              //设置文件头：最后一个参数是设置下载文件名
	              response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(testid+".xls", "UTF-8"));
	          } catch (UnsupportedEncodingException e1) {
	              e1.printStackTrace();
	          }
	       
	          String[] titles = { "试卷id", "试卷名称", "用户名","成绩","是否完成", "提交时间" }; 
	          List<TestGrade> list=new ArrayList<>();
	          list=service.selectTestGrade(testid);
	          ExcelImpl  excleimpl=new ExcelImpl();
	          excleimpl.export(titles,out,list);      
	          return "success";
	      } catch(Exception e){
	          e.printStackTrace();
	          return "导出信息失败";
	      }
	  }
	/*
	 * 实现随机组卷
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
		//字符串转换成timestamp型
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Timestamp st=new Timestamp(dateFormat.parse(start).getTime());
		Timestamp ed=new Timestamp(dateFormat.parse(ends).getTime());
		//比开始时间和结束时间大小
		if(dateFormat.parse(start).getTime()<dateFormat.parse(ends).getTime()) {
			if("".equals(description)) {
				tr=new TestRecord(0,testname,"null",st,ed,libraryid,"未发布","0");
			}else {
				tr=new TestRecord(0,testname,description,st,ed,libraryid,"未发布","0");
			}
			service.addTestRecord(tr);
			return"forward:/TestRecordManager.do";
		}else {
			request.setAttribute("error", "开始时间不允许比结束时间晚！");
		}
		request.setAttribute("testname",testname);
		request.setAttribute("description", description);
		return "TestRecordRandom";
	}
	
}
	

