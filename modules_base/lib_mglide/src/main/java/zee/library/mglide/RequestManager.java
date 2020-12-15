package zee.library.mglide;

import java.io.File;

import zee.library.mglide.manager.Lifecycle;
import zee.library.mglide.manager.LifecycleListener;
import zee.library.mglide.manager.RequestTrack;
import zee.library.mglide.request.Request;

public class RequestManager implements LifecycleListener {

    private final Lifecycle lifecycle;
    private final GlideContext glideContext;
    RequestTrack requestTrack;

    public RequestManager(GlideContext glideContext, Lifecycle lifecycle) {
        this.glideContext = glideContext;
        this.lifecycle = lifecycle;
        requestTrack = new RequestTrack();
        // 注册生命周期回调监听
        lifecycle.addListener(this);

    }

    @Override
    public void onStart() {
        // 继续请求
        resumeRequests();
    }

    @Override
    public void onStop() {
        // 停止所有请求
        pauseRequests();
    }

    @Override
    public void onDestroy() {
        lifecycle.removeListener(this);
        requestTrack.clearRequests();
    }

    public void pauseRequests() {
        requestTrack.pauseRequests();
    }

    public void resumeRequests() {
        requestTrack.resumeRequests();
    }

    public RequestBuilder load(String string) {
        return new RequestBuilder(glideContext, this).load(string);
    }

    public RequestBuilder load(File file) {
        return new RequestBuilder(glideContext, this).load(file);
    }

    /**
     * 管理Request
     * 执行请求
     */
    public void track(Request request) {
        requestTrack.runRequest(request);
    }

}