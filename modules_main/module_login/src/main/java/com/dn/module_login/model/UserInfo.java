package com.dn.module_login.model;

public class UserInfo {

    /**
     * userId : 1
     * userName : 13000000000
     * mobile : 13154488332
     * pwd : 123123
     * state : 0
     * createTime : 2020-12-04 18:05:30
     * updateTime : 2020-12-04 18:05:30
     */

    private String userId;
    private String userName;
    private String mobile;
    private String pwd;
    private String state;
    private String createTime;
    private String updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
