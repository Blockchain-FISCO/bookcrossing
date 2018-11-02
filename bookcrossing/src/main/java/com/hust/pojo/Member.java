package com.hust.pojo;

public class Member {
    private String stuId;

    private String schoId;

    private String bookId;

    private String emailAddr;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId == null ? null : stuId.trim();
    }

    public String getSchoId() {
        return schoId;
    }

    public void setSchoId(String schoId) {
        this.schoId = schoId == null ? null : schoId.trim();
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr == null ? null : emailAddr.trim();
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "bookid:"+this.getBookId()+"-"+"email:"+this.getEmailAddr()+"-"+"schoid:"+this.getSchoId()+"-"+"stuid:"+this.getStuId();
    }
}