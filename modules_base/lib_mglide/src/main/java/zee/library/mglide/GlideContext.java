package zee.library.mglide;

import android.content.Context;

import zee.library.mglide.load.Engine;
import zee.library.mglide.request.RequestOptions;

public class GlideContext {

    Context context;
    RequestOptions defaultRequestOptions;
    Engine engine;
    Registry registry;

    public GlideContext(Context context, RequestOptions defaultRequestOptions, Engine engine,
                        Registry registry) {
        this.context = context;
        this.defaultRequestOptions = defaultRequestOptions;
        this.engine = engine;
        this.registry = registry;
    }

    public Context getContext() {
        return context;
    }

    public RequestOptions getDefaultRequestOptions() {
        return defaultRequestOptions;
    }

    public Engine getEngine() {
        return engine;
    }

    public Registry getRegistry() {
        return registry;
    }

    public Context getApplicationContext() {
        return context;
    }
}
