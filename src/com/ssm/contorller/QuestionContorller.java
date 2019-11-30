package com.ssm.contorller;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssm.entity.Error;
import com.ssm.entity.ExcelImport;
import com.ssm.entity.ExcelTemplet;
import com.ssm.entity.Question;
import com.ssm.entity.TestRecord;
import com.ssm.service.QuestionService;

/**
 * Function:ִ�������ɾ�Ĳ�Ĳ���
 * @author Aaron
 * Date:2019.4.15
 */
@Controller
public class QuestionContorller {
	@Resource(name="questionService")
	QuestionService service;
	String title;
	String type;
	String start;
	String ends;
	int pagecount=0;//��ҳ��
	int Testid=0;//�Ծ�id
	String libraryid="";//�Ծ��������Ӧ���ݿ�ı��
	TestRecord tr;
	/*
	 * ��ѯ�����������
	 */
	@RequestMapping("QuestionManager.do")
	public String doQuestionManager(HttpServletRequest request,int currentpage)throws Exception {
		request.setCharacterEncoding("utf-8");
		int count=0;//��������
		List<Question> list1=new ArrayList<Question>();
		list1=service.selectQuestion();
		//����list���������ܺ�
		for(int i=1;i<=list1.size();i++) {
			count=i++;
		}
		if(count%10==0) {
			pagecount=count/10-1;
		}else {
			pagecount=count/10;
		}
		if(Testid!=0) {
			request.setAttribute("hint", "�Ծ�id�󶨳ɹ������������");
		}else {
			request.setAttribute("error", "�Ծ�id��ʧ�ܣ������°�");
		}
		List<Question> list=new ArrayList<Question>();
		list=service.selectQuestionLimit(currentpage*10,10);
		request.setAttribute("Testid",Testid);
		request.setAttribute("list", list);
		request.setAttribute("pagecount", pagecount);
		request.setAttribute("currentpage", currentpage);
		request.setAttribute("page", "QuestionManager.do?");		
		return "QuestionList";
	}
	/*
	 * �鿴����
	 */
	@RequestMapping("LookQuestion.do")
	public String doLookQuestion(HttpServletRequest request,int tid)throws Exception {
		request.setCharacterEncoding("utf-8");
		Question question=service.findQuestionBytid(tid);
		request.setAttribute("question", question);
		return "QuestionView";
	}
	/*
	 * ɾ������
	 */
	@RequestMapping("deleteQuestion.do")
	public String dodeleteQuestion(HttpServletRequest request,int tid)throws Exception{
		request.setCharacterEncoding("utf-8");
		service.deleteQuestionBytid(tid);
		return"forward:/QuestionManager.do?currentpage=0";
	}
	/*
	 * �������
	 */
	@RequestMapping("AddQuestion.do")
	public String doAddQuestion(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		title=request.getParameter("title");
		type=request.getParameter("type");
		String optionA=request.getParameter("optionA");
		String optionB=request.getParameter("optionB");
		String optionC=request.getParameter("optionC");
		String optionD=request.getParameter("optionD");
		String optionE=request.getParameter("optionE");
		String score=request.getParameter("score");
		String description=request.getParameter("description");
		String[] ct=request.getParameterValues("correct");
		//ѡ�����
		int option=ct.length;
		//���ַ���������ַ���
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < ct.length; i++){
		 sb. append(ct[i]);
		}
		String correct=sb.toString();
		//����д����ʱ ��ǰ̨��������
		request.setAttribute("title", title);
		request.setAttribute("optionA",optionA);
		request.setAttribute("optionB", optionB);
		request.setAttribute("optionC", optionC);
		request.setAttribute("optionD", optionD);
		request.setAttribute("description", description);
		//�ж�score�Ƿ�Ϊ����
		boolean flag=true;
		for (int i=0;i<score.length();i++){  
			if (!Character.isDigit(score.charAt(i))){
		        flag= false;
			}     
		}
		
