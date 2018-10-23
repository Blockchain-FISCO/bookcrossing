package com.hust.service;

import com.hust.pojo.Member;
import com.hust.pojo.Student;

public interface StudentService {	
	public void addMember(Member member);
	public boolean reister(Student studentr);
	public boolean logIn(Student student);
}
