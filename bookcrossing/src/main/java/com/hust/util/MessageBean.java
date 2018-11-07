package com.hust.util;

/**
 * 
 * @author Weifeng Hao
 * 
 */

public class MessageBean {
    private int result;
    private String errMsg;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    @Override
    public String toString() {
		return "MessageBean [result=" + result + ", errMsg=" + errMsg + "]";
    
    }
}
