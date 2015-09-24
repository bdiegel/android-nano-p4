package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.honu.punster.backend.jokeApi.JokeApi;

import java.io.IOException;

/**
 * Fetches joke from GCE (Google Cloud Endpoint) and notifies listener with result.
 */
public class JokeFetcher {

    private static JokeApi mService = null;

    static final String LOG_TAG = JokeFetcher.class.getSimpleName();

    String mRootUrl;

    JokeListener mJokeListener;

    interface JokeListener {
        void onSuccess(String joke);
        void onError(Throwable throwable);
    }

    public JokeFetcher(@NonNull String rootUrl, JokeListener jokeListener) {
        this.mRootUrl = rootUrl;
        this.mJokeListener = jokeListener;
    }

    public void fetchJoke() {
        JokeAsyncTask asyncTask = new JokeAsyncTask();
        asyncTask.execute();
    }

    class JokeAsyncTask extends AsyncTask<Void, Void, String> {

        private Throwable throwable;

        @Override
        protected String doInBackground(Void... params) {

            // introduce a short delay to simulate a slow fetch and force the progress bar to show
            shortDelay();

            // create the API service (do once)
            if (mService == null) {
                JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                      new AndroidJsonFactory(), null)
                      // local devappserver options:
                      .setRootUrl(mRootUrl)
                      .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                          @Override
                          public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                              // turn off compression
                              abstractGoogleClientRequest.setDisableGZipContent(true);
                          }
                      });
                // end: local devappserver options
                mService = builder.build();
            }

            try {
                return mService.getPun().execute().getJoke();
            } catch (IOException e) {
                this.throwable = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (throwable != null) {
                Log.d(LOG_TAG, "API error: " + throwable.getMessage());
                mJokeListener.onError(throwable);
            } else {
                Log.d(LOG_TAG, "Got pun: " + result);
                mJokeListener.onSuccess(result);
            }
        }

        private void shortDelay() {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
