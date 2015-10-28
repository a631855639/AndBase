package com.example.demo;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.helen.andbase.access.HRequestCallback;
import com.helen.andbase.activity.HTitleActivity;


public class TestRequestActivity extends HTitleActivity {
	private TextView msg;
	UpdateAccess access;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(android.R.layout.simple_list_item_1);
		setTitle("测试数据请求");
		msg=(TextView) findViewById(android.R.id.text1);
		request();
	}

	private void request() {
		HRequestCallback<Respond> requestCallback = new HRequestCallback<Respond>() {
			
			@Override
			public void onFail(Context c, String errorMsg) {
				super.onFail(c, errorMsg);
				onLoadFail();
			}
			
			@Override
			public void onSuccess(Respond result) {
				msg.setText(result.toString());
			}
		};
		access = new UpdateAccess(this, requestCallback);
		access.execute("");
	}

	@Override
	protected void onReload() {
		access.execute("");
	}
}
