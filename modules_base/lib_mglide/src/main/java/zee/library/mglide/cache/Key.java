package zee.library.mglide.cache;

import java.security.MessageDigest;

public interface Key {

    void updateDiskCacheKey(MessageDigest md);

    byte[] getKeyBytes();
}