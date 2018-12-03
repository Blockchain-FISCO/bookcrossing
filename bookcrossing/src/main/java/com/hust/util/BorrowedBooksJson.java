package com.hust.util;

import java.util.List;

import com.hust.pojo.Book;

public class BorrowedBooksJson {
	private int count;
	private List<MyBorrowedBooks> books;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<MyBorrowedBooks> getBooks() {
		return books;
	}
	public void setBooks(List<MyBorrowedBooks> books) {
		this.books = books;
	}
}
