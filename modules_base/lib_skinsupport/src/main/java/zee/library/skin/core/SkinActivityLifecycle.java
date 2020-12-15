package zee.library.skin.core;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;

import java.lang.reflect.Field;

import androidx.core.view.LayoutInflaterCompat;
import zee.library.skin.SkinManager;
import zee.library.skin.utils.SkinUtils;

public class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    // 对activity设置了自定义的布局加载工厂后用来保存这个factory对象
    // 后面可以通过activity找到
    private ArrayMap<Activity, SkinLayoutInflaterFactory> mLayoutInflaterFactories = new ArrayMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        // 在这里做更新布局视图
        // 获得Activity的布局加载器
        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        try {
            // Android 布局加载器 使用 mFactorySet 标记是否设置过Factory
            // 如设置过则会抛出异常，这里需要处理下
            // 设置 mFactorySet 标签为false
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 更新状态栏
        SkinUtils.updateStatusBarColor(activity);

        // 获取要更新的字体
        Typeface typeface = SkinUtils.getSkinTypeface(activity);

        // 使用factory2 设置布局加载工程
        SkinLayoutInflaterFactory skinLayoutInflaterFactory = new SkinLayoutInflaterFactory(activity, typeface);
        LayoutInflaterCompat.setFactory2(layoutInflater, skinLayoutInflaterFactory);

        // 将factory添加到缓存中
        mLayoutInflaterFactories.put(activity, skinLayoutInflaterFactory);

        // 添加观察者
        SkinManager.getInstance().addObserver(skinLayoutInflaterFactory);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // 从集合中删除并取消观察
        SkinLayoutInflaterFactory skinLayoutInflaterFactory = mLayoutInflaterFactories.get(activity);
        mLayoutInflaterFactories.remove(skinLayoutInflaterFactory);
        SkinManager.getInstance().deleteObserver(skinLayoutInflaterFactory);
    }

    public void updateSkin(Activity activity) {
        SkinLayoutInflaterFactory skinLayoutInflaterFactory = mLayoutInflaterFactories.get(activity);
        if (skinLayoutInflaterFactory != null) {
            // 调用观察者update方法
            skinLayoutInflaterFactory.update(null, null);
        }
    }

}