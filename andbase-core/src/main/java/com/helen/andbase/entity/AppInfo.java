package com.helen.andbase.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Helen on 2015/10/21.
 *
 */
public class AppInfo extends BmobObject{
    private String appId;//包名
    private Boolean isPass;//是否验证通过
    private Boolean isCheck;//是否需要检测
    private String message;//提示语

    public Boolean getIsCheck() {
        if(isCheck == null){
            isCheck = true;
        }
        return isCheck;
    }

    public void setIsCheck(Boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Boolean getIsPass() {
        if(isPass == null){
            isPass = false;
        }
        return isPass;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appId='" + appId + '\'' +
                ", isPass=" + isPass +
                ", isCheck=" + isCheck +
                '}';
    }
}
