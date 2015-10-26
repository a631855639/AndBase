package com.helen.andbase.service.download;

/**
 * Created by Helen on 2015/10/15.
 * 下载器
 */
public class HDownloader {
    //private static final ExecutorService executorService= Executors.newCachedThreadPool();
    private static HDownloader Instance;
    private HDownloader(){}


    public static HDownloader getInstance(){
        if(Instance == null){
            Instance = new HDownloader();
        }
        return Instance;
    }


}
