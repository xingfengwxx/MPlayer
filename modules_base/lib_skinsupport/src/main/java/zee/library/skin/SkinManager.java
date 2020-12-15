package zee.library.skin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Observable;

import zee.library.skin.core.SkinActivityLifecycle;
import zee.library.skin.core.SkinPreference;
import zee.library.skin.core.SkinResources;

public class SkinManager extends Observable {

    private static SkinManager instance;

    private Application mContext;

    // Activity生命周期回调
    private SkinActivityLifecycle skinActivityLifecycle;

    public static SkinManager getInstance() {
        return instance;
    }

    public static void init(Application application) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }
    }

    private SkinManager(Application application) {
        mContext = application;

        /**
         * 提供了一个应用生命周期回调的注册方法，用来对应用的生命周期进行集中管理。
         * 这个接口叫registerActivityLifecycleCallbacks，可以通过它注册
         * 自己的ActivityLifeCycleCallback，每一个Activity的生命周期都会回调到这里的对应方法。
         */
        skinActivityLifecycle = new SkinActivityLifecycle();
        application.registerActivityLifecycleCallbacks(skinActivityLifecycle);

        // 资源管理类 用于从 app/皮肤包 中加载资源
        SkinResources.init(application);

        // 初始化SkinPreference，用于记录当前使用的皮肤
        SkinPreference.init(application);

        // 从SharedPreferences中获取最近使用的皮肤包路径
        String skinPath = getPreferenceSkinPath();

        // 首次加载皮肤并应用
        loadSkin(skinPath);
    }

    /**
     * 核心方法：
     * 加载皮肤并应用
     *
     * @param skinPath 皮肤路径 如果为空则使用默认皮肤
     */
    public void loadSkin(String skinPath) {
        Log.i("TAG", "loadSkin：" + skinPath);
        if (TextUtils.isEmpty(skinPath)) {
            // 记录使用默认皮肤
            SkinPreference.getInstance().setSkin("");
            // 清空资源管理器中皮肤资源属性
            SkinResources.getInstance().reset();
        } else {
            try {
                // 反射创建AssetManager 与 Resource
                AssetManager assetManager = AssetManager.class.newInstance();

                // addAssetPath这个方法是hide的
                // 需要通过反射来调用：设置资源路径目录或压缩包
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(assetManager, skinPath);

                // 根据当前的显示与配置(横竖屏、语言等)创建这个assetManager对应的Resources，用来获取皮肤包中相应的配置属性值
                Resources skinResource = new Resources(assetManager,
                        mContext.getResources().getDisplayMetrics(),
                        mContext.getResources().getConfiguration());

                // 获取外部Apk(皮肤包)包名
                PackageManager pm = mContext.getPackageManager();
                PackageInfo info = pm.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
                String packageName = info.packageName;

                // 把对应的皮肤资源路径记录起来，供下次启动时用
                SkinPreference.getInstance().setSkin(skinPath);

                // 把新的Resources和PackageName交给SkinResources
                SkinResources.getInstance().applySkin(skinResource, packageName);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 设置状态改变，被观察者改变通知所有观察者
        // 被采集的View更新皮肤
        setChanged();
        notifyObservers(null);
    }

    /**
     * 获取SharedPreferences中最近使用的皮肤包路径
     *
     * @return String 皮肤包路径
     */
    public String getPreferenceSkinPath() {
        return SkinPreference.getInstance().getSkin();
    }

}