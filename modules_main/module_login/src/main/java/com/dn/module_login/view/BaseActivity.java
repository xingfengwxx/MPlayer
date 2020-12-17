package com.dn.module_login.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dn.module_login.life.ActivityLifeObserver;

/**
 * author : 王星星
 * date : 2020/12/17 9:44
 * email : 1099420259@qq.com
 * description :
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this AppCompatActivity
        setContentView(getLayoutId());
        // 统一注解ARouter
        ARouter.getInstance().inject(this);
        // 初始化View、适配器等调用
        initViews(this);
        // 数据请求调用
        initData(this);
        // 事件绑定
        initEvent(this);
        // 注册生命周期监听 - 然后做一些绑定、解绑、埋点等操作
        getLifecycle().addObserver(new ActivityLifeObserver(this, getLifecycle()));
    }

    public abstract int getLayoutId();

    public abstract void initViews(Context context);

    public abstract void initData(Context context);

    public abstract void initEvent(Context context);

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
