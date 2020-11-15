package com.example.yego.Login;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApp extends Application {

    private static MyApp mInstance;

    public static final String CHANNEL_1_ID = "channel1";


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        createNotificationChannels();

    }

    public static synchronized MyApp getInstance(){
        return mInstance;
    }
    public void setConnectivityListener(MyReceiver.ConnectivityReciverListener listener){
        MyReceiver.connectivityReciverListener=listener;
    }


    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

        }
    }
}
