package com.dn.module_main.livedata.bus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LiveDataBus {

    private static LiveDataBus instance;

    private Map<String, MLiveData> bus;

    public static LiveDataBus getInstance() {
        if (instance == null) {
            synchronized (LiveDataBus.class) {
                if (instance == null) {
                    instance = new LiveDataBus();
                }
            }
        }
        return instance;
    }

    private LiveDataBus() {
        bus = new ConcurrentHashMap<>();
    }

    public synchronized <T> MLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new MLiveData<>());
        }
        // 如果不需要阻止黏性事件，则换回MutableLiveData
        return (MLiveData<T>) bus.get(key);
    }

}