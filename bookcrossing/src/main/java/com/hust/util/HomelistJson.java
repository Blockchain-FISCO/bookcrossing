package com.hust.util;

import java.util.List;

/**
 * 给安卓端返回的主页Json响应消息格式
 * @author jiajie zeng
 *
 */
public class HomelistJson {
	private int count;
	private List<BookInfo> books;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<BookInfo> getBooks() {
		return books;
	}
	public void setBooks(List<BookInfo> books) {
		this.books = books;
	}
}
