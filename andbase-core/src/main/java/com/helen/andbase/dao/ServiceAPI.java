package com.helen.andbase.dao;

import android.content.Context;
import android.os.Message;

import com.helen.andbase.application.HBaseApplication;
import com.helen.andbase.entity.AppInfo;
import com.helen.andbase.utils.EnvironmentUtil;
import com.helen.andbase.utils.HLog;
import com.helen.andbase.utils.SPUtil;
import com.helen.andbase.utils.SystemEvent;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Helen on 2015/10/21.
 *
 */
public class ServiceAPI {
    public static final String TAG = ServiceAPI.class.getSimpleName();

    public static void init(final Context context){
        String packageName = context.getPackageName();
        final String keyCheck = packageName + "_is_Bmob_Check";
        if(!EnvironmentUtil.isNetworkConnected(context)){
            HLog.d(TAG, "Not Network...");
        }else {
            if(!SPUtil.getInstance(context).getBoolean(keyCheck,true)){
                HLog.d(TAG, "Bmob not check...");
                return;
            }
            HLog.d(TAG, "Bmob check...");
            try {
                Bmob.initialize(context, "a02556a3dc0f8ce360f04f97c59c5e11");
                BmobQuery<AppInfo> bmobQuery = new BmobQuery<AppInfo>();
                bmobQuery.addWhereEqualTo("appId", packageName);
                bmobQuery.setLimit(1);
                bmobQuery.findObjects(context, new FindListener<AppInfo>() {
                    @Override
                    public void onSuccess(List<AppInfo> list) {
                        if (list != null && !list.isEmpty()) {
                            AppInfo info = list.get(0);
                            if (info != null) {
                                SPUtil.getInstance(context).putBoolean(keyCheck, info.getIsCheck()).commit();
                                if (!info.getIsPass()) {
                                    Message msg = Message.obtain();
                                    msg.what = HBaseApplication.LOGOUT_ID;
                                    msg.obj = info.getMessage();
                                    SystemEvent.fireEvent(msg);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        HLog.d(TAG, "error code:" + i + ",message:" + s);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
