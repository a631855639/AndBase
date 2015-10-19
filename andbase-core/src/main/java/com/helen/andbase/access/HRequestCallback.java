package com.helen.andbase.access;

import android.content.Context;
import android.widget.Toast;


public abstract class HRequestCallback<T>{
	/**
	 * 成功
	 */
	public static final int RESULT_SUCCESS=1000;
	/**
	 * 发生未知异常
	 */
	public static final int RESULT_EXCEPTION=1001;
	/**
	 * json转换为空
	 */
	public static final int RESULT_EMPTY=1002;
	/**
	 * 网络异常
	 */
	public static final int RESULT_NETWORK_EXCEPTION=1003;
	/**
	 * 请求超时
	 */
	public static final int RESULT_TIMEOUT_EXCEPTION=1004;
	/**
	 * 服务器异常
	 */
	public static final int RESULT_SERVER_EXCEPTION=1005;
	/**
	 * 
	 *  李晓伟
	 *  2015-3-4 下午12:54:14
	 *  获取数据失败时调用
	 */
	public void onFail(Context c,String errorMsg){
		Toast.makeText(c, errorMsg, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 
	 *  李晓伟
	 *  2015-3-4 下午12:54:30
	 *  获取数据成功时调用，在此处理业务逻辑
	 */
	public abstract void onSuccess(T result);
	/**
	 * 
	 *  李晓伟
	 *  2015-3-4 下午12:54:51
	 *  解析json格式，转换对应实体类
	 */
	public abstract T parseJson(String jsonStr);
}
