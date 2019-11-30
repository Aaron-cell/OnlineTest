package com.ssm.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.dao.UserDao;
import com.ssm.entity.User;

/**
 * Function:用户服务类
 * @author Aaron
 * Date:2019.4.11
 */
@Service("userService")
public class UserService {
	@Resource(name="userDao")
	UserDao dao;
	/*
	 * 通过用户名 密码查询是否存在
	 */
	public User findUser(String username, String password) {
		// TODO Auto-generated method stub
		User user=dao.FindUser(username,password);
		return user;
	}
	/*
	 * 通过用户名查询用户是否存在
	 */
	public User findUserById(String username) {
		// TODO Auto-generated method stub
		User user=dao.FindUserById(username);
		return user;
	}
	/*
	 * 注册用户
	 */
	public void alterUser(User user) {
		// TODO Auto-generated method stub
		dao.AlterUser(user);
	}
	/*
	 * 修改密码
	 */
	public void userUpdate(String username, String newPassword) {
		// TODO Auto-generated method stub
		dao.UserUpdate(username,newPassword);
	}
	public List<User> selectUser() {
		// TODO Auto-generated method stub
		List<User> list=dao.SelectUser();
		return list;
	}
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		dao.DeleteUser(username);
	}
	public List<User> inquiryUser(String username) {
		// TODO Auto-generated method stub
		List<User> list=dao.InquiryUser(username);
		return list;
	}
	public List<User> selectUserLimit(int i, int j) {
		// TODO Auto-generated method stub
		List<User> list=dao.SelectUserLimit(i,j);
		return list;
	}
	public List<User> inquiryUserLimit(String username,int i, int j) {
		// TODO Auto-generated method stub
		List<User> list=dao.InquiryUserLimit(username,i,j);
		return list;
	
	}

}
