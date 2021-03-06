package com.hust.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hust.pojo.Student;
import com.hust.service.StudentService;

import com.hust.util.*;

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
	public MessageBean register(HttpServletRequest request) {
		
		//更新用作登陆的息，信息存储在数据库上
		Student student = new Student();
		student.setStuId(request.getParameter("stu_id"));
		student.setStuName(request.getParameter("stu_name"));
		student.setPassword(request.getParameter("passwd"));
			
		Boolean _flag = studentservice.reister(student);
		
		//在进行判断的基础上，进行信息增加
		MessageBean res = new MessageBean();
		if(_flag) {
	      	res.setResult(200);
	      	res.setErrMsg("null");
	    	
		}else {
	      	res.setResult(500);
	      	res.setErrMsg("err");
		}
      		
		return res;	
	}
	
	/**
	 * 学生信息登陆  返回布尔类型
	 * @param request
	 * @return 
	 * @throws
	 */
	@RequestMapping(value = "user/login")
	@ResponseBody
	public MessageBean logIn(HttpServletRequest request) {
		//获取输入的id和密码
		Student student = new Student();
		student.setStuId(request.getParameter("stu_id"));
		student.setPassword(request.getParameter("passwd"));
		
		//调用 studentservice 进行信息判断 ,然后进行消息封装
		MessageBean res = new MessageBean();
		if(studentservice.logIn(student)) {
	      	res.setResult(200);
	      	res.setErrMsg("null");
	    	
		}else {
	      	res.setResult(500);
	      	res.setErrMsg("err");
		}
			return res;
			
	}
}
