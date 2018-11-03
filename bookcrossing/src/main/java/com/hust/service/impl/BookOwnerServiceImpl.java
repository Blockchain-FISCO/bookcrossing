package com.hust.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.mapper.BookOwnerMapper;
import com.hust.pojo.BookOwner;
import com.hust.service.BookOwnerService;

@Service
public class BookOwnerServiceImpl implements BookOwnerService{

	@Autowired
	private BookOwnerMapper bookOwnerMapper;
	
	
	@Override
	public void addBookOwner(BookOwner bookOwner) {
		// TODO Auto-generated method stub
		bookOwnerMapper.insert(bookOwner);
	}

}
