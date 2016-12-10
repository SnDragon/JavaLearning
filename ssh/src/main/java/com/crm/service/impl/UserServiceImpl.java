package com.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.crm.dao.UserDao;
import com.crm.model.User;
import com.crm.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
	private UserDao userDao;

   /**
    * 登陆验证
    * @param
    */
	public User checkLogin(String username, String password) {
		
		User user=userDao.findUserByName(username);
		if(user!=null&&user.getPassword().equals(password)){
			return user;
		}
		return null;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
