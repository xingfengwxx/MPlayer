package zee.library.mglide.cache;

public interface ArrayPool {

    byte[] get(int len);

    void put(byte[] data);

    void clearMemory();

    void trimMemory(int level);

    int getMaxSize();
}