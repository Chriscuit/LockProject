package com.lockproject.thomaz.lockproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by christophergill on 12/18/17.
 */

public class MyReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){

        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){

            Log.d("Log", "The screen is on.");
        }

        else{
            Log.d("Log", "The screen is off.");

            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
        }

    }
}


//public  int onStartCommand(Intent intent,int flag, int startIs){
//
//    // Detect screen off
//    IntentFilter filter=new IntentFilter(Intent.ACTION_SCREEN_ON);
//    filter.addAction(Intent.ACTION_SCREEN_OFF);
//    sReceiver=new MyReceiver();
//    registerReceiver(sReceiver,filter);
//    return  START_STICKY;
//
//}