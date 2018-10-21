package com.hust.pojo;

public class Location {
    private String bookId;

    private String stuId;

    private String schoId;

    private Boolean available;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}