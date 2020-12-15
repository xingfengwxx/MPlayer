package com.dn.module_main.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import zee.library.utils.Log;

public class MRetrofit {

    private static MusicApi api;

    public static void init() {

        // 添加日志拦截
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i(message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY);

        // 修改请求头拦截
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                        .build();
                return chain.proceed(newRequest);
            }
        };

        // 生成OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .build();

        // 创建Retrofit并设置OkHttpClient
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://tingapi.ting.baidu.com")
                //.addConverterFactory(GsonConverterFactory.create()) // 需要直接返回实体对象时加入这个
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();

        // 获取动态代理对象
        api = retrofit.create(MusicApi.class);
    }

    // 获得已经创建的请求动态代理对象
    public static MusicApi getApi() {
        if (api == null) {
            synchronized (MusicApi.class) {
                if (api == null) {
                    init();
                }
            }
        }
        return api;
    }

}
