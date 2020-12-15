package com.dn.module_main.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import zee.library.utils.Log;

/**
 * 假设我这是一个框架
 * 有些业务需要依赖Activity的生命周期
 * 那么这种方式去管理生命周期就很好
 */
public class ZeeLib implements LifecycleObserver {

    private LifecycleOwner lifecycleOwner;

    private static ZeeLib instance = new ZeeLib();

    public static ZeeLib getInstance() {
        return instance;
    }

    //@OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Log.i("onStart..");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Log.i("onStop..");
    }

    /**
     * (注册绑定)传入生命周期拥有者
     * 它能获取到生命周期感知者Lifecycle对象
     *
     * @param lifecycleOwner
     */
    public void register(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

}