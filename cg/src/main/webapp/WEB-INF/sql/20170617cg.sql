DROP DATABASE IF EXISTS `cg`;
CREATE DATABASE `cg`;
USE `cg`;
-- 用户表
DROP TABLE IF EXISTS `user`;
create table `user`(
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(20) NOT NULL COMMENT '用户名',
	password VARCHAR(255) NOT NULL COMMENT '密码'
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO `user`
VALUES
(1,'admin','$shiro1$SHA-512$1$$ujJTh2rta8ItSm/1PYQGxq2GQZXtFEq1yHYhtsIztUi66uaVbfNG7IwX9eoQ817jy8UUeX7X3dMUVGTioLq0Ew=='),
(2,'user','$shiro1$SHA-512$1$$ujJTh2rta8ItSm/1PYQGxq2GQZXtFEq1yHYhtsIztUi66uaVbfNG7IwX9eoQ817jy8UUeX7X3dMUVGTioLq0Ew==');

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role`(
	id INT PRIMARY KEY AUTO_INCREMENT,
	role_name VARCHAR(100) NOT NULL COMMENT '角色名称',
	role_description VARCHAR(100) NULL COMMENT '角色描述'
)	ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO role
VALUES(1,'admin','超级管理员'),(2,'user','普通用户');

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`(
	id INT PRIMARY KEY AUTO_INCREMENT,
	user_id INT NOT NULL,
	role_id INT NOT NULL
)	ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO user_role(user_id,role_id)
VALUES(1,1),(2,2);

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`(
	id INT PRIMARY KEY AUTO_INCREMENT,
	permission_name VARCHAR(100) NOT NULL COMMENT '权限名',
	permission_description VARCHAR(100) NULL COMMENT '权限描述'
)	ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO permission
VALUES
(1,'user:view','查看用户信息'),
(2,'user:create','创建用户'),
(3,'user:update','修改用户信息'),
(4,'user:delete','删除用户');

DROP TABLE IF EXISTS role_permission;
CREATE TABLE `role_permission`(
	id INT PRIMARY KEY AUTO_INCREMENT,
	role_id INT NOT NULL,
	permission_id INT NOT NULL
)	ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO `role_permission`
(role_id,permission_id)
VALUES
(1,1),(1,2),(1,3),(1,4),
(2,1);