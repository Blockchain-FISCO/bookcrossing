package com.hust.pojo;

public class Student {
    private String stuId;

    private String stuName;

    private String schoId;

    private String emailAddr;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId == null ? null : stuId.trim();
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }

    public String getSchoId() {
        return schoId;
    }

    public void setSchoId(String schoId) {
        this.schoId = schoId == null ? null : schoId.trim();
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr == null ? null : emailAddr.trim();
    }
}