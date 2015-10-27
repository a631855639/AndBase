package com.example.demo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李晓伟 on 2015/10/27.
 *
 */
public class Respond implements Serializable{
    private String message;
    private boolean isSuccess;
    private UpdateInfo data;
    private List<UpdateInfo> dataList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public UpdateInfo getData() {
        return data;
    }

    public void setData(UpdateInfo data) {
        this.data = data;
    }

    public List<UpdateInfo> getDataList() {
        return dataList;
    }

    public void setDataList(List<UpdateInfo> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "Respond{" +
                "message='" + message + '\'' +
                ", isSuccess=" + isSuccess +
                ", data=" + data +
                ", dataList=" + dataList +
                '}';
    }
}
