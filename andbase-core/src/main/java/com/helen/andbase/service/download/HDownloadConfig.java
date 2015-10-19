package com.helen.andbase.service.download;

import android.text.TextUtils;

import java.io.FileNotFoundException;

/**
 * Created by 李晓伟 on 2015/10/15.
 * 下载器配置项
 */
class HDownloadConfig {
    private String mFileDir;//文件存放的文件夹路径
    private String mFileName;//文件名称
    private String mDownloadUrl;//下载的URL
    private HDownloadCallback mCallback;//下载回调
    public HDownloadConfig(String mFileDir, String mFileName, String mDownloadUrl, HDownloadCallback mCallback) {
        this.mFileDir = mFileDir;
        this.mFileName = mFileName;
        this.mDownloadUrl = mDownloadUrl;
        this.mCallback = mCallback;
    }

    public String getFileDir() {
        return mFileDir;
    }

    public void setFileDir(String mFileDir) {
        this.mFileDir = mFileDir;
    }

    public String getFileName() throws FileNotFoundException {
        if(TextUtils.isEmpty(mFileName)){
            throw new FileNotFoundException("请配置要保存的文件名称");
        }
        return mFileName;
    }

    public void setFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String mDownloadUrl) {
        this.mDownloadUrl = mDownloadUrl;
    }

    public HDownloadCallback getCallback() {
        return mCallback;
    }

    public void setCallback(HDownloadCallback mCallback) {
        this.mCallback = mCallback;
    }
}