			//��ѡ��ʱ
			if("��ѡ".equals(type)) {
				if(option==1) {
					if(!"E".equals(ct[0])) {
						if(!flag) {
							request.setAttribute("errors", "��ֵ��ʽ����");
						}else {
							Date date=new Date();
							Question question=new Question(0,type,title,optionA,optionB,optionC,optionD,"null",ct[0],score,description,new Timestamp(date.getTime()));
							service.addQuestion(question);
							request.setAttribute("error", "��ӳɹ�");
							return"forward:/QuestionManager.do?currentpage=0";
						}
					}else {
						request.setAttribute("errors", "��ѡ�𰸲���ΪE");
					}
				}else if(option>1) {
					request.setAttribute("errors", "��ѡ���ܴ��ڶ��ѡ��");
				}
				//ǰ̨��������
				request.setAttribute("score", score);	
			}
			//��ѡ��ʱ
			if("��ѡ".equals(type)) {
				if(!"".equals(optionE)) {
					if(option>1){
						if(!flag) {
							request.setAttribute("errors", "��ֵ��ʽ����");
						}else {
							Date date=new Date();
							Question question=new Question(0,type,title,optionA,optionB,optionC,optionD,optionE,correct,score,description,new Timestamp(date.getTime()));
							service.addQuestion(question);
							request.setAttribute("error", "��ӳɹ�");
							return"forward:/QuestionManager.do?currentpage=0";
						}
					}else {
						request.setAttribute("errors", "����Ϊ��ѡʱ����ȷ�𰸲�������������");
					}
				}else {
					request.setAttribute("errors", "����Ϊ��ѡʱ��ѡ��E����Ϊ��");
					
				}
				request.setAttribute("score", score);
				request.setAttribute("optionE",optionE);
				//ǰ̨�������������õ�ѡ��Ĭ��ֵ
				request.setAttribute("type1", "");
				request.setAttribute("type2", "checked");
			}
		
