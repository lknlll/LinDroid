package com.example.lindroidcode.networkstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络状态广播接收器，接到广播时负责处理并通知消费者
 */
public class NetworkReceiver extends BroadcastReceiver {

    private boolean isRegistered = false;

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
    public static final int WIFI = 0;
    public static final int MOBILE = 1;
    public static final int NONE = 2;

    @IntDef(value = {
            WIFI,
            MOBILE,
            NONE
    })
    @Retention (RetentionPolicy.SOURCE)
    @interface NetState{}

    // 注册的监听者集合
    private List<IOnNetworkStateChangedListener> mListeners;

    public NetworkReceiver(List<IOnNetworkStateChangedListener> listeners) {
        if (listeners == null) {
            mListeners = new ArrayList<> ();
        }else{
            mListeners = listeners;
        }
    }

    /**
     * 添加监听器
     * @param listener 网络状态监听器
     */
    public synchronized void addListener(@NonNull IOnNetworkStateChangedListener listener){
        mListeners.add (listener);
    }

    /**
     * 移除监听器
     * @param listener 网络状态监听器
     */
    public synchronized void removeListener(@NonNull IOnNetworkStateChangedListener listener){
        mListeners.remove (listener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 遍历通知所有注册者
        if(mListeners != null && mListeners.size () > 0){
            NetworkInfo info = intent.getParcelableExtra (ConnectivityManager.EXTRA_NETWORK_INFO);
            int state = getNetState (info);

            for (IOnNetworkStateChangedListener listener : mListeners) {
                listener.onNetworkStateChanged (state);
            }
        }
    }

    // 判断连接状态
    private @NetState int getNetState(NetworkInfo info){
        if(info.getState () == NetworkInfo.State.CONNECTED){
            // wifi 还是 数据
            if(info.getType () == ConnectivityManager.TYPE_WIFI){
                return WIFI;
            } else if(info.getType () == ConnectivityManager.TYPE_MOBILE){
                return MOBILE;
            }
        }
        return NONE;
    }
}