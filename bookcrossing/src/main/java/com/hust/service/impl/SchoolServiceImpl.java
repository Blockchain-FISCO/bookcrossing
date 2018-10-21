package com.hust.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.mapper.SchoolMapper;
import com.hust.pojo.School;
import com.hust.service.SchoolService;

@Service
public class SchoolServiceImpl implements SchoolService{
	
	@Autowired
	private SchoolMapper schoolMapper;

	@Override
	public List<School> list() {
		return schoolMapper.list();
	}

}
