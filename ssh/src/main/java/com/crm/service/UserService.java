package com.crm.service;

import com.crm.model.*;


public interface UserService {
	
   public User checkLogin(String username,String password);
}
