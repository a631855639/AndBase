package com.example.demo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.helen.andbase.activity.HTitleActivity;
import com.helen.andbase.utils.AppManager;

public class MainActivity extends HTitleActivity {
	private long currTime=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("测试");
	}

	public void testRxAndroid(View v){
		startActivity(new Intent(this,TestRxAndroidActivity.class));
	}

	public void testTab(View v){
		startActivity(new Intent(this, TestTabActivity.class));
	}
	public void testXListView(View v){
		startActivity(new Intent(this, TestXListViewActivity.class));
	}
	public void testRequest(View v){
		startActivity(new Intent(this, TestRequestActivity.class));
	}
	
	@Override
	public void onBackPressed() {
		if(System.currentTimeMillis()-currTime>2000){
			currTime=System.currentTimeMillis();
			showMsg(MainActivity.this, "再按一次退出程序");
		}else{
			AppManager.getInstance().ExitApp();
		}
	}
	
}
