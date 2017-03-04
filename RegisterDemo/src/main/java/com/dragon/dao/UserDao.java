package com.dragon.dao;

import com.dragon.model.User;

public interface UserDao {

	int save(User user);

	int activeUser(String code);

}
