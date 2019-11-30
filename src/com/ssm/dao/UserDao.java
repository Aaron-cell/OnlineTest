package com.ssm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssm.entity.User;

/**
 * Function:定义用户操作接口
 * @author Aaron
 * Date:2019.4.11
 */
@Repository("userDao")
public interface UserDao {
	
	User FindUser(String username, String password);

	User FindUserById(String username);

	void AlterUser(User user);

	void UserUpdate(String username, String newPassword);

	List<User> SelectUser();

	void DeleteUser(String username);

	List<User> InquiryUser(String username);

	List<User> SelectUserLimit(int i, int j);

	List<User> InquiryUserLimit(String username,int i, int j);

}
