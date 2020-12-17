package com.dn.module_login.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dn.module_login.model.BannerBean;
import com.dn.module_login.model.UserInfo;
import com.dn.module_login.net.Data;
import com.dn.module_login.net.Result;
import com.dn.module_login.net.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author : 王星星
 * date : 2020/12/17 10:12
 * email : 1099420259@qq.com
 * description : ViewModel只做和业务逻辑和业务数据相关的事
 */
public class UserViewModel extends ViewModel {

    private MutableLiveData<UserInfo> userLiveData;
    private MutableLiveData<List<BannerBean>> bannerListLiveData;

    public UserViewModel() {
    }

    public MutableLiveData<UserInfo> getUserLiveData() {
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<>();
        }
        return userLiveData;
    }

    public MutableLiveData<List<BannerBean>> getBannerListLiveData() {
        if (bannerListLiveData == null) {
            bannerListLiveData = new MutableLiveData<>();
        }
        return bannerListLiveData;
    }

    public void login(String userName, String pwd) {
        UserService.getApi().login(userName, pwd)
                .enqueue(new Callback<Result<UserInfo>>() {
                    @Override
                    public void onResponse(Call<Result<UserInfo>> call, Response<Result<UserInfo>> response) {
                        if (response.body() != null
                                && response.body().getCode() == 200) {
                            userLiveData.setValue(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<UserInfo>> call, Throwable t) {

                    }
                });
    }

    public void getUserInfo(String userId) {

    }

    public void getBanners() {
        UserService.getApi().getBanners()
                .enqueue(new Callback<Data<BannerBean>>() {
                    @Override
                    public void onResponse(Call<Data<BannerBean>> call, Response<Data<BannerBean>> response) {
                        if (response.body() != null
                        && response.body().getStatus() == 100) {
                            bannerListLiveData.setValue(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<Data<BannerBean>> call, Throwable t) {

                    }
                });
    }
}
