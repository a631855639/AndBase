package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.helen.andbase.activity.HTitleActivity;
import com.helen.andbase.utils.HLog;
import com.qhad.ads.sdk.adcore.Qhad;
import com.qhad.ads.sdk.interfaces.IQhBannerAd;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.youxiaoad.ssp.core.YouxiaoAd;
import com.youxiaoad.ssp.listener.AdListener;

import org.json.JSONObject;

import java.util.Random;

import th.ds.wa.normal.banner.BannerManager;

/**
 * Created by Helen on 2016/9/27.
 *
 */

public class AdActivity extends HTitleActivity implements View.OnClickListener{
    private static final String TAG = "AdActivity";
    private LinearLayout mLayoutAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        setTitle(getString(com.example.demo.R.string.app_name));
        getLeftIcon().setVisibility(View.GONE);
        mLayoutAd = (LinearLayout) findViewById(R.id.layout_ad);
        findViewById(R.id.btn_refresh).setOnClickListener(this);
        findViewById(R.id.btn_refresh_run).setOnClickListener(this);
        initAd();
    }


    private void initAd() {
        //腾讯广告
        if(!TextUtils.isEmpty(BuildConfig.APP_ID_TX)&&!TextUtils.isEmpty(BuildConfig.AD_ID_TX)){
            final BannerView adViewT = new BannerView(this, ADSize.BANNER, BuildConfig.APP_ID_TX, BuildConfig.AD_ID_TX);
            adViewT.setRefresh(5);
            adViewT.setADListener(new AbstractBannerADListener() {
                @Override
                public void onNoAD(int i) {
                    HLog.d(TAG,"onNoAD----"+i);
                    adViewT.loadAD();
                }

                @Override
                public void onADReceiv() {
                    HLog.d(TAG,"onADReceive");
                }
            });
            adViewT.loadAD();
            mLayoutAd.addView(adViewT);
        }

        //百度广告
        String adPlaceId = BuildConfig.AD_ID_BD;
        if(!TextUtils.isEmpty(adPlaceId)){
            AdView adViewB = new AdView(this,adPlaceId);
            adViewB.setListener(new AdViewListener() {
                @Override
                public void onAdReady(AdView adView) {

                }

                @Override
                public void onAdShow(JSONObject jsonObject) {
                    HLog.d(TAG,"onAdShow");
                }

                @Override
                public void onAdClick(JSONObject jsonObject) {

                }

                @Override
                public void onAdFailed(String s) {
                    HLog.d(TAG,"onFailedToReceivedAd error = "+s);
                }

                @Override
                public void onAdSwitch() {
                    HLog.d(TAG,"onSwitchedAd");
                }

                @Override
                public void onAdClose(JSONObject jsonObject) {

                }
            });
            mLayoutAd.addView(adViewB);
        }
        //360
        if(!TextUtils.isEmpty(BuildConfig.AD_ID_360)){
            IQhBannerAd bannerAd = Qhad.showBanner(mLayoutAd,this,BuildConfig.AD_ID_360,false);
            if(bannerAd != null) {
                bannerAd.showAds(this);
            }
        }

        //有米
        if(!TextUtils.isEmpty(BuildConfig.APP_ID_YM)&&!TextUtils.isEmpty(BuildConfig.SECRET_ID_YM)){
            View adViewY = BannerManager.getInstance(getApplicationContext()).getBanner(this);
            if(adViewY != null) {
                BannerManager.getInstance(getApplicationContext()).setAdListener(new th.ds.wa.normal.banner.AdViewListener() {
                    @Override
                    public void onReceivedAd() {
                        HLog.d(TAG,"onReceivedAd");
                    }

                    @Override
                    public void onSwitchedAd() {
                        HLog.d(TAG,"onSwitchedAd");
                    }

                    @Override
                    public void onFailedToReceivedAd() {
                        HLog.d(TAG,"onFailedToReceivedAd");
                    }
                });
                mLayoutAd.addView(adViewY);
            }
        }

        //优效
        if(!TextUtils.isEmpty(BuildConfig.APP_ID_YX)&&!TextUtils.isEmpty(BuildConfig.AD_ID_YX)){
            YouxiaoAd youxiaoAd = new YouxiaoAd(this, BuildConfig.APP_ID_YX, BuildConfig.AD_ID_YX, new AdListener() {
                @Override
                public void onFail(String s) {
                    HLog.d(TAG,"onFail "+s);
                }

                @Override
                public void onBack(String s) {
                    HLog.d(TAG,"onBack "+s);
                }

                @Override
                public void onAdReceive(String s) {
                    HLog.d(TAG,"onAdReceive "+s);
                }

                @Override
                public void onExposure(String s) {
                    HLog.d(TAG,"onExposure "+s);
                }

                @Override
                public void onClickAd(String s) {
                    HLog.d(TAG,"onClickAd "+s);
                }
            });
            View adViewYX = youxiaoAd.showBannerAD(false,false);
            if(adViewYX != null) {
                mLayoutAd.addView(adViewYX);
            }
        }
    }

    private boolean isAutoRefresh = false;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_refresh:
                isAutoRefresh = false;
                refreshAd();
                break;
            case R.id.btn_refresh_run:
                isAutoRefresh = true;
                startRefreshRunnable();
                break;
        }
    }

    private Handler mHandler = new Handler();
    private void refreshAd(){
        mLayoutAd.removeAllViews();
        initAd();
        startEventRunnable();
    }

    private static final int TIME_COUNT_EVENT = 10*1000;
    private void startEventRunnable(){
        stopEventRunnable();
        mHandler.postDelayed(mEventRunnable,TIME_COUNT_EVENT);
    }
    private void stopEventRunnable(){
        mHandler.removeCallbacks(mEventRunnable);
    }
    private EventRunnable mEventRunnable = new EventRunnable();
    private class EventRunnable implements Runnable{
        @Override
        public void run() {
            if(!isDestroyed()) {
                dispatchEvent();
                mHandler.postDelayed(this, TIME_COUNT_EVENT);
            }
        }
    }

    private void dispatchEvent(){
        int x,y,i;
        Random random = new Random();
        i = random.nextInt(100)+1;
        if(i<=50){
            return;
        }
        x = random.nextInt(mLayoutAd.getWidth());
        y = random.nextInt(mLayoutAd.getHeight());

        long downTime = SystemClock.uptimeMillis();
        MotionEvent downEvent = MotionEvent.obtain(downTime,downTime, MotionEvent.ACTION_DOWN,x,y,0);
        mLayoutAd.dispatchTouchEvent(downEvent);
        long upTime = SystemClock.uptimeMillis();
        MotionEvent upEvent = MotionEvent.obtain(upTime,upTime, MotionEvent.ACTION_UP,x,y,0);
        mLayoutAd.dispatchTouchEvent(upEvent);
    }

    private RefreshRunnable mRefreshRunnable = new RefreshRunnable();
    private class RefreshRunnable implements Runnable{
        @Override
        public void run() {
            if(!isDestroyed()) {
                refreshAd();
                mHandler.postDelayed(this,TIME_COUNT_REFRESH);
            }
        }
    }

    private static final int TIME_COUNT_REFRESH = 60*1000;
    private void startRefreshRunnable(){
        stopRefreshRunnable();
        mHandler.post(mRefreshRunnable);
    }

    private void stopRefreshRunnable(){
        mHandler.removeCallbacks(mRefreshRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopEventRunnable();
    }


    @Override
    protected void onResume() {
        super.onResume();
        startEventRunnable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRefreshRunnable();
        stopEventRunnable();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        HLog.d(TAG,"onNewIntent");
    }
}
