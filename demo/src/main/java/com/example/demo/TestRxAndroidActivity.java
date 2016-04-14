package com.example.demo;

import android.os.Bundle;
import android.widget.TextView;

import com.helen.andbase.activity.HTitleActivity;
import com.helen.andbase.utils.HLog;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class TestRxAndroidActivity extends HTitleActivity {
	private TextView msg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(android.R.layout.simple_list_item_1);
		setTitle("测试RxAndroid");
		msg=(TextView) findViewById(android.R.id.text1);
		request();
	}

	private void request() {
		/*String baseUrl = "https://api.douban.com/v2/movie/";
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				//.addConverterFactory(GsonConverterFactory.create())
				.build();
		MovieService movieService = retrofit.create(MovieService.class);
		Call<String> call = movieService.getTopMovie(0, 10);
		call.enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {
				msg.setText(response.body());
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {
				onLoadFail();
			}
		});*/
		Observable.just(1,2,3,4)
				.map(new Func1<Integer, String>() {
					@Override
					public String call(Integer integer) {
						return integer.toString();
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<String>() {
					@Override
					public void call(String integer) {
						HLog.d(MainActivity.class.getSimpleName(),"number:"+integer);
					}
				});
	}

	@Override
	protected void onReload() {
	}
}
