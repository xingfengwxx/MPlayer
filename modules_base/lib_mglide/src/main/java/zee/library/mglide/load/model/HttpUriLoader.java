package zee.library.mglide.load.model;

import android.net.Uri;

import java.io.InputStream;

import zee.library.mglide.load.ObjectKey;
import zee.library.mglide.load.model.data.HttpUriFetcher;

public class HttpUriLoader implements ModelLoader<Uri, InputStream> {

    /**
     * http类型的uri此loader才支持
     *
     * @param uri
     * @return
     */
    @Override
    public boolean handles(Uri uri) {
        String scheme = uri.getScheme();
        return scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https");
    }

    @Override
    public LoadData<InputStream> buildData(Uri uri) {
        return new LoadData<InputStream>(new ObjectKey(uri), new HttpUriFetcher(uri));
    }

    public static class Factory implements ModelLoaderFactory<Uri, InputStream> {

        @Override
        public ModelLoader<Uri, InputStream> build(ModelLoaderRegistry registry) {
            return new HttpUriLoader();
        }
    }

}