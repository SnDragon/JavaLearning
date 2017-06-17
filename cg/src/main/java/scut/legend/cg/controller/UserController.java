package scut.legend.cg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.PrivateKeyResolver;

import ch.qos.logback.core.net.LoginAuthenticator;
import scut.legend.cg.po.User;
import scut.legend.cg.service.UserService;

@Controller
public class UserController {
	@Resource
	private UserService userService;
	@Resource
	private PasswordService passwordService;
	
//	@RequestMapping(value="/user")
//	@ResponseBody
//	public User testUser(@RequestBody User user){
//		System.out.println(user);
//		return user;
//	}
//	
	@RequestMapping(value="/user/{id}")
	@ResponseBody
	public User testList(@PathVariable("id")Integer id){
		User user=userService.getUserById(id);
		return user;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public String login(@RequestBody User user){
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());
		try{
			SecurityUtils.getSubject().login(token);
			return "success";
		}catch(UnknownAccountException e){
			return "账号不存在";
		}catch(IncorrectCredentialsException e){
			return "密码错误";
		}catch (Exception e) {
			return "其他错误";
		}
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/unauthorized")
	public String unauthorized(){
		return "unauthorized";
	}
	
	@RequestMapping(value="/encry")
	@ResponseBody
	public String encry(@RequestParam(value="password")String password){
		return passwordService.encryptPassword(password);
	}
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(){
		SecurityUtils.getSubject().logout();
		return new ModelAndView("login");
	}
	
	@RequestMapping(value="/index")
	@ResponseBody
	public String index(){
		return "如果你看到这个页面，说明你已经登录,否则会跳到登录页面";
	}
	
	@RequestMapping(value="/index2")
	@ResponseBody
	public String index2(){
		return "如果你看到这个页面，说明你已经登录或者选择了记住我选项";
	}
	
	@RequestMapping(value="/user")
	@ResponseBody
	public String getUser(){
		return "role为user的用户才能看到这个页面";
	}
	
	@RequestMapping(value="/admin")
	@ResponseBody
	public String getAdmin(){
		return "role为admin的用户才能看到这个页面";
	}
	
	
	@RequestMapping(value="/user/view")
	@ResponseBody
	public String viewUser(){
		return "拥有user:view权限的用户才能看到这个页面";
	}
	
	@RequestMapping(value="/user/create")
	@ResponseBody
	public String createUser(){
		return "拥有user:create权限的用户才能看到这个页面";
	}
	
	
	
}
