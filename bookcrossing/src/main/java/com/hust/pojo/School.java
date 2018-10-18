package com.hust.pojo;

public class School {
    private String schoId;

    private String schoName;

    public String getSchoId() {
        return schoId;
    }

    public void setSchoId(String schoId) {
        this.schoId = schoId == null ? null : schoId.trim();
    }

    public String getSchoName() {
        return schoName;
    }

    public void setSchoName(String schoName) {
        this.schoName = schoName == null ? null : schoName.trim();
    }
}