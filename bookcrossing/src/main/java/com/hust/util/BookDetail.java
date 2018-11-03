package com.hust.util;

import com.hust.pojo.Book;

/**
 * 图书详情，包含位置与是否可借信息
 * @author cengj
 *
 */
public class BookDetail{
	
	public String getBook_id() {
		return book_id;
	}


	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}


	public String getBook_name() {
		return book_name;
	}


	public void setBook_name(String book_name) {
		this.book_name = book_name;
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


	public String getBook_description() {
		return book_description;
	}


	public void setBook_description(String book_description) {
		this.book_description = book_description;
	}


	public String getPress() {
		return press;
	}


	public void setPress(String press) {
		this.press = press;
	}


	public String getAvailable() {
		return available;
	}


	public void setAvailable(String available) {
		this.available = available;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	private String book_id;
    private String book_name;
    private String picture;
    private String author;
    private String book_description;
    private String press;
	private String available;
	private String location;

	
	public void setBook(Book book) {
		this.setAuthor(book.getAuthor());
		this.setBook_description(book.getBookDescription());
		this.setBook_name(book.getBookName());
		this.setPicture(book.getPicture());
		this.setPress(book.getPress());
	}
}
