package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

/*
 * Broadcast Receiver class is used to notify about events of WiFi
 */
public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private GamePlayActivity mActivity;

    //constructor
    public WifiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, GamePlayActivity activity) {
        mManager = manager;
        mChannel = channel;
        mActivity = activity;

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction(); //use this to check the current action

        //notify about current status of WIFI
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) { //indicates whether Wi-Fi p2p is enabled
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1); //we will either have the value of the state or the default -1

            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) { //if wifi is enabled
                Toast.makeText(context, "Wifi is ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Wifi is OFF", Toast.LENGTH_SHORT).show();
            }

            //indicates that the available peer list has changed
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            //get a list of peers by calling WifiP2pManager.requestPeers()
            if (mManager!=null) {
                mManager.requestPeers(mChannel, mActivity.peerListListener);  //pass in peerListListener from mainActivity
            }
            //indicates the state of the wifi p2p connectivity has changed
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            //respond to a new connection or disconnections
            if (mManager == null) {
                return;
            }

            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isConnected()) { //we are connected with the other device, request connection info to find group owner IP
                mManager.requestConnectionInfo(mChannel,mActivity.connectionInfoListener);

            } else {
                mActivity.connectionStatus.setText("Device Disconnected");
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) { //indicates this device's configuration details have changed
            //respond to this device's wifi state changing
        }

    }
}
