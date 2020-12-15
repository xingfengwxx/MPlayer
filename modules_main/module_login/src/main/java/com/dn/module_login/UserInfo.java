package com.dn.module_login;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * 这是一个ViewModel
 */
public class UserInfo extends BaseObservable {

    private String mobile;

    private String pwd;

    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR.pwd);
    }

}
