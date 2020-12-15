package com.dn.module_login;

import android.os.Bundle;
import android.view.View;

import com.dn.module_login.databinding.ActivityLoginBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import zee.library.arouter.DNRouter;
import zee.library.router_annotation.Router;

@Router(path = "/login/LoginActivity")
public class LoginActivity extends AppCompatActivity {

    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定Layout
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        userInfo = new UserInfo();
        userInfo.setMobile("1223123123");
        userInfo.setPwd("asdfasdf");
        binding.setUser(userInfo);
        //binding.setVariable(BR.user, userInfo);
        binding.setActivity(this);

        binding.etMobile.setText("");
    }

    public void onClick(View view) {

        /*userInfo.setMobile("120000000000");
        userInfo.setPwd("120000000000");*/

        //Toast.makeText(this, userInfo.getMobile() + "\n" + userInfo.getPwd(), Toast.LENGTH_SHORT).show();

        DNRouter.getInstance().build("/main/MainActivity").navigation(LoginActivity.this);
        finish();

    }

}