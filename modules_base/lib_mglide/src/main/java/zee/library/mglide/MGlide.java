package zee.library.mglide;

import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;

import java.io.File;
import java.io.InputStream;

import androidx.fragment.app.FragmentActivity;
import zee.library.mglide.cache.ArrayPool;
import zee.library.mglide.cache.MemoryCache;
import zee.library.mglide.cache.recycle.BitmapPool;
import zee.library.mglide.load.Engine;
import zee.library.mglide.load.codec.StreamBitmapDecoder;
import zee.library.mglide.load.model.FileLoader;
import zee.library.mglide.load.model.FileUriLoader;
import zee.library.mglide.load.model.HttpUriLoader;
import zee.library.mglide.load.model.StringModelLoader;

public class MGlide implements ComponentCallbacks2 {

    private final MemoryCache memoryCache;
    private final BitmapPool bitmapPool;
    private final ArrayPool arrayPool;
    private final RequestManagerRetriever requestManagerRetriever;
    private static MGlide glide;
    private final Engine engine;
    private final GlideContext glideContext;

    protected MGlide(Context context, GlideBuilder builder) {
        memoryCache = builder.memoryCache;
        bitmapPool = builder.bitmapPool;
        arrayPool = builder.arrayPool;
        // 注册机
        Registry registry = new Registry();
        ContentResolver contentResolver = context.getContentResolver();
        registry.add(String.class, InputStream.class, new StringModelLoader.StreamFactory())
                .add(Uri.class, InputStream.class, new HttpUriLoader.Factory())
                .add(Uri.class, InputStream.class, new FileUriLoader.Factory(contentResolver))
                .add(File.class, InputStream.class, new FileLoader.Factory())
                .register(InputStream.class, new StreamBitmapDecoder(bitmapPool, arrayPool));

        engine = builder.engine;
        glideContext = new GlideContext(context, builder.defaultRequestOptions,
                engine, registry);

        requestManagerRetriever = new RequestManagerRetriever(glideContext);
    }

    /**
     * 默认实现
     *
     * @param context
     * @return
     */
    private static MGlide get(Context context) {
        if (null == glide) {
            synchronized (MGlide.class) {
                if (null == glide) {
                    init(context, new GlideBuilder());
                }
            }
        }
        return glide;
    }

    /**
     * 使用者可以定制自己的 GlideBuilder
     *
     * @param context
     * @param builder
     */
    public static void init(Context context, GlideBuilder builder) {
        if (MGlide.glide != null) {
            tearDown();
        }
        Context applicationContext = context.getApplicationContext();
        MGlide glide = builder.build(applicationContext);
        applicationContext.registerComponentCallbacks(glide);
        MGlide.glide = glide;
    }

    public static synchronized void tearDown() {
        if (glide != null) {
            glide.glideContext
                    .getApplicationContext()
                    .unregisterComponentCallbacks(glide);
            glide.engine.shutdown();
        }
        glide = null;
    }

    public static RequestManager with(FragmentActivity activity) {
        return MGlide.get(activity).requestManagerRetriever.get(activity);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    /**
     * 内存紧张
     */
    @Override
    public void onLowMemory() {
        // memory和bitmappool顺序不能变
        // 因为memory移除后会加入复用池
        memoryCache.clearMemory();
        bitmapPool.clearMemory();
        arrayPool.clearMemory();
    }

    /**
     * 需要释放内存
     * 在onTrimMemory 中可以根据系统的内存状况及时调整App内存占用，提升用户体验或让App存活更久
     *
     * @param level 内存状态
     */
    @Override
    public void onTrimMemory(int level) {
        memoryCache.trimMemory(level);
        bitmapPool.trimMemory(level);
        arrayPool.trimMemory(level);
    }

}