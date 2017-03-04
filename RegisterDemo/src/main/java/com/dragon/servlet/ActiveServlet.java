package com.dragon.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dragon.service.UserService;
import com.dragon.service.impl.UserServiceImpl;

public class ActiveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code=request.getParameter("code");
		UserService userService=new UserServiceImpl();
		if(userService.activeUser(code)){
			request.getRequestDispatcher("/welcome.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("/fail.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
