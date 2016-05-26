package es.atrapandocucarachas.locationservice.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Alejandro Platas Mallo
 * @version X.XX
 * @since 13/5/16
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LocationService.class);
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
