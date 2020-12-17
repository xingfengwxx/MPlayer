package com.dn.module_login.net;

import java.util.List;

/**
 * author : 王星星
 * date : 2020/12/17 11:53
 * email : 1099420259@qq.com
 * description :
 */
public class Data<T> {

    private int status;
    private List<T> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
