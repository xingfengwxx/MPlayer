package zee.library.common.base;

import android.app.Application;

import zee.library.utils.DisplayUtils;
import zee.library.skin.SkinManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayUtils.init(this);
        SkinManager.init(this);
    }
}
