package com.dn.module_main.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MusicApi {

    // Call<Object> 设置实体返回需要addConverterFactory(GsonConverterFactory.create())
    @GET("/v1/restserver/ting")
    Call<String> getMusics(@Query("method") String method,
                           @Query("type") int type,
                           @Query("size") int size,
                           @Query("offset") int offset);

}