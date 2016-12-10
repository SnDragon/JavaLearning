package com.crm.controller;


import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.crm.model.User;
import com.crm.service.UserService;





@Controller
@Scope(value="prototype")
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView login(String name,String password){
		ModelAndView modelAndView=new ModelAndView();
		User user=userService.checkLogin(name, password);
		if(user!=null){
			modelAndView.setViewName("welcome");
			modelAndView.addObject("user",user);
		}else{
			modelAndView.setViewName("fail");
		}
		return modelAndView;
	}
}
