package com.hust.util;

/**
 * 安卓主页Json数据中书籍的缩略信息
 * @author jiajie zeng
 *
 */
public class BookInfo {
	private String book_id;
	private String book_name;
	private String picture;
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


}
