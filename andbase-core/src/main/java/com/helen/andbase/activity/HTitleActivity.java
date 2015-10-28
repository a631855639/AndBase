package com.helen.andbase.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helen.andbase.R;

/**
 * 
 *  Helen
 * 2015-3-9 下午5:49:07
 *
 */
public class HTitleActivity extends HBaseActivity{

	protected LinearLayout mRootView;
	private FrameLayout mLayoutContent;
	private RelativeLayout mLayoutError;

	private ImageView mLeftIcon;
	private ImageView mRightIcon;
	
	private TextView mTitleView;
	private TextView mErrorView;
	private AnimationDrawable anim;
	private LinearLayout mLayoutTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	public void setContentView(int layoutResID) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(layoutResID, null);
		setContentView(v);
	}

	@Override
	public void setContentView(View view) {
		mRootView = (LinearLayout) View.inflate(this, R.layout.activity_base, null);
		//init title
		BaseOnClickListener listener=new BaseOnClickListener();
		mLayoutContent=(FrameLayout) mRootView.findViewById(R.id.fragment_content);
		LinearLayout mLayoutCustomView = (LinearLayout) mRootView.findViewById(R.id.layout_custom_title_view);
		mLeftIcon=(ImageView) mRootView.findViewById(R.id.iv_left_icon);
		mLeftIcon.setOnClickListener(listener);
		mRightIcon=(ImageView) mRootView.findViewById(R.id.iv_right_icon);
		mRightIcon.setOnClickListener(listener);
		mLayoutTitle = (LinearLayout) mRootView.findViewById(R.id.layout_action_bar);
		setCustomView(mLayoutCustomView);
		hideErrorView();
		mLayoutContent.addView(view);

		super.setContentView(mRootView);
	}

	/**
	 * 
	 *  Helen
	 * 2015-3-5 上午9:58:43
	 *  自定义标题栏
	 */
	protected void setCustomView(LinearLayout customViewLayout){
		LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1);
		params.gravity=Gravity.CENTER;
		View view=View.inflate(this,R.layout.item_action_bar_title, null);
		mTitleView=(TextView) view.findViewById(R.id.action_bar_title);
		mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		customViewLayout.addView(view, params);
	}


	@Override
	public void setTitle(CharSequence title) {
		setActionBarTitle(title);
	}

	@Override
	public void setTitle(int titleId) {
		setActionBarTitle(titleId);
	}


	private void setActionBarTitle(CharSequence title){
		if(mTitleView!=null){
			mTitleView.setText(title);
		}
	}

	private void setActionBarTitle(int resId){
		if(mTitleView!=null){
			mTitleView.setText(resId);
		}
	}

	/**
	 * 
	 *  Helen
	 * 2015-3-9 上午11:01:33
	 *  页面跳转
	 */
	public void jump(Context c,Class<?> clazz){
		Intent intent=new Intent(c, clazz);
		startActivity(intent);
	}
	public void jump(Context c,Class<?> clazz,Bundle bundle){
		Intent intent=new Intent(c, clazz);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	/**
	 * 
	 *  Helen
	 * 2015-3-12 上午11:56:51
	 *  隐藏错误页面
	 */
	protected void hideErrorView(){
		mLayoutContent.setVisibility(View.VISIBLE);
		if(mLayoutError!=null)mLayoutError.setVisibility(View.GONE);
		if(anim!=null){
			anim.stop();
			anim=null;
		}
	}
	
	/**
	 * 
	 *  Helen
	 *  2015-3-12 上午11:18:13
	 *  按钮点击事件
	 */
	private class BaseOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			final int id = v.getId();
			if(id == R.id.iv_left_icon){
				onBackPressed();
			}/*else if(id == R.id.iv_right_icon){
			}*/else if(id == R.id.layout_error){
				onReload();
			}
		}
		
	}
	/**
	 * 
	 *  Helen
	 * 2015-3-9 下午5:11:39
	 *  show the error message
	 */
	protected void onLoadFail(){
		if(mErrorView == null){
			mErrorView=(TextView) getLayoutError().findViewById(R.id.img_error);
			anim =(AnimationDrawable) mErrorView.getCompoundDrawables()[1];
		}
		onLoadFail(mErrorView);
		if(anim!=null){
			anim.start();
		}
	}


	/**
	 * show the error message
	 * @param errorMsg error message
	 */
	protected void onLoadFail(String errorMsg){
		onLoadFail();
		mErrorView.setText(errorMsg);
	}

	/**
	 * onLoadFail() and this method can only one be called.
	 * @link onLoadFail()
	 * @param view custom error view
	 */
	protected void onLoadFail(View view){
		if(view == null) return;
		view.setTag("error_view");
		if(mLayoutError == null){
			mLayoutError = getLayoutError();
		}
		mLayoutError.setVisibility(View.VISIBLE);
		mLayoutContent.setVisibility(View.GONE);
		View errorView = mLayoutError.findViewWithTag("error_view");
		if(errorView == null){
			mLayoutError.removeAllViews();
			mLayoutError.addView(view);
		}
	}
	/**
	 * 
	 *  Helen
	 * 2015-3-9 下午4:10:47
	 *  触摸layout_error，重新加载
	 */
	protected void onReload(){

	}
	
	/**
	 * 
	 *  Helen
	 * 2015-3-9 上午10:18:37
	 *  提示信息
	 */
	protected void showMsg(Context c,String msg){
		Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
	}
	protected void showMsg(Context c,int resId){
		Toast.makeText(c, resId, Toast.LENGTH_SHORT).show();
	}
	protected ImageView getLeftIcon() {
		return mLeftIcon;
	}

	/**
	 * set left ImageView src
	 * @param resId resource id
	 */
	protected void setLeftIconBackgroundResource(int resId){
		mLeftIcon.setImageResource(resId);
	}

	protected ImageView getRightIcon() {
		return mRightIcon;
	}
	/**
	 * set right ImageView src
	 * @param resId resource id
	 */
	protected void setRightIconBackgroundResource(int resId){
		mRightIcon.setImageResource(resId);
	}

	protected TextView getTitleView() {
		return mTitleView;
	}

	protected void setTitleTextColor(int color){
		mTitleView.setTextColor(color);
	}

	protected void setTitleBarBackgroundColor(int color){
		mLayoutTitle.setBackgroundColor(color);
	}

	protected void setTitleBarBackgroundResource(int resId){
		mLayoutTitle.setBackgroundResource(resId);
	}


	protected FrameLayout getLayoutContent() {
		return mLayoutContent;
	}

	protected RelativeLayout getLayoutError() {
		if(mLayoutError==null){
			View view=((ViewStub) mRootView.findViewById(R.id.vs_error)).inflate();
			mLayoutError = (RelativeLayout) view.findViewById(R.id.layout_error);
			mLayoutError.setOnClickListener(new BaseOnClickListener());

		}
		return mLayoutError;
	}
}
