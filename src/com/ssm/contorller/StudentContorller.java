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
 * Function:ʵ��ѧ���˴�����
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
	 * �޸�����
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
					request.setAttribute("news","�����޸ĳɹ��������µ�¼");
					request.setAttribute("username", user.getUsername());
					return "index";
				}else {
					request.setAttribute("error", "�����������벻һ�£�");
				}
			}else {
				request.setAttribute("error", "ԭ�������");
			}
		request.setAttribute("username", username);
		return "Spassword";
	}
	/*
	 *��ʾ�Ծ�
	 */
	@RequestMapping("OnlineTest.do")
	public String doOnlineTest(HttpServletRequest request,String username)throws Exception{
		request.setCharacterEncoding("utf-8");
		name=username;
		List<TestRecord> list=new ArrayList<>();
		List<TestGrade> list1=new ArrayList<>();
		list=service.findTestRecordBypublish("�ѷ���");
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
	 * ����
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
		if("δ���".equals(testgrade1.getFlag())) {
			Date date=new Date();
			Timestamp currenttime=new Timestamp(date.getTime());
			if(currenttime.before(testrecord2.getStart())) {
				request.setAttribute("hint", "��δ�����Ŵ���ʱ�䣡");
				
				return "forward:OnlineTest.do";
			}else 
				if(currenttime.after(testrecord2.getEnds())) {
					request.setAttribute("hint", "����ʱ���ѹ���");
					
					return "forward:OnlineTest.do";
				
			}
		}else {
			request.setAttribute("hint", "���Ѿ���ɴ��⣬���δ��ⲻ����ɼ���");
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
		int count=0;//��Ը���
		int score=0;//�ܷ�
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
		
		//�����ȡ��������ʱ���testid usernameҲ��ȡ��
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
		if("δ���".equals(testgrade1.getFlag())) {
			Date date=new Date();
			service.updateTestGrade(testid,username,score,"�����",new Timestamp(date.getTime()));
			request.setAttribute("hint", "��ϲ�������δ��"+count+"���⣬�ܷ�"+score+"������ɴ��⣬���δ������ɼ�");
		}else {
			request.setAttribute("hint", "��ϲ�������δ��"+count+"���⣬�ܷ�"+score+"������֮ǰ����ɴ��⣬���δ��ⲻ����ɼ�");
		}
		String st="forward:OnlineTest.do";
		return st;
	}
	/*
	 * ѧ���˳ɼ���ѯ
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
