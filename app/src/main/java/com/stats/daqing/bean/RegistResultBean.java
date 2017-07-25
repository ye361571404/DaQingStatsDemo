package com.stats.daqing.bean;

/**
 * Created by Administrator on 2017/7/21.
 */

public class RegistResultBean {


    /**
     * message : 邮箱格式不正确
     * status : 500
     */

    private String message;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
