package br.puzzleappz.accepttest;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by lucasselani on 22/06/2017.
 */

public class NotificationReceivedService extends NotificationListenerService implements ReadNotificationAsync.AsyncResponse{
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.v("BACKGROUNDSERVICE", "CREATED");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        ReadNotificationAsync readNotification = new ReadNotificationAsync(this);
        readNotification.execute(sbn);
        Log.v("BACKGROUNDSERVICE", "READING");
    }

    @Override
    public void processFinish(Boolean output) {
        Intent i = new Intent("gmail");
        Log.v("BACKGROUNDSERVICE", "SENDING BROADCAST " + output);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
    }
}
