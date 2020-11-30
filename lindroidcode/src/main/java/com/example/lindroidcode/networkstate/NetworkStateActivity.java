package com.example.lindroidcode.networkstate;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lindroidcode.R;

import static com.example.lindroidcode.networkstate.NetworkReceiver.MOBILE;
import static com.example.lindroidcode.networkstate.NetworkReceiver.WIFI;

public class NetworkStateActivity extends AppCompatActivity implements IOnNetworkStateChangedListener{
    private NetworkReceiver mNetworkReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_state);
        mNetworkReceiver = new NetworkReceiver (null);
        // 注册广播
        IntentFilter filter = new IntentFilter ();
        filter.addAction (ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver (mNetworkReceiver, filter);
        mNetworkReceiver.setRegistered(true);
        mNetworkReceiver.addListener (this);
    }

    @Override
    protected void onDestroy() {

        if (mNetworkReceiver != null && mNetworkReceiver.isRegistered()) {
            mNetworkReceiver.removeListener (this);
            unregisterReceiver(mNetworkReceiver);
            mNetworkReceiver.setRegistered(false);
        }
        super.onDestroy();
    }

    @Override
    public void onNetworkStateChanged(int state) {
        if (state == WIFI) {
            this.setTitle("Wifi connected.");
        }else if (state == MOBILE){
            this.setTitle("Mobile plan connected.");
        }else {
            this.setTitle("None network connection.");
        }
    }
}
