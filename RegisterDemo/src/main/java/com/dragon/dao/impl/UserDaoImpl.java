package com.dragon.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dragon.dao.UserDao;
import com.dragon.model.User;
import com.dragon.util.DBUtil;

public class UserDaoImpl implements UserDao{

	@Override
	public int save(User user) {
		int num=0;
		try {
			Connection conn=DBUtil.getConnection();
			String sql ="insert into user(username,email,password,state,code) values(?,?,?,?,?)";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setInt(4, user.getState());
			pstmt.setString(5, user.getCode());
			num=pstmt.executeUpdate();
			DBUtil.close(conn,pstmt, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
		
	}

	@Override
	public int activeUser(String code) {
		int num=0;
		try {
			Connection conn=DBUtil.getConnection();
			String sql="update user set state=1 where code=?";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, code);
			num=pstmt.executeUpdate();
			DBUtil.close(conn,pstmt,null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
		
	}

}
