package com.hust.pojo;

public class want_book {
    private String wantId;

    private String stuId;

    private String bookId;

    public String getWantId() {
        return wantId;
    }

    public void setWantId(String wantId) {
        this.wantId = wantId == null ? null : wantId.trim();
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId == null ? null : stuId.trim();
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }
}