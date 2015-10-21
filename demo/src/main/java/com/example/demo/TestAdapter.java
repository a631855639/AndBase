package com.example.demo;

import android.content.Context;
import android.widget.TextView;

import com.helen.andbase.adapter.HBaseAdapter;
import com.helen.andbase.adapter.ViewHolder;

import java.util.List;
import java.util.Map;


public class TestAdapter extends HBaseAdapter<Map<String, String>> {

	public TestAdapter(Context c, List<Map<String, String>> datas) {
		super(c, datas);
	}

	@Override
	public void convert(ViewHolder holder, Map<String, String> bean,int position) {
		((TextView)holder.getView(android.R.id.text1)).setText(bean.get("title"));
	}

	@Override
	public int getResId() {
		return android.R.layout.simple_list_item_1;
	}

}
