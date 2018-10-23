package com.hust.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hust.pojo.Member;
import com.hust.pojo.Student;
import com.hust.service.StudentService;

@Controller
@RequestMapping(value = "/")
public class UserController {

	@Autowired
	private StudentService studentservice;
	
	/**
	 * 学生信息注册
	 * @param request
	 * @return 
	 * @throws
	 */
	@RequestMapping(value = "user/register")
	public String register(HttpServletRequest request) {
		//新增会员,会员信息在区块链上存储
		Member member = new Member();		
		member.setStuId(request.getParameter("stu_id"));
		member.setSchoId(request.getParameter("scho_id"));
		member.setBookId(request.getParameter("book_id"));
		member.setEmailAddr(request.getParameter("email_addr"));
		
		studentservice.addMember(member);
		
		//更新用作登陆的信息，信息存储在数据库上
		Student student = new Student();
		student.setStuId(request.getParameter("stu_id"));
		student.setStuName(request.getParameter("stu_name"));
		student.setPassword(request.getParameter("password"));
		
		studentservice.reister(student);
		
		return "user/register";		
	}
	
	/**
	 * 学生信息登陆
	 * @param request
	 * @return 
	 * @throws
	 */
	@RequestMapping(value = "user/login")
	public String  logIn(HttpServletRequest request) {
		//获取输入的id和密码
		Student student = new Student();
		student.setStuId(request.getParameter("stu_id"));
		student.setPassword(request.getParameter("password"));
		
		//调用 studentservice 进行信息判断
		if(studentservice.logIn(student))
			return "success!";
		
		return "user/login";		
	}
}
