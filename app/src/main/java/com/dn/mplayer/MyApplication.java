package com.dn.mplayer;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;
import zee.library.arouter.DNRouter;
import zee.library.common.base.BaseApplication;
import zee.library.orm.DBManager;


public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DNRouter.init(this);
        DBManager.getInstance().init(this, "mplayer.db");
        // 用来连接SQLiteStudio工具
        SQLiteStudioService.instance().start(this);
    }

}