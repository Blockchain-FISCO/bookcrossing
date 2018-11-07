package com.hust.service;

import java.util.List;

import com.hust.pojo.Book;

public interface BookService {
	public Book getBookById(String bookId);
	public List<Book> list(int start,int count);
	public List<Book> homeList();
	public void addBook(Book book);
	public void deleteBookById(String bookId);
	public int getTotal();
	public void updateBook(Book book);
	public List<Book> searchBookByName(String bookName, int start ,int count);
}
