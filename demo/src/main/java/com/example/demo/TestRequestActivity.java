package com.example.demo;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.helen.andbase.access.HRequestCallback;
import com.helen.andbase.activity.HTitleActivity;
import com.helen.andbase.utils.JsonUtil;


public class TestRequestActivity extends HTitleActivity {
	private TextView msg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(android.R.layout.simple_list_item_1);
		setTitle("测试数据请求");
		msg=(TextView) findViewById(android.R.id.text1);
		request();
	}

	private void request() {
		HRequestCallback<UpdateInfo> requestCallback = new HRequestCallback<UpdateInfo>() {
			
			@Override
			public void onFail(Context c, String errorMsg) {
				super.onFail(c, errorMsg);
				onLoadFail();
			}
			
			@Override
			public void onSuccess(UpdateInfo result) {
				msg.setText(result.toString());
			}
			@Override
			public UpdateInfo parseJson(String jsonStr) {
				return (UpdateInfo) JsonUtil.jsonToBean(jsonStr, UpdateInfo.class);
			}
		};
		UpdateAccess access = new UpdateAccess(this, requestCallback);
		access.execute("");
	}
}
