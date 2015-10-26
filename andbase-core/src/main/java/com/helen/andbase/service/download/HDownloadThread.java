package com.helen.andbase.service.download;

import com.helen.andbase.application.HConstant;
import com.helen.andbase.utils.FileUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Helen on 2015/10/15.
 * 下载线程
 */
public class HDownloadThread extends Thread{
    private boolean isStop = false;

    private HDownloadConfig mConfig;

    private HDownloadThread(){}
    private HDownloadThread(HDownloadConfig config){
        this.mConfig = config;
    }

    public HDownloadConfig getConfig() {
        return mConfig;
    }

    public void setConfig(HDownloadConfig mConfig) {
        this.mConfig = mConfig;
    }
    @Override
    public void run() {
        if(!isStop){
            BufferedOutputStream bos = null;
            InputStream is = null;
            HDownloadCallback callback = mConfig.getCallback();
            try {
                //下载完成的文件
                File mLoadingFile = FileUtil.getInstance().createFileInSDCard(mConfig.getFileDir(), mConfig.getFileName());

                URL url=new URL(mConfig.getDownloadUrl());
                URLConnection conn=url.openConnection();
                conn.setConnectTimeout(5000);
                int len=conn.getContentLength();
                long fileLen = mLoadingFile.length();
                /**如果已经存在安装文件，则直接安装，无需下载*/
                if(fileLen == len){
                    if(callback != null){
                        callback.onSuccess(mLoadingFile);
                    }
                    isStop=true;
                    return;
                }
                is = conn.getInputStream();
                bos = new BufferedOutputStream(new FileOutputStream(mLoadingFile,false));
                byte buffer[] = new byte[1024];
                int temp;
                int count = 0;
                while((temp = is.read(buffer)) != -1){
                    bos.write(buffer, 0, temp);
                    count += temp;
                    if(count >= len){
                        if(callback != null){
                            callback.onSuccess(mLoadingFile);
                        }
                    }else {
                        if(callback != null){
                            callback.onProgress(count,len);
                        }
                    }
                }
                bos.flush();
            }catch (IOException e){
                e.printStackTrace();
                if(e instanceof SocketTimeoutException){
                    if(callback != null){
                        callback.onFail(HDownloadCallback.CODE_TIMEOUT);
                    }
                }else if(e instanceof FileNotFoundException){
                    if(callback != null){
                        callback.onFail(HDownloadCallback.CODE_FILE_NOT_FOUND);
                    }
                }else{
                    if(callback != null){
                        callback.onFail(HDownloadCallback.CODE_UNKNOWN);
                    }
                }

            }finally {
                try {
                    if(bos != null){
                        bos.close();
                    }
                    if(is != null){
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            isStop=true;
        }
    }

    public static class Builder{
        HDownloadThread downloadThread = new HDownloadThread(new HDownloadConfig(HConstant.DIR_DOWNLOAD,"","",null));
        public Builder(){

        }
        public Builder setDownloadUrl(String url){
            downloadThread.getConfig().setDownloadUrl(url);
            return this;
        }

        public Builder setFileDir(String fileDir){
            downloadThread.getConfig().setFileDir(fileDir);
            return this;
        }

        public Builder setFileName(String fileName){
            downloadThread.getConfig().setFileName(fileName);
            return this;
        }

        public Builder setDownloadCallback(HDownloadCallback callback){
            downloadThread.getConfig().setCallback(callback);
            return this;
        }

        public HDownloadThread builder(){
            return downloadThread;
        }
    }

}
