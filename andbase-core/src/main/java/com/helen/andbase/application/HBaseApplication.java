package com.helen.andbase.application;

import android.app.Application;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.helen.andbase.R;
import com.helen.andbase.dao.ServiceAPI;
import com.helen.andbase.utils.AppManager;
import com.helen.andbase.utils.EnvironmentUtil;
import com.helen.andbase.utils.FileUtil;
import com.helen.andbase.utils.SystemEvent;

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

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
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
		initFresco();
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

	protected void initFresco(){
		DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
				.setBaseDirectoryPath(new File(FileUtil.getInstance().getSDCardRoot()))
				.setBaseDirectoryName(HConstant.DIR_IMAGE_CACHE)
				.setMaxCacheSize(50 * ByteConstants.MB)
				.setMaxCacheSizeOnLowDiskSpace(10 * ByteConstants.MB)
				.setMaxCacheSizeOnVeryLowDiskSpace(2 * ByteConstants.MB)
				.build();
		ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
				//.setNetworkFetcher()
				.setMainDiskCacheConfig(diskCacheConfig).build();
		Fresco.initialize(this, config);
		//清理fresco缓存
		//Fresco.getImagePipeline().clearCaches();
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
