package com.dn.module_login.life;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * author : 王星星
 * date : 2020/12/17 10:02
 * email : 1099420259@qq.com
 * description :
 */
public class ActivityLifeObserver implements LifecycleObserver {

    private Lifecycle mLifecycle;
    private Context mContext;

    public ActivityLifeObserver(Context context, Lifecycle lifecycle) {
        mLifecycle = lifecycle;
        mContext = context;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onPause() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onStop() {

    }

}
