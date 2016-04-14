package com.example.demo.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 李晓伟 on 2016/4/7.
 *
 */
public interface MovieService {
    @GET("top250")
    Call<String> getTopMovie(@Query("start") int start,@Query("count") int count);
}
