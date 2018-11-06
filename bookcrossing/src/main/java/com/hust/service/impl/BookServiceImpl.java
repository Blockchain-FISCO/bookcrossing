package com.hust.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.mapper.BookMapper;
import com.hust.pojo.Book;
import com.hust.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;
	
	@Override
	public Book getBookById(String bookId) {
		// TODO Auto-generated method stub
		return bookMapper.selectByPrimaryKey(bookId);
	}

	@Override
	public List<Book> list(int start, int count) {
		// TODO Auto-generated method stub
		return bookMapper.list(start, count);
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return bookMapper.getTotal();
	}

	@Override
	public void addBook(Book book) {
		// TODO Auto-generated method stub
		bookMapper.insert(book);
	}

	@Override
	public List<Book> homeList() {
		// TODO Auto-generated method stub
		return bookMapper.homeList();
	}

	@Override
	public void deleteBookById(String bookId) {
		// TODO Auto-generated method stub
		bookMapper.deleteByPrimaryKey(bookId);
	}

	@Override
	public void updateBook(Book book) {
		// TODO Auto-generated method stub
		bookMapper.updateByPrimaryKey(book);
	}

	@Override
	public List<Book> searchBookByName(String bookName) {
		// TODO Auto-generated method stub
		return bookMapper.searchBookByName(bookName);
	}
	
	
}
