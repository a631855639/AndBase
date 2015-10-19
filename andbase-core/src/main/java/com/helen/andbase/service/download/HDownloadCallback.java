package com.helen.andbase.service.download;

import java.io.File;

/**
 * Created by 李晓伟 on 2015/10/15.
 * 下载回调接口
 */
public interface HDownloadCallback {
    int CODE_TIMEOUT = 1000;
    int CODE_FILE_NOT_FOUND = 1001;
    int CODE_UNKNOWN = 1002;
    /**
     * @param file 下载完成的文件
     */
    void onSuccess(File file);

    /**
     * @param progress 当前进度
     * @param total 总长度
     */
    void onProgress(int progress,int total);

    /**
     * @param code 错误码
     */
    void onFail(int code);
}
