package scut.legend.cg.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.stereotype.Service;

import scut.legend.cg.dao.UserDao;
import scut.legend.cg.po.User;
import scut.legend.cg.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	
	@Override
	public User getUserById(Integer id) {
		return userDao.getUserById(id);
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public Set<String> findRoles(String username) {
		List<String> roleList = userDao.findRoles(username);
		Set<String>  roleSet=new HashSet<>();
		for(String role:roleList){
			roleSet.add(role);
		}
		return roleSet;
	}

	@Override
	public Set<String> findPermissions(String username) {
		List<String> permsList=userDao.findPermissions(username);
		Set<String> permsSet=new HashSet<>();
		for(String permission:permsList){
			permsSet.add(permission);
		}
		return permsSet;
	}

}
