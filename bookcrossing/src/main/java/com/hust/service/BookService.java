package com.hust.service;

import java.util.List;

import com.hust.pojo.Book;

public interface BookService {
	public Book getBookById(int bookId);
	public List<Book> list(int start,int count);
	public void addBook(Book book);
	public int getTotal();
}
