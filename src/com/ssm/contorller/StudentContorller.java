package com.ssm.contorller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssm.entity.Question;
import com.ssm.entity.TestGrade;
import com.ssm.entity.TestRecord;
import com.ssm.entity.User;
import com.ssm.service.StudentService;

/**
 * Function:实现学生端答卷的类
 * @author Aaron
 * Date:2019.4.21
 */
@Controller
public class StudentContorller {
	@Resource(name="studentService")
	StudentService service;
	TestRecord testrecord;
	int testid=0;
	String name;
	/*
	 * 修改密码
	 */
	@RequestMapping("password.do")
	public String dopassword(HttpServletRequest request,String username)throws Exception {
		System.out.println(username);
		request.setCharacterEncoding("utf-8");	 
		request.setAttribute("username", username);
		return "Spassword";
	}
	
	@RequestMapping("SUpdatePassword.do")
	public String doSUpdatePassword(HttpServletRequest request,String username)throws Exception {
		request.setCharacterEncoding("utf-8");
		String oldPassword=request.getParameter("oldPassword");
		String newPassword=request.getParameter("newPassword");
		String reNewPassword=request.getParameter("reNewPassword");
		User user=service.findUserByusername(username);
		if(user.getPassword().equals(oldPassword)){
				if(newPassword.equals(reNewPassword)) {
					service.userUpdate(user.getUsername(),newPassword);
					request.setAttribute("news","密码修改成功！请重新登录");
					request.setAttribute("username", user.getUsername());
					return "index";
				}else {
					request.setAttribute("error", "两次密码输入不一致！");
				}
			}else {
				request.setAttribute("error", "原密码错误！");
			}
		request.setAttribute("username", username);
		return "Spassword";
	}
	/*
	 *显示试卷
	 */
	@RequestMapping("OnlineTest.do")
	public String doOnlineTest(HttpServletRequest request,String username)throws Exception{
		request.setCharacterEncoding("utf-8");
		name=username;
		List<TestRecord> list=new ArrayList<>();
		List<TestGrade> list1=new ArrayList<>();
		list=service.findTestRecordBypublish("已发布");
		for(int i=0;i<list.size();i++) {
			int testid=list.get(i).getTestid();
			TestGrade testgrade=service.findTestGradeBytestid(testid,username);
			list1.add(testgrade);
		}
		request.setAttribute("username", username);
		request.setAttribute("list1", list1);
		request.setAttribute("list", list);
		return "SonlineTestList";
	}
	/*
	 * 答题
	 */
	@RequestMapping("AnswertheTest.do")
	public String doAnswertheTest(HttpServletRequest request,int id,String username)throws Exception {
		request.setCharacterEncoding("utf-8");
		testid=id;
		name=username;
		List<Question> list=new ArrayList<>();
		TestGrade testgrade1=service.findTestGradeBytestid(id,username);
		TestRecord testrecord2=service.selectTestRecordByid(id);
		String libraryid=testrecord2.getLibraryid();
		String[] str=libraryid.split(",");
		for(int i=0;i<str.length;i++) {
			int tid=Integer.parseInt(str[i]);
			Question question=service.findQuestionBytid(tid);
			list.add(question);
		}
		if("未完成".equals(testgrade1.getFlag())) {
			Date date=new Date();
			Timestamp currenttime=new Timestamp(date.getTime());
			if(currenttime.before(testrecord2.getStart())) {
				request.setAttribute("hint", "还未到开放答题时间！");
				
				return "forward:OnlineTest.do";
			}else 
				if(currenttime.after(testrecord2.getEnds())) {
					request.setAttribute("hint", "答题时间已过！");
					
					return "forward:OnlineTest.do";
				
			}
		}else {
			request.setAttribute("hint", "你已经完成答题，本次答题不记入成绩！");
		}
		request.setAttribute("list", list);
		request.setAttribute("testid", id);
		request.setAttribute("username",username);
		request.setAttribute("testname", testgrade1.getTestname());
		return "Test";
	}
	
	@RequestMapping("CalculateTest.do")
	public String doCalculateTest(HttpServletRequest request,int testid,String username)throws Exception{
		request.setCharacterEncoding("utf-8");
		int k=0;
		int count=0;//答对个数
		int score=0;//总分
		request.setCharacterEncoding("utf-8");
		Enumeration<String> pNames=request.getParameterNames();
		List<Question> list=new ArrayList<>();
		TestRecord testrecord2=service.selectTestRecordByid(testid);
		String libraryid=testrecord2.getLibraryid();
		String[] str=libraryid.split(",");
		for(int i=0;i<str.length;i++) {
			int tid=Integer.parseInt(str[i]);
			Question question=service.findQuestionBytid(tid);
			list.add(question);
		}
		
		//这里获取属性名的时候把testid username也获取了
		while(pNames.hasMoreElements()){ 
			String name=(String)pNames.nextElement(); 
			String[] value=request.getParameterValues(name);
			
			if(k>1&&value!=null) {
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < value.length; i++){
					 sb. append(value[i]);
				}
				String s = sb.toString();
				if(s.equals(list.get(k-2).getCorrect())){
					score=score+Integer.parseInt(list.get(k-2).getScore());
					count++;
				}
			}
			k++;
		//ACB	
		}

		TestGrade testgrade1=service.findTestGradeBytestid(testid,username);
		if("未完成".equals(testgrade1.getFlag())) {
			Date date=new Date();
			service.updateTestGrade(testid,username,score,"已完成",new Timestamp(date.getTime()));
			request.setAttribute("hint", "恭喜您，本次答对"+count+"道题，总分"+score+"你已完成答题，本次答题计入成绩");
		}else {
			request.setAttribute("hint", "恭喜您，本次答对"+count+"道题，总分"+score+"鉴于您之前已完成答题，本次答题不计入成绩");
		}
		String st="forward:OnlineTest.do";
		return st;
	}
	/*
	 * 学生端成绩查询
	 */
	@RequestMapping("InquiryTestGrade.do")
	public String doInquiryTestGrade(HttpServletRequest request, String username)throws Exception{
		request.setCharacterEncoding("utf-8");
		List<TestGrade> list=new ArrayList<>();
		list=service.inquiryTestGradeByusername(username);
		request.setAttribute("list", list);
		request.setAttribute("username", username);
		return"STestGrade";
	}
}
