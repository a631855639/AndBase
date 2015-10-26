package com.helen.andbase.utils;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Helen on 2015/10/21.
 * 事件分发器
 */
public class SystemEvent {

    private static final String TAG = "SystemEvent";
    private static SparseArray<ArrayList<WeakReference<IEventListener>>> mEventMap = new SparseArray<ArrayList<WeakReference<IEventListener>>>();
//    private static Map<Integer, ArrayList<WeakReference<IEventListener>>> mEventMap = new HashMap<Integer, ArrayList<WeakReference<IEventListener>>>();
//    private static Map<Integer, ArrayList<WeakReference<IEventListener>>> mTmpEventMap = new HashMap<Integer, ArrayList<WeakReference<IEventListener>>>();

    /**
     * 自定义的事件监听器
     */
    public interface IEventListener {
        void onEvent(Message msg);
    }

    /**
     * 暂存监听器
     */
    /*public static void storeListener() {
        mTmpEventMap.putAll(mEventMap);
        mEventMap.clear();
    }*/

    /**
     * 还原监听器
     */
    /*public static void restoreListener() {
        mEventMap.putAll(mTmpEventMap);
        mTmpEventMap.clear();
    }*/

    /**
     * 加入监听器
     * @param eventType eventType > 0
     */
    public static void addListener(int eventType, IEventListener listener) {
        ArrayList<WeakReference<IEventListener>> list = mEventMap.get(eventType);
        if (null == list)
            list = new ArrayList<WeakReference<IEventListener>>();
        // 如果已经存在同一个监听者，就不添加
        for (WeakReference<IEventListener> weakReference : list) {
            IEventListener temp=weakReference.get();
            if (temp!=null && temp.equals(listener)) {
                return;
            }
        }
        WeakReference<IEventListener> wrf = new WeakReference<IEventListener>(
                listener);
        list.add(wrf);
        mEventMap.put(eventType, list);
    }

    /**
     * 移除监听器
     */
    public static void removeListener(int eventType, IEventListener listener) {
        ArrayList<WeakReference<IEventListener>> list = mEventMap.get(eventType);
        if (null == list)
            return;
        for (int i = 0; i < list.size(); i++) {
            IEventListener l = list.get(i).get();
            if (l != null && l.equals(listener)) {
                list.remove(i);
                break;
            }
        }
    }

    /**
     * 移除当前Event的所有Listener
     */
    public static void removeListener(int eventType) {
        Log.d(TAG, "removeListener = " + eventType);
        mEventMap.remove(eventType);
    }

    public static void removeListenerAll() {
		mEventMap.clear();
    }

    /**
     * 激活监听器
     */
    public static void fireEvent(Message msg) {
        ArrayList<WeakReference<IEventListener>> list = mEventMap.get(msg.what);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                IEventListener listener = list.get(i).get();
                if (listener != null) {
                    listener.onEvent(msg);
                }
            }
        }

    }

    public static void fireEvent(int eventType,Bundle bundle){
        Message msg = Message.obtain();
        msg.what = eventType;
        if(bundle != null) {
            msg.setData(bundle);
        }
        fireEvent(msg);
    }

    public static void fireEvent(int eventType){
        fireEvent(eventType,null);
    }
}