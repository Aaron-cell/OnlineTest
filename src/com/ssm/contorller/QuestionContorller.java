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
 * Function:执行题库增删改查的操作
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
	int pagecount=0;//总页数
	int Testid=0;//试卷id
	String libraryid="";//试卷中试题对应数据库的编号
	TestRecord tr;
	/*
	 * 查询题库所有试题
	 */
	@RequestMapping("QuestionManager.do")
	public String doQuestionManager(HttpServletRequest request,int currentpage)throws Exception {
		request.setCharacterEncoding("utf-8");
		int count=0;//数据总数
		List<Question> list1=new ArrayList<Question>();
		list1=service.selectQuestion();
		//遍历list集合数据总和
		for(int i=1;i<=list1.size();i++) {
			count=i++;
		}
		if(count%10==0) {
			pagecount=count/10-1;
		}else {
			pagecount=count/10;
		}
		if(Testid!=0) {
			request.setAttribute("hint", "试卷id绑定成功！请添加试题");
		}else {
			request.setAttribute("error", "试卷id绑定失败！请重新绑定");
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
	 * 查看试题
	 */
	@RequestMapping("LookQuestion.do")
	public String doLookQuestion(HttpServletRequest request,int tid)throws Exception {
		request.setCharacterEncoding("utf-8");
		Question question=service.findQuestionBytid(tid);
		request.setAttribute("question", question);
		return "QuestionView";
	}
	/*
	 * 删除试题
	 */
	@RequestMapping("deleteQuestion.do")
	public String dodeleteQuestion(HttpServletRequest request,int tid)throws Exception{
		request.setCharacterEncoding("utf-8");
		service.deleteQuestionBytid(tid);
		return"forward:/QuestionManager.do?currentpage=0";
	}
	/*
	 * 添加试题
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
		//选项个数
		int option=ct.length;
		//将字符串数组变字符串
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < ct.length; i++){
		 sb. append(ct[i]);
		}
		String correct=sb.toString();
		//当填写错误时 向前台发送数据
		request.setAttribute("title", title);
		request.setAttribute("optionA",optionA);
		request.setAttribute("optionB", optionB);
		request.setAttribute("optionC", optionC);
		request.setAttribute("optionD", optionD);
		request.setAttribute("description", description);
		//判断score是否为数字
		boolean flag=true;
		for (int i=0;i<score.length();i++){  
			if (!Character.isDigit(score.charAt(i))){
		        flag= false;
			}     
		}
		
			//单选题时
			if("单选".equals(type)) {
				if(option==1) {
					if(!"E".equals(ct[0])) {
						if(!flag) {
							request.setAttribute("errors", "分值格式错误");
						}else {
							Date date=new Date();
							Question question=new Question(0,type,title,optionA,optionB,optionC,optionD,"null",ct[0],score,description,new Timestamp(date.getTime()));
							service.addQuestion(question);
							request.setAttribute("error", "添加成功");
							return"forward:/QuestionManager.do?currentpage=0";
						}
					}else {
						request.setAttribute("errors", "单选答案不能为E");
					}
				}else if(option>1) {
					request.setAttribute("errors", "单选不能存在多个选项");
				}
				//前台发送数据
				request.setAttribute("score", score);	
			}
			//多选题时
			if("多选".equals(type)) {
				if(!"".equals(optionE)) {
					if(option>1){
						if(!flag) {
							request.setAttribute("errors", "分值格式错误");
						}else {
							Date date=new Date();
							Question question=new Question(0,type,title,optionA,optionB,optionC,optionD,optionE,correct,score,description,new Timestamp(date.getTime()));
							service.addQuestion(question);
							request.setAttribute("error", "添加成功");
							return"forward:/QuestionManager.do?currentpage=0";
						}
					}else {
						request.setAttribute("errors", "题型为多选时，正确答案不得少于两个！");
					}
				}else {
					request.setAttribute("errors", "题型为多选时，选项E不能为空");
					
				}
				request.setAttribute("score", score);
				request.setAttribute("optionE",optionE);
				//前台绑定数据用于设置单选框默认值
				request.setAttribute("type1", "");
				request.setAttribute("type2", "checked");
			}
		
		return "QuestionAdd";
	}
	/*
	 * 1.根据题目名称全部搜索
	 * 2.根据题目名称题目类型搜索
	 * 3.题目类型直接搜索
	 */
	@RequestMapping("LookLibrary.do")
	public String doLookLibrary(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		title=request.getParameter("title");
		type=request.getParameter("type");
		int count=0;//数据总数
		if("".equals(title)){
			//根据题目类型直接搜索
			if("全部".equals(type)) {
				return "forward:/QuestionManager.do?currentpage=0"; 
			}else{
				List<Question> list1=new ArrayList<Question>();
				list1=service.findQuestionByType(type);
				//遍历list集合数据总和
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
			//根据题目和类型查找
			if("全部".equals(type)) {
				List<Question> list1=new ArrayList<Question>();
				list1=service.findQuestionBytitle(title);
				//遍历list集合数据总和
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
				//遍历list集合数据总和
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
			if("全部".equals(type)) {
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
	 * 根据写入题库日期筛选
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
			request.setAttribute("error", "日期框不能为空");
			return"forward:/QuestionManager.do?currentpage=0";
		}
		return "forward:/LookLibraryLimit.do?currentpage=0";
	}
	/*
	 * 题库分页查询
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
	  * 接收试卷管理传递的testid 绑定testid
	  */
	 @RequestMapping("AddQuestionsByid.do")
	 public String doAddQuestion(HttpServletRequest request,int id)throws Exception {
		 
		 tr=service.selectTestRecordByid(id);
			 libraryid=tr.getLibraryid();
		 if("已发布".equals(tr.getPublish())) {
			 request.setAttribute("error1", "试卷已发布，无法再做更改!");
			 Testid=0;
		 }else {
			 Testid=id;
		 }
		 String st="forward:/QuestionManager.do?currentpage=0";
		 return st;
	 }
	 /*
	  * 在题库点击添加试题
	 */
	 @RequestMapping("AddTest.do")
	 public String doAddTest(HttpServletRequest request,int tid,String page,int currentpage)throws Exception {
		 request.setCharacterEncoding("utf-8");
		 String s=String.valueOf(tid);
		 if(!libraryid.contains(s)) {
			 libraryid=libraryid+s+",";
			 if("已发布".equals(tr.getPublish())) {
				 request.setAttribute("error1", "试卷已发布，无法添加试题!!!");
			 }else {
				 service.updateTestRecordBytestid(Testid,libraryid);
			 }
		 }else {
			 request.setAttribute("error", "试题添加失败，试卷添加重复试题");
		 }
		 String st="forward:/"+page+"currentpage="+currentpage;
		 return st;
	 }
	 /*
	  * 试题修改
	  */
	 @RequestMapping("AlterQuestion.do")
	 public String doAlterQuestion(HttpServletRequest request,int tid)throws Exception{
		 request.setCharacterEncoding("utf-8");
		 Question question=service.findQuestionBytid(tid);
		 //设置单选 多选按钮默认
		 if("单选".equals(question.getType())) {
			 request.setAttribute("type1","checked");
		 }else {
			 request.setAttribute("type2", "checked");
		 }
		 //设置ABCDE选项默认勾选
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
	  * 修改后保存
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
			//选项个数
			int option=ct.length;
			//将字符串数组变字符串
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < ct.length; i++){
			 sb. append(ct[i]);
			}
			String correct=sb.toString();
			//当填写错误时 向前台发送数据
			Question question1=new Question(title,optionA,optionB,optionC,optionD,optionE,score,description);
			request.setAttribute("question", question1);
			//判断score是否为数字
			boolean flag=true;
			for (int i=0;i<score.length();i++){  
				if (!Character.isDigit(score.charAt(i))){
			        flag= false;
				}     
			}
			if("单选".equals(type)) {
				 request.setAttribute("type1","checked");
			 }else {
				 request.setAttribute("type2", "checked");
			 }
				//单选题时
				if("单选".equals(type)) {
					if(option==1) {
						if(!"E".equals(ct[0])) {
							if(!flag) {
								request.setAttribute("errors", "分值格式错误");
							}else {
								Date date=new Date();
								Question question=new Question(tid,type,title,optionA,optionB,optionC,optionD,"null",ct[0],score,description,new Timestamp(date.getTime()));
								service.updateQuestionBytid(question);
								request.setAttribute("error", "修改成功");
								return"forward:/QuestionManager.do?currentpage=0";
							}
						}else {
							request.setAttribute("errors", "单选答案不能为E");
						}
					}else if(option>1) {
						request.setAttribute("errors", "单选不能存在多个选项");
					}
				}
				//多选题时
				if("多选".equals(type)) {
					if(!"".equals(optionE)) {
						if(option>1){
							if(!flag) {
								request.setAttribute("errors", "分值格式错误");
							}else {
								Date date=new Date();
								Question question=new Question(tid,type,title,optionA,optionB,optionC,optionD,optionE,correct,score,description,new Timestamp(date.getTime()));
								service.updateQuestionBytid(question);
								request.setAttribute("error", "修改成功");
								return"forward:/QuestionManager.do?currentpage=0";
							}
						}else {
							request.setAttribute("errors", "题型为多选时，正确答案不得少于两个！");
						}
					}else {
						request.setAttribute("errors", "题型为多选时，选项E不能为空");
						
					}
				}
			
			return "QuestionAlter";
	 }
	 
	 /*
	  * 下载Excel模板
	  */
	 @RequestMapping("DownExcelTemplet.do")
	 public void doQuestionImport(HttpServletResponse response)throws Exception{
		 response.setContentType("application/binary;charset=UTF-8");     		
  		response.setCharacterEncoding("utf-8");  
           try{
               ServletOutputStream out=response.getOutputStream();
               try {
                   //设置文件头：最后一个参数是设置下载文件名
                   response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("ExcelTemplet"+".xls", "UTF-8"));
               } catch (UnsupportedEncodingException e1) {
                   e1.printStackTrace();
               }
               String[] titles = { "试题类型", "题目", "选项A","选项B","选项C", "选项D","选项E","正确答案","分值","试题描述" }; 
               ExcelTemplet et=new ExcelTemplet();        
               et.export(titles,out);      
           } catch(Exception e){
               e.printStackTrace();     
           }
	 }
	 
	 /*
	  * 批量导入试题
	  */
	@RequestMapping("ExcelImport.do")
	 public String doExcelImport(HttpServletRequest request)throws Exception{
		 request.setCharacterEncoding("utf-8");
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		 MultipartFile file = multipartRequest.getFile("file");
		 String filename=file.getOriginalFilename();
		 //判断是不是Excel
		 if(filename.endsWith(".xls") || filename.endsWith(".xlsx")){  
			 //转换成输入流
            InputStream in = file.getInputStream();
           
            List<List<Object>> list=new ExcelImport().getBankListByExcel(in, filename);
            
            //记录导入失败的信息
            List<Error> list2=new ArrayList<>();
            int count=list.size();
            //对Excel数据进行判断 选择性插入
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
            	if("单选".equals(list.get(i).get(0))) {
            		
            		Question question=new Question(0,type,title,optionA,optionB,optionC,optionD,"null",correct,score,description,new Timestamp(date.getTime()));
            		service.addQuestion(question);
            	}else if("多选".equals(list.get(i).get(0))) {
            		Question question=new Question(0,type,title,optionA,optionB,optionC,optionD,optionE,correct,score,description,new Timestamp(date.getTime()));
            		service.addQuestion(question);
            	}else {
            		count--;
            		Error error=new Error(i+1,"试题类型格式不正确");
            		list2.add(error);
            	}
            }
            request.setAttribute("hint", "共导入"+count+"条试题！");
			request.setAttribute("list2", list2);    
		 }else {
			 request.setAttribute("error", "导入文件不是Excel！");
		 }
	
		return "QuestionImport";
		 
	 } 
}
