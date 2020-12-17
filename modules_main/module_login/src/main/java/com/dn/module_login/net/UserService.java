package com.dn.module_login.net;

import com.dn.module_login.model.BannerBean;
import com.dn.module_login.model.UserInfo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import zee.library.utils.Log;

public class UserService {

    public static final String LOCAL_BASE_URL = "http://192.168.1.64:9099";
    public static final String BASE_URL = "https://gank.io/api/v2/";

    private static Api api;

    public static void init() {

        // 添加日志拦截
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i(message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY);

        // 生成OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        api = retrofit.create(Api.class);
    }

    // 获得已经创建的请求动态代理对象
    public static Api getApi() {
        if (api == null) {
            synchronized (Api.class) {
                if (api == null) {
                    init();
                }
            }
        }
        return api;
    }

    public interface Api {

        @GET("/music/user/login")
        Call<Result<UserInfo>> login(@Query("userName") String userName,
                                     @Query("pwd") String pwd);

        @GET("banners")
        Call<Data<BannerBean>> getBanners();
    }

}
