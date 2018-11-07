package com.hust.util;

import com.hust.pojo.Book;

public class BookResultForSearch {
    private String bookId;

    private String bookName;

    private String picture;

    private String author;

    private String press;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}
	
	public void setBookResult(Book book) {
		this.setAuthor(book.getAuthor());
		this.setBookId(book.getBookId());
		this.setBookName(book.getBookName());
		this.setPicture(book.getPicture());
		this.setPress(book.getPress());
	}
}
