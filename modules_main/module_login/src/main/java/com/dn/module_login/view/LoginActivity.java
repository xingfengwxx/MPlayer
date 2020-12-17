package com.dn.module_login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dn.module_login.R;
import com.dn.module_login.model.BannerBean;
import com.dn.module_login.model.UserInfo;
import com.dn.module_login.viewmodel.UserViewModel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

@Route(path = "/login/LoginActivity")
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private UserViewModel mViewModel;
    private EditText etMobile;
    private EditText etPwd;
    private ImageView ivNext;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Context context) {
        etMobile = findViewById(R.id.et_mobile);
        etPwd = findViewById(R.id.et_pwd);
        ivNext = findViewById(R.id.iv_next);

        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//        mViewModel.getUserLiveData().observe(this, new Observer<UserInfo>() {
//            @Override
//            public void onChanged(UserInfo userInfo) {
//                if (userInfo != null) {
//                    ARouter.getInstance().build("/main/MainActivity").navigation(LoginActivity.this);
//                    finish();
//                }
//            }
//        });

        mViewModel.getBannerListLiveData().observe(this, new Observer<List<BannerBean>>() {
            @Override
            public void onChanged(List<BannerBean> bannerBeans) {
                if (bannerBeans != null) {
                    Log.i(TAG, "onChanged: 跳转到主页");
                    ARouter.getInstance().build("/main/MainActivity").navigation();
                    finish();
                }
            }
        });
    }

    @Override
    public void initData(Context context) {

    }

    @Override
    public void initEvent(Context context) {
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = etMobile.getText().toString();
                String pwd = etPwd.getText().toString();
//                mViewModel.login(mobile, pwd);
                mViewModel.getBanners();
            }
        });
    }


}