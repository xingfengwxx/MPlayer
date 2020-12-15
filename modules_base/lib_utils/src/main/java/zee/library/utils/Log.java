package zee.library.utils;

public class Log {

    private static String MAIN_TAG = "mplayer";

    public static void setMainTag(String mainTag) {
        MAIN_TAG = mainTag;
    }

    public static int v(String msg) {
        return v(MAIN_TAG, msg);
    }

    public static int d(String msg) {
        return d(MAIN_TAG, msg);
    }

    public static int i(String msg) {
        return i(MAIN_TAG, msg);
    }

    public static int w(String msg) {
        return w(MAIN_TAG, msg);
    }

    public static int e(String msg) {
        return e(MAIN_TAG, msg);
    }

    public static int v(String TAG, String msg) {
        return android.util.Log.v(getTag(TAG), msg);
    }

    public static int v(String TAG, String msg, Throwable tr) {
        return android.util.Log.v(getTag(TAG), msg, tr);
    }

    public static int d(String TAG, String msg) {
        return android.util.Log.d(getTag(TAG), msg);
    }

    public static int d(String TAG, String msg, Throwable tr) {
        return android.util.Log.d(getTag(TAG), msg, tr);
    }

    public static int i(String TAG, String msg) {
        return android.util.Log.i(getTag(TAG), msg);
    }

    public static int i(String TAG, String msg, Throwable tr) {
        return android.util.Log.i(getTag(TAG), msg, tr);
    }

    public static int w(String TAG, String msg) {
        return android.util.Log.w(getTag(TAG), msg);
    }

    public static int w(String TAG, String msg, Throwable tr) {
        return android.util.Log.w(getTag(TAG), msg, tr);
    }

    public static int e(String TAG, String msg) {
        return android.util.Log.e(getTag(TAG), msg);
    }

    public static int e(String TAG, String msg, Throwable tr) {
        return android.util.Log.e(getTag(TAG), msg, tr);
    }

    private static String getTag(String tag) {
        return "TAG".equals(tag) ? MAIN_TAG : tag;
    }

}