package com.reljicd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-07-17.
 */
public class RetMessage implements Serializable{
    private boolean result;
    private String msg;
    private Object data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
