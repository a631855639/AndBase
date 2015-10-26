package com.helen.andbase.dao;

import android.content.Context;

import com.helen.andbase.application.HBaseApplication;
import com.helen.andbase.entity.AppInfo;
import com.helen.andbase.utils.EnvironmentUtil;
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
    public static void init(final Context context){
        String packageName = context.getPackageName();
        final String keyCheck = packageName + "_is_Bmob_Check";
        if(!EnvironmentUtil.isNetworkConnected(context)){
            System.out.println("Not Network...");
        }else {
            if(!SPUtil.getInstance(context).getBoolean(keyCheck,true)){
                System.out.println("Bmob not check...");
                return;
            }
            System.out.println("Bmob check...");
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
                                System.out.println(info);
                                SPUtil.getInstance(context).putBoolean(keyCheck, info.getIsCheck()).commit();
                                if (!info.getIsPass()) {
                                    SystemEvent.fireEvent(HBaseApplication.LOGOUT_ID);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        System.out.println("error code:" + i + ",message:" + s);
                    }
                });
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

    }
}
