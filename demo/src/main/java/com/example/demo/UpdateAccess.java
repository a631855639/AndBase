package com.example.demo;

import android.content.Context;

import com.helen.andbase.access.HBaseAccess;
import com.helen.andbase.access.HRequestCallback;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 李晓伟
 * 2015-3-25 上午10:09:32
 * 检测更新接口
 */
public class UpdateAccess extends HBaseAccess<UpdateInfo> {

	public UpdateAccess(Context c, HRequestCallback<UpdateInfo> requestCallback) {
		super(c, requestCallback);
	}

	public void execute(String version){
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("", ""));
		execute("http://www.baidu.com/",nvps);
	}
	
}
