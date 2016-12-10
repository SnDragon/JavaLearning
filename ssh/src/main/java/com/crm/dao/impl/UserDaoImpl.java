package com.crm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.crm.dao.UserDao;
import com.crm.model.User;



@Repository("userDao")
public class UserDaoImpl implements UserDao{
	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public User findUserByName(String username) {
		String jpql="select u from User u where u.name=?0";
		Query query=em.createQuery(jpql).setParameter(0, username);
		
		List<User> userList=(List<User>)query.getResultList();
		User user=null;
		if(userList.size()!=0){
			System.out.println(userList.size());
			user=userList.get(0);
		}
		System.out.println(user);
		return user;
	}
	
}
