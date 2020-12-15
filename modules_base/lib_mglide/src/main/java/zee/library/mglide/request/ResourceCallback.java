package zee.library.mglide.request;

import zee.library.mglide.cache.recycle.Resource;

public interface ResourceCallback {
    void onResourceReady(Resource reference);
}