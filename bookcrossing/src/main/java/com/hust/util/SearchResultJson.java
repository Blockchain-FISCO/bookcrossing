package com.hust.util;

import java.util.List;

import com.hust.pojo.Book;

public class SearchResultJson {
	private int count;
	private List<BookResultForSearch> books;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<BookResultForSearch> getBooks() {
		return books;
	}
	public void setBooks(List<BookResultForSearch> books) {
		this.books = books;
	}
}
