package com.hust.pojo;

public class Want_Book_Hot{
    private String bookId;
   
    private String hotNum;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }
    
    public String getHotNum() {
        return hotNum;
    }

    public void setHotNum(String bookId) {
        this.hotNum = hotNum == null ? null : hotNum.trim();
    }
}