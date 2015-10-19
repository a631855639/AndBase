package com.helen.andbase.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.helen.andbase.R;
import com.helen.andbase.service.download.HDownloadCallback;
import com.helen.andbase.service.download.HDownloadThread;

import java.io.File;


public class APKDownloadService extends Service implements HDownloadCallback{
	
	public static final String KEY_DOWNLOAD_URL="download_url";
	public static final String KEY_VERSION="version";
	public static final String KEY_FILE_NAME="FileName";
	/**
	 * 通知栏ID
	 */
	private static final int NOTIFICATION_ID=1;
	private File mApkFile;//apk文件
	private NotificationManager mManager;
	private Notification mNotice;
	private Handler mHandler;//进度更新处理器
	private int mProgress;//进度
	
	private HDownloadThread mDownloadThread;
	private boolean isStop=true;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String mDownloadUrl = intent.getStringExtra(KEY_DOWNLOAD_URL);
		String mVersion = intent.getStringExtra(KEY_VERSION);
		String fileName=intent.getStringExtra(KEY_FILE_NAME);
		initNotification();
		initHandler();
		if(!TextUtils.isEmpty(mDownloadUrl)&&isStop&&mDownloadThread==null){
			mProgress=0;
			String mFileName;
			if(!TextUtils.isEmpty(fileName)){
				mFileName =fileName;
			}else{
				if(!TextUtils.isEmpty(mVersion)){
					mFileName ="hbase_download_"+ mVersion +".apk";
				}else{
					mFileName ="hbase_download_"+System.currentTimeMillis()+".apk";
				}
			}
			HDownloadThread.Builder builder = new HDownloadThread.Builder();
			builder.setFileName(mFileName);
			builder.setDownloadUrl(mDownloadUrl);
			builder.setDownloadCallback(this);
			mDownloadThread = builder.builder();
			isStop=false;
			mDownloadThread.start();
		}else{
			Message msg = new Message();
			msg.what=1;
			mHandler.sendMessage(msg);
		}
		return super.onStartCommand(intent, flags, startId);
	}
	/**
	 * 
	 * 
	 * 2014-7-7 上午10:43:28
	 *  初始化通知栏
	 */
	@SuppressWarnings("deprecation")
	private void initNotification() {
		mNotice = new Notification(R.drawable.ic_launcher, getText(R.string.app_name), System.currentTimeMillis());
		mNotice.flags = Notification.FLAG_ONGOING_EVENT;
		/*Intent intent=new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent=PendingIntent.getActivity(this, 0, intent, 0);*/
		
		RemoteViews contentView=new RemoteViews(getPackageName(), R.layout.notification_download);
		contentView.setTextViewText(R.id.notificationTitle, getText(R.string.app_name));
		contentView.setTextViewText(R.id.notificationPercent, "0%");
		contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);
		
		mNotice.contentView=contentView;
		//mNotice.contentIntent=contentIntent;
		mManager.notify(NOTIFICATION_ID, mNotice);
	}
	/***
	 * 初始化处理器
	 */
	private void initHandler() {
		mHandler = new Handler(new Handler.Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				if(msg.what==0){
					Bundle bundle = msg.getData();
					String title = bundle.getString("title");
					int progress = bundle.getInt("progress");
					updateNotification(title, progress);
					if(progress>=100){
						installApk(mApkFile);
					}
				}else if(msg.what==1){//文件下载异常
					stopSelf();
					Toast.makeText(getApplicationContext(), "文件下载失败!", Toast.LENGTH_SHORT).show();
					mNotice.contentView.setTextViewText(R.id.notificationPercent, "文件下载失败！");
					mNotice.flags = Notification.FLAG_AUTO_CANCEL;
					mManager.notify(NOTIFICATION_ID, mNotice);
				}
				return true;
			}
		});
	}
	/**
	 * 
	 * 
	 * 2014-7-7 上午10:46:15
	 *  更新通知栏
	 */
	private void updateNotification(String title,int progress){
		if(progress>=100){
			Intent intent=new Intent(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.fromFile(mApkFile), "application/vnd.android.package-archive");
			mNotice.contentIntent=PendingIntent.getActivity(this, 0, intent, 0);;
			mNotice.flags = Notification.FLAG_AUTO_CANCEL;
		}
		mNotice.contentView.setTextViewText(R.id.notificationTitle, title);
		mNotice.contentView.setTextViewText(R.id.notificationPercent, progress + "%");
		mNotice.contentView.setProgressBar(R.id.notificationProgress, 100, progress, false);
		mManager.notify(NOTIFICATION_ID, mNotice);
	}
	/**
	 * 
	 * 
	 * 2014-7-8 上午10:39:39
	 *  安装apk
	 */
	protected void installApk(File file) {
		stopSelf();
		if(file!=null){
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//执行的数据类型
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			startActivity(intent);
		}
	}
	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}
	
	@Override
	public void onCreate() {
		mManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		isStop=true;
		mDownloadThread=null;
	}

	@Override
	public void onSuccess(File file) {
		mApkFile = file;
		mManager.cancel(NOTIFICATION_ID);
		installApk(mApkFile);
	}
	private int count = 0;
	@Override
	public void onProgress(int progress, int total) {
		mProgress=(int) (((float) progress / total) * 100);
		String title="下载中...";
		if(mProgress>=100){
			title="下载完成，点击安装！";
		}
		//每增长3%刷新一次
		if(mProgress-count >= 3||mProgress>=100){
			count = mProgress;
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("title", title);
			bundle.putInt("progress", mProgress);
			msg.setData(bundle);
			msg.what=0;
			mHandler.sendMessage(msg);
		}
	}

	@Override
	public void onFail(int code) {
		Message msg = new Message();
		msg.what = 1;
		mHandler.sendMessage(msg);
	}
}
