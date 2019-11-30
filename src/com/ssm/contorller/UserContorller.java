package com.ssm.contorller;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssm.entity.User;
import com.ssm.service.UserService;
/**
 * Function:实现用户登录 注册 修改密码 用户管理  退出系统
 * @author Aaron
 * Date:2019.4.11
 */
@Controller
public class UserContorller {
	@Resource(name="userService")
	UserService service;
	User user;
	String username;
	int pagecount=0;//总页数
	/*
	 * 用户登录操作
	 */
	@RequestMapping("login.do")
	public String doLogin(HttpServletRequest request)throws Exception{
		request.setCharacterEncoding("utf-8");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		user=service.findUser(username,password);
		if(user!=null) {
			request.setAttribute("hint", username);
			if("admin".equals(user.getType())) {
				HttpSession session=request.getSession();
				session.setAttribute("username", username);
				return"adminwelcome";
			}else if("counterman".equals(user.getType())){
				return"cmanwelcome";
			}else {
				//username=java.net.URLDecoder.decode(username, "utf-8");
				request.setAttribute("username", username);
				return "studentwelcome";
			}
		}else {
			request.setAttribute("error", "用户名或密码错误！");
			request.setAttribute("username", username);
		}	
		return "index";
	}
	/*
	 * 用户注册操作
	 */
	@RequestMapping("AddUser.do")
	public String doAddUser(HttpServletRequest request)throws Exception{
		request.setCharacterEncoding("utf-8");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String repassword=request.getParameter("repassword");
		if(password.equals(repassword)) {
			user=service.findUserById(username);
			if(user!=null) {
				request.setAttribute("hint", "用户名已存在！");
			}else {
				Date date=new Date();
				user=new User(username,password,"student",new Timestamp(date.getTime()));
				service.alterUser(user);
				request.setAttribute("news", "注册成功!");
				request.setAttribute("username", username);
				return "index";
			}
		}else {
			request.setAttribute("hint", "两次密码不一致！");
		}
		return "register";
	}
	/*
	 * 用户修改密码
	 */
	@RequestMapping("UpdatePassword.do")
	public String doUpdatePassword(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		String username=(String)session.getAttribute("username");
		String oldPassword=request.getParameter("oldPassword");
		String newPassword=request.getParameter("newPassword");
		String reNewPassword=request.getParameter("reNewPassword");
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
		return "password";
	}
	/*
	 * 在用户管理页面显示所有用户
	 */
	@RequestMapping("UserManager.do")
	public String doUserManager(HttpServletRequest request,int currentpage)throws Exception {
		request.setCharacterEncoding("utf-8");
		int count=0;//数据总数
		List<User> list1=new ArrayList<User>();
		list1=service.selectUser();
		//遍历list集合数据总和
		for(int i=1;i<=list1.size();i++) {
			count=i++;
		}
		if(count%10==0) {
			pagecount=count/10-1;
		}else {
			pagecount=count/10;
		}
		List<User> list=new ArrayList<User>();
		list=service.selectUserLimit(currentpage*10,10);
		request.setAttribute("list", list);
		request.setAttribute("pagecount", pagecount);
		request.setAttribute("currentpage", currentpage);
		request.setAttribute("page", "UserManager.do?");
		return "userList";
	}
	/*
	 * 查看用户信息
	 */
	@RequestMapping("LookUser.do")
	public String doLookUser(HttpServletRequest request,String username)throws Exception{
		request.setCharacterEncoding("utf-8");
		User user1=service.findUserById(username);
		request.setAttribute("user1", user1);
		return "userView";
	}
	
	/*
	 * 删除用户
	 */
	@RequestMapping("deleteUser.do")
	public String dodelete(String username,HttpServletRequest request)throws Exception{
		
		if(!username.equals(user.getUsername())) {
			service.deleteUser(username);
		}else {
			request.setAttribute("error","无法删除当前登录用户名");
		}
		//同一个控制器之中 方法相互跳转
		return"forward:/UserManager.do?currentpage=0";
	}
	/*
	 * 通过关键字查询用户名
	 */
	@RequestMapping("inquiryUser.do")
	public String doInquiryUser(HttpServletRequest request)throws Exception {
		request.setCharacterEncoding("utf-8");
		int count=0;//数据总数
		username=request.getParameter("username");
		System.out.println(username);	
		List<User> list1=new ArrayList<User>();
			list1=service.inquiryUser("username");
			//遍历list集合数据总和
			for(int i=1;i<=list1.size();i++) {
				count=i++;
			}
			if(count%10==0) {
				pagecount=count/10;
			}else {
				pagecount=count/10+1;
			}
			return "forward:/inquiryUserByLimit.do?currentpage=0";
	}
			
	@RequestMapping("inquiryUserByLimit.do")
	public String doinquiryUserByLimit(HttpServletRequest request,int currentpage)throws Exception{
		request.setCharacterEncoding("utf-8");	
		List<User> list=new ArrayList<User>();
			list=service.inquiryUserLimit(username,currentpage*10,10);
			request.setAttribute("list", list);
			request.setAttribute("username", username);
			request.setAttribute("pagecount", pagecount);
			request.setAttribute("currentpage", currentpage);
			request.setAttribute("page", "inquiryUserByLimit.do?");
			return "userList";
	}
	
	/*
	 * 添加用户 仅限管理员和业务员
	 */
	@RequestMapping("UserAdd.do")
	public String doadduser(HttpServletRequest request)throws Exception{
		request.setCharacterEncoding("utf-8");
		//
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String repassword=request.getParameter("repassword");
		String type=request.getParameter("type");
		if(!"".equals(username) && !"".equals(password) && !"".equals(repassword)) {
			if(password.equals(repassword)) {
				User user3=service.findUserById(username);
				if(user3!=null) {
					request.setAttribute("hint", "用户名已存在！");
				}else {
					Date date=new Date();
					User user4=new User(username,password,type,new Timestamp(date.getTime()));
					service.alterUser(user4);
					request.setAttribute("error", "用户添加成功！");
					return"forward:/UserManager.do?currentpage=0";
				}
				
			}else {
				request.setAttribute("hint","两次输入密码不一致！");
			}
		}else {
			request.setAttribute("hint", "输入栏不能为空！");
		}
		return "userAdd";
	}
}
