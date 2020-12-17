package com.dn.module_login.net;

public class Result<T> {

    /**
     * code : 200
     * data : {"userId":1,"userName":"13000000000","mobile":"13154488332","pwd":"123123","state":0,"createTime":"2020-12-04 18:05:30","updateTime":"2020-12-04 18:05:30"}
     * msg : 请求成功
     */

    private int code;
    private T data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
