package com.restart.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 1. Make sure Internet permission is set in AndroidManifest.xml to communicate with the outside world
 * 2. The network calls should not be done on a main thread
 * 3. Update the UI content when the network call responded back
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = ".MainActivity";
    private TextView mMainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainContent = (TextView) findViewById(R.id.contentMain);

        try {
            Uri uri = Uri.parse("https://inventory.data.gov/api/action/datastore_search?resource_id=8ea44bc4-22ba-4386-b84c-1494ab28964b");
            URL url = new URL(uri.toString());
            new JSONAsyncTask().execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * AsyncTask that will make a single call to gsa to grab the JSON of travel costs.
     */
    private class JSONAsyncTask extends AsyncTask<URL, Void, String> {

        /**
         * Start the background process which includes the network call and the parsing of resulted
         * JSON.
         *
         * @param params Incoming URL to open an http connection with
         * @return N/A
         */
        @Override
        protected String doInBackground(URL... params) {
            try {
                return NetworkUtils.HttpResponse(params[0]);
            } catch (IOException e) {
                Log.e(TAG, "Unable in grabbing data");
                e.printStackTrace();
            } catch (SecurityException e) {
                Log.e(TAG, "Permission to access internet was denied");
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Manipulate the TextView using the data we just received from doInBackground.
         *
         * @param resultsJSON The resulting JSON parsed into a String object
         */
        @Override
        protected void onPostExecute(String resultsJSON) {
            if (resultsJSON != null && resultsJSON.equals("")) {
                mMainContent.setText("Not Found!");
            } else {
                mMainContent.setText(resultsJSON);
            }
        }
    }
}
