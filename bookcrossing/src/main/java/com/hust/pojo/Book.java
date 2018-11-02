package com.hust.pojo;

public class Book {
    private String bookId;

    private String bookName;

    private String picture;

    private String author;

    private String bookDescription;

    private String press;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription == null ? null : bookDescription.trim();
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press == null ? null : press.trim();
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "bookid:"+this.getBookId()+"-bookname:"+this.getBookName()+"-picture:"+this.getPicture()+"-author:"+this.getAuthor()+"-press:"+this.getPress()+"-description:"+this.getBookDescription();
    }
}