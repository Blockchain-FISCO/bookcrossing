package com.hust.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.mapper.StudentMapper;
import com.hust.pojo.Student;
import com.hust.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentMapper studentmapper;

	@Override
	public boolean reister(Student student) {
		// TODO Auto-generated method stub
		String stu_id = student.getStuId();

		Student stu = studentmapper.selectByPrimaryKey(stu_id);
		if(stu.getStuId() == "") {
			studentmapper.insert(student);
			return true;
		}
			
		else
			return false;
	}

	@Override
	public boolean logIn(Student student) {
		// TODO Auto-generated method stub		
		String stu_id = student.getStuId();
		String password = student.getPassword();
		
		Student stu = studentmapper.selectByPrimaryKey(stu_id);
		if(stu.getStuId() == stu_id && stu.getPassword() == password) 
			return true;
		else
			return false;
	}

	@Override
	public Student getStudentById(String stuId) {

		return studentmapper.selectByPrimaryKey(stuId);
	}

	

}
