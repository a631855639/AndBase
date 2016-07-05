package com.example.demo.service;

import com.example.demo.service.entity.MovieEn;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 李晓伟 on 2016/4/7.
 *
 */
public interface MovieService {
    @GET("top250")
    Call<MovieEn> getTopMovie(@Query("start") int start, @Query("count") int count);
    @GET("top250")
    Observable<MovieEn> getTopMovieSubscriber(@Query("start") int start, @Query("count") int count);
}
