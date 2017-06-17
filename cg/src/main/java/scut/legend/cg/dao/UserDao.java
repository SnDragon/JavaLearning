package scut.legend.cg.dao;

import java.util.List;
import java.util.Set;

import scut.legend.cg.po.User;

public interface UserDao {

	User getUserById(Integer id);

	User findByUsername(String username);

	List<String> findRoles(String username);

	List<String> findPermissions(String username);

}
