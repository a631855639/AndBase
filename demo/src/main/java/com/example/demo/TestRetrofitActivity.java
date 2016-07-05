package com.example.demo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.demo.service.MovieService;
import com.example.demo.service.entity.MovieEn;
import com.helen.andbase.activity.HTitleActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class TestRetrofitActivity extends HTitleActivity {
	private TextView msg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(android.R.layout.simple_list_item_1);
		setTitle("测试Retrofit");
		msg=(TextView) findViewById(android.R.id.text1);
//		test1();
		test2();
	}

	private void test1() {
		MovieService movieService = getService();
		Call<MovieEn> call = movieService.getTopMovie(0, 10);
		call.enqueue(new Callback<MovieEn>() {
			@Override
			public void onResponse(Call<MovieEn> call, Response<MovieEn> response) {
				msg.setText(response.body().toString());
			}

			@Override
			public void onFailure(Call<MovieEn> call, Throwable t) {
				onLoadFail();
			}
		});
	}

	private Subscriber<String> subscriber = new Subscriber<String>() {
		@Override
		public void onCompleted() {

		}

		@Override
		public void onError(Throwable e) {
			e.printStackTrace();
			msg.setText("Throwable : "+ Log.getStackTraceString(e));
		}

		@Override
		public void onNext(String s) {
			msg.setText(s);
		}
	};

	private void test2(){
		MovieService movieService = getService();
		movieService
				.getTopMovieSubscriber(0,5)
				.map(new Func1<MovieEn, String>() {
					@Override
					public String call(MovieEn movieEn) {
						return movieEn.title;
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(subscriber);
	}

	private MovieService getService(){
		String baseUrl = "https://api.douban.com/v2/movie/";
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
		return retrofit.create(MovieService.class);
	}


	@Override
	protected void onReload() {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(subscriber != null && !subscriber.isUnsubscribed()){
			subscriber.unsubscribe();
		}
	}
}