		return "QuestionAdd";
	}
	/*
	 * 1.������Ŀ����ȫ������
	 * 2.������Ŀ������Ŀ��������
	 * 3.��Ŀ����ֱ������
	 */
	@RequestMapping("LookLibrary.do")
	public String doLookLibrary(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		title=request.getParameter("title");
		type=request.getParameter("type");
		int count=0;//��������
		if("".equals(title)){
			//������Ŀ����ֱ������
			if("ȫ��".equals(type)) {
				return "forward:/QuestionManager.do?currentpage=0"; 
			}else{
				List<Question> list1=new ArrayList<Question>();
				list1=service.findQuestionByType(type);
				//����list���������ܺ�
				for(int i=1;i<=list1.size();i++) {
					count=i++;
				}
				if(count%10==0) {
					pagecount=count/10;
				}else {
					pagecount=count/10+1;
				}
			}
		}else {
			//������Ŀ�����Ͳ���
			if("ȫ��".equals(type)) {
				List<Question> list1=new ArrayList<Question>();
				list1=service.findQuestionBytitle(title);
				//����list���������ܺ�
				for(int i=1;i<=list1.size();i++) {
					count=i++;
				}
				if(count%10==0) {
					pagecount=count/10;
				}else {
					pagecount=count/10+1;
				}
			}else {
				
				List<Question> list1=new ArrayList<Question>();
				list1=service.findQuestionBytt(title,type);
				//����list���������ܺ�
				for(int i=1;i<=list1.size();i++) {
					count=i++;
				}
				if(count%10==0) {
					pagecount=count/10;
				}else {
					pagecount=count/10+1;
				}
			}
		}
		
		return "forward:/LookQuestionByLimit.do?currentpage=0";
	}
	@RequestMapping("LookQuestionByLimit.do")
	public String doLookQuestionByLimit(HttpServletRequest request,int currentpage)throws Exception{
		request.setCharacterEncoding("utf-8");
		List<Question> list=new ArrayList<Question>();
		if("".equals(title)) {
			list=service.lookQuestionBytype(type,currentpage*10,10);
		}else {
			if("ȫ��".equals(type)) {
				list=service.lookQuestionBytitle(title,currentpage*10,10);
			}else {
				list=service.lookQuestion(title,type,currentpage*10,10);
			}
		}
		request.setAttribute("list", list);
		request.setAttribute("pagecount", pagecount);
		request.setAttribute("currentpage", currentpage);
		request.setAttribute("page", "LookQuestionByLimit.do?");		
		return "QuestionList";
	}
	/*
	 * ����д���������ɸѡ
	 */
	@RequestMapping("LookLibraryByDate.do")
	public String doLookLibraryByDate(HttpServletRequest request) {
		int count=0;
		start=request.getParameter("start");
		ends=request.getParameter("ends");
		if(!"".equals(start) && !"".equals(ends)) {
			List<Question> list1=new ArrayList<Question>();
			list1=service.findQuestionByDate(start,ends);
			for(int i=1;i<=list1.size();i++) {
				count=i++;
			}
			if(count%10==0) {
				pagecount=count/10;
			}else {
				pagecount=count/10+1;
			}
		}else {
			request.setAttribute("error", "���ڿ���Ϊ��");
			return"forward:/QuestionManager.do?currentpage=0";
		}
		return "forward:/LookLibraryLimit.do?currentpage=0";
	}
	/*
	 * ����ҳ��ѯ
	 */
	 @RequestMapping("LookLibraryLimit.do")
	 public String doLookLibraryLimit(HttpServletRequest request,int currentpage)throws Exception {
		request.setCharacterEncoding("utf-8");
		List<Question> list=new ArrayList<Question>();
		list=service.lookLibraryLimit(start,ends,currentpage*10,10);
		request.setAttribute("list", list);
		request.setAttribute("pagecount", pagecount);
		request.setAttribute("currentpage", currentpage);
		request.setAttribute("page", "LookLibraryLimit.do?");	
		return "QuestionList";
	 }
	 
	 /*
	  * �����Ծ�����ݵ�testid ��testid
	  */
	 @RequestMapping("AddQuestionsByid.do")
	 public String doAddQuestion(HttpServletRequest request,int id)throws Exception {
		 
		 tr=service.selectTestRecordByid(id);
			 libraryid=tr.getLibraryid();
		 if("�ѷ���".equals(tr.getPublish())) {
			 request.setAttribute("error1", "�Ծ��ѷ������޷���������!");
			 Testid=0;
		 }else {
			 Testid=id;
		 }
		 String st="forward:/QuestionManager.do?currentpage=0";
		 return st;
	 }
	 /*
	  * ��������������
	 */
	 @RequestMapping("AddTest.do")
	 public String doAddTest(HttpServletRequest request,int tid,String page,int currentpage)throws Exception {
		 request.setCharacterEncoding("utf-8");
		 String s=String.valueOf(tid);
		 if(!libraryid.contains(s)) {
			 libraryid=libraryid+s+",";
			 if("�ѷ���".equals(tr.getPublish())) {
				 request.setAttribute("error1", "�Ծ��ѷ������޷��������!!!");
			 }else {
				 service.updateTestRecordBytestid(Testid,libraryid);
			 }
		 }else {
			 request.setAttribute("error", "�������ʧ�ܣ��Ծ�����ظ�����");
		 }
		 String st="forward:/"+page+"currentpage="+currentpage;
		 return st;
	 }
	 /*
	  * �����޸�
	  */
	 @RequestMapping("AlterQuestion.do")
	 public String doAlterQuestion(HttpServletRequest request,int tid)throws Exception{
		 request.setCharacterEncoding("utf-8");
		 Question question=service.findQuestionBytid(tid);
		 //���õ�ѡ ��ѡ��ťĬ��
		 if("��ѡ".equals(question.getType())) {
			 request.setAttribute("type1","checked");
		 }else {
			 request.setAttribute("type2", "checked");
		 }
		 //����ABCDEѡ��Ĭ�Ϲ�ѡ
		 if(question.getCorrect().contains("A")){
			 request.setAttribute("correct1","checked");
		 }
		 if(question.getCorrect().contains("B")){
			 request.setAttribute("correct2","checked");
		 }
		 if(question.getCorrect().contains("C")){
			 request.setAttribute("correct3","checked");
		 }
		 if(question.getCorrect().contains("D")){
			 request.setAttribute("correct4","checked");
		 }
		 if(question.getCorrect().contains("E")){
			 request.setAttribute("correct5","checked");
		 }
		 
		 request.setAttribute("question", question);
		 return "QuestionAlter";
	 }
	 /*
	  * �޸ĺ󱣴�
	  */
	 @RequestMapping("AlterQuestionBytid.do")
	 public String doAlterQuestionBytid(HttpServletRequest request,int tid)throws Exception{
		 request.setCharacterEncoding("utf-8");
		 title=request.getParameter("title");
			type=request.getParameter("type");
			String optionA=request.getParameter("optionA");
			String optionB=request.getParameter("optionB");
			String optionC=request.getParameter("optionC");
			String optionD=request.getParameter("optionD");
			String optionE=request.getParameter("optionE");
			String score=request.getParameter("score");
			String description=request.getParameter("description");
			String[] ct=request.getParameterValues("correct");
			//ѡ�����
			int option=ct.length;
			//���ַ���������ַ���
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < ct.length; i++){
			 sb. append(ct[i]);
			}
			String correct=sb.toString();
			//����д����ʱ ��ǰ̨��������
			Question question1=new Question(title,optionA,optionB,optionC,optionD,optionE,score,description);
			request.setAttribute("question", question1);
			//�ж�score�Ƿ�Ϊ����
			boolean flag=true;
			for (int i=0;i<score.length();i++){  
				if (!Character.isDigit(score.charAt(i))){
			        flag= false;
				}     
			}
			if("��ѡ".equals(type)) {
				 request.setAttribute("type1","checked");
			 }else {
				 request.setAttribute("type2", "checked");
			 }
				//��ѡ��ʱ
				if("��ѡ".equals(type)) {
					if(option==1) {
						if(!"E".equals(ct[0])) {
							if(!flag) {
								request.setAttribute("errors", "��ֵ��ʽ����");
							}else {
								Date date=new Date();
								Question question=new Question(tid,type,title,optionA,optionB,optionC,optionD,"null",ct[0],score,description,new Timestamp(date.getTime()));
								service.updateQuestionBytid(question);
								request.setAttribute("error", "�޸ĳɹ�");
								return"forward:/QuestionManager.do?currentpage=0";
							}
						}else {
							request.setAttribute("errors", "��ѡ�𰸲���ΪE");
						}
					}else if(option>1) {
						request.setAttribute("errors", "��ѡ���ܴ��ڶ��ѡ��");
					}
				}
				//��ѡ��ʱ
				if("��ѡ".equals(type)) {
					if(!"".equals(optionE)) {
						if(option>1){
							if(!flag) {
								request.setAttribute("errors", "��ֵ��ʽ����");
							}else {
								Date date=new Date();
								Question question=new Question(tid,type,title,optionA,optionB,optionC,optionD,optionE,correct,score,description,new Timestamp(date.getTime()));
								service.updateQuestionBytid(question);
								request.setAttribute("error", "�޸ĳɹ�");
								return"forward:/QuestionManager.do?currentpage=0";
							}
						}else {
							request.setAttribute("errors", "����Ϊ��ѡʱ����ȷ�𰸲�������������");
						}
					}else {
						request.setAttribute("errors", "����Ϊ��ѡʱ��ѡ��E����Ϊ��");
						
					}
				}
			
			return "QuestionAlter";
	 }
	 
	 /*
	  * ����Excelģ��
	  */
	 @RequestMapping("DownExcelTemplet.do")
	 public void doQuestionImport(HttpServletResponse response)throws Exception{
		 response.setContentType("application/binary;charset=UTF-8");     		
  		response.setCharacterEncoding("utf-8");  
           try{
               ServletOutputStream out=response.getOutputStream();
               try {
                   //�����ļ�ͷ�����һ�����������������ļ���
                   response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("ExcelTemplet"+".xls", "UTF-8"));
               } catch (UnsupportedEncodingException e1) {
                   e1.printStackTrace();
               }
               String[] titles = { "��������", "��Ŀ", "ѡ��A","ѡ��B","ѡ��C", "ѡ��D","ѡ��E","��ȷ��","��ֵ","��������" }; 
               ExcelTemplet et=new ExcelTemplet();        
               et.export(titles,out);      
           } catch(Exception e){
               e.printStackTrace();     
           }
	 }
	 
	 /*
	  * ������������
	  */
	@RequestMapping("ExcelImport.do")
	 public String doExcelImport(HttpServletRequest request)throws Exception{
		 request.setCharacterEncoding("utf-8");
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		 MultipartFile file = multipartRequest.getFile("file");
		 String filename=file.getOriginalFilename();
		 //�ж��ǲ���Excel
		 if(filename.endsWith(".xls") || filename.endsWith(".xlsx")){  
			 //ת����������
            InputStream in = file.getInputStream();
           
            List<List<Object>> list=new ExcelImport().getBankListByExcel(in, filename);
            
            //��¼����ʧ�ܵ���Ϣ
            List<Error> list2=new ArrayList<>();
            int count=list.size();
            //��Excel���ݽ����ж� ѡ���Բ���
            for(int i=0;i<list.size();i++) {
            	String type=(String)list.get(i).get(0);
            	String title=(String)list.get(i).get(1);
            	String optionA=(String)list.get(i).get(2);
            	String optionB=(String)list.get(i).get(3);
            	String optionC=(String)list.get(i).get(4);
            	String optionD=(String)list.get(i).get(5);
            	String optionE=(String)list.get(i).get(6);
            	String correct=(String)list.get(i).get(7);
            	String score=(String)list.get(i).get(8);
            	String description=(String)list.get(i).get(9);
            	Date date=new Date();
            	if("��ѡ".equals(list.get(i).get(0))) {
            		
            		Question question=new Question(0,type,title,optionA,optionB,optionC,optionD,"null",correct,score,description,new Timestamp(date.getTime()));
            		service.addQuestion(question);
            	}else if("��ѡ".equals(list.get(i).get(0))) {
            		Question question=new Question(0,type,title,optionA,optionB,optionC,optionD,optionE,correct,score,description,new Timestamp(date.getTime()));
            		service.addQuestion(question);
            	}else {
            		count--;
            		Error error=new Error(i+1,"�������͸�ʽ����ȷ");
            		list2.add(error);
            	}
            }
            request.setAttribute("hint", "������"+count+"�����⣡");
			request.setAttribute("list2", list2);    
		 }else {
			 request.setAttribute("error", "�����ļ�����Excel��");
		 }
	
		return "QuestionImport";
		 
	 } 
}
