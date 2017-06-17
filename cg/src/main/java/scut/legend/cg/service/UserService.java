package scut.legend.cg.service;

import java.util.Set;

import scut.legend.cg.po.User;

public interface UserService {

	User getUserById(Integer id);

	User findByUsername(String username);

	Set<String> findRoles(String username);

	Set<String> findPermissions(String username);

}
