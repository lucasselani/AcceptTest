package br.puzzleappz.accepttest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import java.io.IOException;

/**
 * Created by lucasselani on 23/06/2017.
 */

public class MakeRequestTask extends AsyncTask<Void, Void, Message> {
    private com.google.api.services.gmail.Gmail mService = null;
    private Exception mLastError = null;
    private static final String USER = "me";
    private static final String QUERY = "from:noreply@testerwork.com";
    private ListMessagesResponse messageResponse;

    MakeRequestTask(GoogleAccountCredential credential) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.gmail.Gmail.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Gmail AcceptTest")
                .build();
    }

    /**
     * Background task to call Gmail API.
     *
     * @param params no parameters needed for this task.
     */
    @Override
    protected Message doInBackground(Void... params) {
        try {
            new Thread(getDataFromApi).start();
            Log.v("DOINBACKGROUND", messageResponse.getMessages().toString());
            return null;
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Message message) {
        Log.v("ONPOSTEXECUTE", "MESSAGES GOT");
    }

    @Override
    protected void onCancelled() {
    }

    /**
     * Fetch a list of Gmail labels attached to the specified account.
     *
     * @return List of Strings labels.
     * @throws IOException
     */

    private Runnable getDataFromApi = new Runnable() {
        @Override
        public void run() {
            Log.v("GETDATAFROMAPI", "GETTING MESSAGENS");

            try {
                messageResponse =
                        mService.users().messages()
                                .list(USER)
                                .setQ(QUERY)
                                .setMaxResults(1L)
                                .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}