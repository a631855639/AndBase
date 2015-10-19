package com.helen.andbase.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.helen.andbase.R;
import com.helen.andbase.adapter.TabFragmentAdapter;
import com.helen.andbase.widget.viewpagerindicator.TabPageIndicator;

import java.util.List;

/**
 * 
 * Helen
 *  2015-3-25 下午2:00:42
 *  
 * 带有Tab选项卡的activity
 */
public abstract class HBaseTabPageActivity extends HTitleActivity {
	private String[] titles;
	private int[] icons;
	private List<? extends Fragment> fragments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.StyledIndicators);
		setContentView(R.layout.item_tab_page);
		init();
		titles=getTitles();
		icons=getIcons();
		fragments=getFragments();
		initTab();
	}
	
	private void initTab() {
		TabFragmentAdapter adapter=new TabFragmentAdapter(getSupportFragmentManager(), titles, icons, fragments);
		ViewPager tabPager=(ViewPager) findViewById(R.id.tab_pager);
		tabPager.setAdapter(adapter);
		//tabPager.setOffscreenPageLimit(titles.length);
		TabPageIndicator mTabIndicator= (TabPageIndicator)findViewById(R.id.tab_indicator);
		mTabIndicator.setViewPager(tabPager);
	}
	/**
	 * 
	 * Helen
	 * 2015-3-25 下午2:07:18
	 *  设置选项卡标题
	 */
	public abstract String[] getTitles();
	/**
	 * 
	 * Helen
	 * 2015-3-25 下午2:07:27
	 *  设置选项卡图片
	 */
	public abstract int[] getIcons();
	/**
	 * 
	 * Helen
	 * 2015-3-25 下午2:07:47
	 *  设置选项卡内容
	 */
	public abstract List<? extends Fragment> getFragments();
	/**
	 * 
	 * Helen
	 * 2015-3-25 下午2:23:11
	 *  做一些初始化工作
	 */
	public abstract void init();
	
}
