package com.lockproject.thomaz.lockproject;

/**
 * Created by christophergill on 12/18/17.
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.view.WindowManager;


public class OverlayShowingService extends Service {

//    private View topLeftView;

    private WindowManager wm;

    private BroadcastReceiver sReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public  int onStartCommand(Intent intent,int flag, int startIs){
        // Detect screen off
        IntentFilter filter=new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        sReceiver = new MyReceiver();
        registerReceiver(sReceiver,filter);
        return  START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    public void onDestroy() {

        if(sReceiver!=null) {
            unregisterReceiver(sReceiver);
        }
        super.onDestroy();
    }

}
