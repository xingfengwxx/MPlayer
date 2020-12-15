package com.dn.module_main.livedata.bus;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import zee.library.utils.Log;

public class MLiveData<T> extends LiveData<T> {

    private boolean hasModified = false;

    private Handler handler;

    // 只有onStart后，对数据的修改才会触发 observer.onChanged()
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super T> observer) {
        super.observe(owner, new Observer<T>() {
            private boolean hasIntercept = false;

            // 创建了一个新的Observer来包装处理我们传入的observer
            @Override
            public void onChanged(T t) {
                Log.i("onChanged=" + t.toString());
                // 通过逻辑判断来解决不需要粘性事件的情况
                if (!hasModified || hasIntercept) {
                    observer.onChanged(t);
                }
                hasIntercept = true;
            }
        });
    }

    // 无论何时，只要数据发生改变，就会触发 observer.onChanged()
    @Override
    public void observeForever(@NonNull final Observer<? super T> observer) {
        super.observeForever(new Observer<T>() {
            private boolean hasIntercept = false;

            @Override
            public void onChanged(T t) {
                if (!hasModified || hasIntercept) {
                    observer.onChanged(t);
                }
                hasIntercept = true;
            }
        });
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
        hasModified = true;
        Log.i("hasModified=" + hasModified);
    }

    /**
     * 这里可能存在问题？
     * 第一次需要使用handler来解决hasModified在异步情况先先改变的问题
     *
     * @param value
     */
    @Override
    public void postValue(T value) {
        super.postValue(value);
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    hasModified = true;
                }
            });
        } else {
            hasModified = true;
        }
        // 不能直接修改hasModified值，因为异步情况导致还没onChanged调用就到这里了
        // hasModified = true;
        Log.i("hasModified=" + hasModified);
    }

}
