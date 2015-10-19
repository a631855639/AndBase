package com.helen.andbase.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.helen.andbase.widget.viewpagerindicator.IconPagerAdapter;

import java.util.List;

/**
 * 
 * Helen
 *  2015-3-3 上午10:08:16
 *  选项卡适配器
 */
public class TabFragmentAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {
	private String[] titles;
	private int[] icons;
	private List<? extends Fragment> fragments;
	public TabFragmentAdapter(FragmentManager fm, String[] titles, int[] icons, List<? extends Fragment> fragments) {
		super(fm);
		this.fragments=fragments;
		this.icons=icons;
		this.titles=titles;
	}

	@Override
	public int getIconResId(int index) {
		if(icons!=null) return icons[index%icons.length];
		return 0;
	}

	@Override
	public Fragment getItem(int arg0) {
		
		if(fragments!=null) {
			if(fragments.size()!=titles.length){
				throw new IllegalArgumentException("Fragment List size must equals title's length");
			}
			return fragments.get(arg0);
		}
		return null;
	}

	@Override
	public int getCount() {
		return titles.length;
	}
	@Override
	public CharSequence getPageTitle(int position) {
		if(titles!=null){
			return titles[position%titles.length];
		}
		return super.getPageTitle(position);
	}
}
