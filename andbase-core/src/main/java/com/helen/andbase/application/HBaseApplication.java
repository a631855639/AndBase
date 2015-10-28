package com.helen.andbase.application;

import android.app.Application;
import android.graphics.Bitmap.Config;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.helen.andbase.R;
import com.helen.andbase.dao.ServiceAPI;
import com.helen.andbase.utils.AppManager;
import com.helen.andbase.utils.EnvironmentUtil;
import com.helen.andbase.utils.SystemEvent;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;

public class HBaseApplication extends Application implements SystemEvent.IEventListener{
	public static final int LOGOUT_ID = -1;
	@Override
	public void onCreate() {
		super.onCreate();
		String processName = EnvironmentUtil.getProcessName(this,android.os.Process.myPid());
		if(getPackageName().equals(processName)) {
			init();
		}

	}

	/**
	 * if you override this method,must be call super
	 */
	protected void init(){
		HConstant.DIR_BASE = getBaseDirName();
		SystemEvent.addListener(LOGOUT_ID, this);
		HCrashHandler.init(this);
		if(isAuthorityCheck()) {
			ServiceAPI.init(this);
		}
	}

	/**
	 * 是否权限检测
	 */
	protected boolean isAuthorityCheck(){
		return true;
	}

	/**
	 * 项目根目录名称
	 */
	protected String getBaseDirName(){
		return HConstant.DIR_BASE;
	}

	protected void initImageLoader(int failImgId,int emptyImgId,int loadingImgId) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnFail(failImgId>0?failImgId:R.drawable.ic_launcher) // 加载图片出现问题，会显示该图片
		.showImageForEmptyUri(emptyImgId>0?failImgId:R.drawable.ic_launcher)//url为空的时候显示的图片
		.showImageOnLoading(loadingImgId>0?failImgId:R.drawable.ic_launcher)//图片加载过程中显示的图片
		.bitmapConfig(Config.RGB_565)
		.cacheOnDisk(true)//开启硬盘缓存
		.cacheInMemory(true)//内存缓存
		.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY)
				.defaultDisplayImageOptions(options)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileCount(100)
				.diskCacheSize(10*1024*1024)//缓存容量
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCache(new UnlimitedDiskCache(new File(Environment.getExternalStorageDirectory() + HConstant.DIR_CACHE)))
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		L.writeLogs(false);//关闭日志
		ImageLoader.getInstance().init(config);
	}

	@Override
	public void onEvent(Message msg) {
		switch (msg.what){
			case LOGOUT_ID:
				final String message = msg.obj == null?getResources().getString(R.string.no_authority):msg.obj.toString();
				new Thread() {
					@Override
					public void run() {
						Looper.prepare();
						Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
						Looper.loop();
					}
				}.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//退出程序
				AppManager.getInstance().ExitApp();
				break;
		}
	}

}
