package com.dn.mplayer;

import com.alibaba.android.arouter.launcher.ARouter;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;
import zee.library.common.base.BaseApplication;


public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
//        DBManager.getInstance().init(this, "mplayer.db");
        // 用来连接SQLiteStudio工具
//        SQLiteStudioService.instance().start(this);
    }

}