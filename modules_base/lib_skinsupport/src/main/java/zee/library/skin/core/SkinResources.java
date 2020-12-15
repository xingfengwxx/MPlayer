package zee.library.skin.core;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * 换肤资源管理类
 * 用于从app/皮肤包中加载资源
 */
public class SkinResources {

    private static SkinResources instance;

    // App自己的Resources
    private Resources mAppResources;

    // 加载了皮肤包后创建的Resources
    private Resources mSkinResources;

    // 皮肤报名
    private String mSkinPkgName;

    private boolean isDefaultSkin = true;

    private SkinResources(Context context) {
        mAppResources = context.getResources();
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }

    /**
     * 恢复到默认的皮肤设置
     */
    public void reset() {
        mSkinResources = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }

    /**
     * 提交新的属性值
     * 最后通过观察者模式，通知使用这些新的属性
     *
     * @param resources
     * @param pkgName
     */
    public void applySkin(Resources resources, String pkgName) {
        mSkinResources = resources;
        mSkinPkgName = pkgName;
        // 是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }


    // 加载了皮肤包后获取资源ID的方法
    public int getIdentifier(int resId) {
        if (isDefaultSkin) {
            // 默认的皮肤，直接用原APP的
            return resId;
        }
        // 在皮肤包中不一定就是 当前程序的 id
        // 获取对应id 在当前的名称
        String resName = mAppResources.getResourceEntryName(resId);
        String resType = mAppResources.getResourceTypeName(resId);
        // 重新获取指定应用包下的指定资源ID
        int skinResId = mSkinResources.getIdentifier(resName, resType, mSkinPkgName);
        return skinResId;
    }

    // 获取颜色值，同上getIdentifier原理
    public int getColor(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId);
        }
        int skinResId = getIdentifier(resId);
        if (skinResId == 0) {
            // 当皮肤包中不存在的时候
            // 依然使用原APP的对应值
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(skinResId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinResId = getIdentifier(resId);
        if (skinResId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinResId);
    }

    public Drawable getDrawable(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId, null);
        }
        int skinResId = getIdentifier(resId);
        if (skinResId == 0) {
            return mAppResources.getDrawable(resId, null);
        }
        return mSkinResources.getDrawable(skinResId);
    }


    /**
     * 可能是Color 也可能是drawable
     *
     * @return
     */
    public Object getBackground(int resId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resId);

        if (resourceTypeName.equals("color")) {
            return getColor(resId);
        } else {
            // Drawable
            return getDrawable(resId);
        }
    }

    public String getString(int resId) {
        try {
            if (isDefaultSkin) {
                return mAppResources.getString(resId);
            }
            int skinResId = getIdentifier(resId);
            if (skinResId == 0) {
                return mAppResources.getString(resId);
            }
            return mSkinResources.getString(skinResId);
        } catch (Resources.NotFoundException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public Typeface getTypeface(int resId) {
        String skinTypefacePath = getString(resId);
        if (TextUtils.isEmpty(skinTypefacePath)) {
            return Typeface.DEFAULT;
        }
        try {
            // 使用皮肤包
            if (isDefaultSkin) {
                return Typeface.createFromAsset(mAppResources.getAssets(), skinTypefacePath);
            }
            return Typeface.createFromAsset(mSkinResources.getAssets(), skinTypefacePath);
        } catch (RuntimeException e) {
        }
        return Typeface.DEFAULT;
    }

}