package com.helen.andbase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class HBaseAdapter<T> extends BaseAdapter {
	private List<T> mDatas;
	private Context mContext;
	private int mResId;
	
	public HBaseAdapter(Context c,List<T> datas){
		this.mContext=c;
		this.mDatas=datas;
		this.mResId=getResId();
	}
	@Override
	public int getCount() {
		if(mDatas !=null){
			return mDatas.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(mDatas!=null&&position<mDatas.size()){
			return mDatas.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(mResId, null);
		}
		T bean=mDatas.get(position);
		ViewHolder holder=ViewHolder.get(convertView);
		convert(holder, bean, position);
		return holder.getConvertView();
	}
	/**
	 * 
	 * Helen
	 * 2015-3-25 下午6:04:33
	 *  设置布局内容
	 */
	public abstract void convert(ViewHolder holder, T bean, int position);
	/**
	 * 
	 * Helen
	 * 2015-3-25 下午6:04:16
	 *  获取布局文件id
	 */
	public abstract int getResId();
	
}
