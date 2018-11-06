package com.hust.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.pojo.Student;
import com.hust.service.StudentService;

@Controller
@RequestMapping(value = "/")
public class UserController {

	@Autowired
	private StudentService studentservice;
	
	/**
	 * 学生信息注册 返回布尔类型
	 * @param request
	 * @return 
	 * @throws
	 */
	@RequestMapping(value = "user/register")
	@ResponseBody
	public String register(HttpServletRequest request) {
		
		//更新用作登陆的息，信息存储在数据库上
		Student student = new Student();
		student.setStuId(request.getParameter("stu_id"));
		student.setStuName(request.getParameter("scho_name"));
		student.setPassword(request.getParameter("password"));
		
		studentservice.reister(student);
		
		return "true";		//调换页面
	}
	
	/**
	 * 学生信息登陆  返回布尔类型
	 * @param request
	 * @return 
	 * @throws
	 */
	@RequestMapping(value = "user/login")
	@ResponseBody
	public String  logIn(HttpServletRequest request) {
		//获取输入的id和密码
		Student student = new Student();
		student.setStuId(request.getParameter("stu_id"));
		student.setPassword(request.getParameter("password"));
		
		//调用 studentservice 进行信息判断
		if(studentservice.logIn(student))
			return "ture";
		
		return "false";		
	}
}
