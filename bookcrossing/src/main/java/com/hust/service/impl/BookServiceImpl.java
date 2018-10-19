package com.hust.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.mapper.BookMapper;
import com.hust.pojo.Book;
import com.hust.service.BookService;

@Service("bookService")
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookMapper bookMapper;

	public Book getBookById(int bookId) {
		// TODO Auto-generated method stub
		return bookMapper.selectByPrimaryKey(String.valueOf(bookId));
	}
	
//	public Book setBookById() {
//		return bookMapper.selectByPrimaryKey(String.valueOf(bookId));
//	}


}
