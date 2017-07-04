package br.puzzleappz.accepttest;

import android.os.AsyncTask;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by lucasselani on 22/06/2017.
 */

public class ReadNotificationAsync extends AsyncTask<StatusBarNotification, Void, Boolean> {
    // you may separate this or combined to caller class.
    public interface AsyncResponse { void processFinish(Boolean output); }
    public AsyncResponse delegate = null;
    public ReadNotificationAsync(AsyncResponse delegate){ this.delegate = delegate;  }

    @Override
    protected Boolean doInBackground(StatusBarNotification... params) {
        StatusBarNotification sbn = params[0];
        String pack = sbn.getPackageName();
        Log.v("NOTIFICATION", sbn.getNotification().extras.getCharSequence("android.text").toString());
        return pack.equals("com.google.android.gm");
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        delegate.processFinish(result);
    }
}