package com.lockproject.thomaz.lockproject;

/**
 * Created by christophergill on 12/18/17.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class yourActivityRunOnStartup extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

}
