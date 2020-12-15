package zee.library.arouter.callback;

import zee.library.arouter.Postcard;

/**
 * 跳转结果回调
 */
public interface NavigationCallback {

    /**
     * 找到跳转页面
     *
     * @param postcard meta
     */
    void onFound(Postcard postcard);

    /**
     * 未找到
     *
     * @param postcard meta
     */
    void onLost(Postcard postcard);

    /**
     * 成功跳转
     *
     * @param postcard meta
     */
    void onArrival(Postcard postcard);

}