package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.honu.punster.backend.jokeApi.JokeApi;
import com.honu.standup.JokeActivity;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        JokeAsyncTask asyncTask = new JokeAsyncTask();
        asyncTask.execute();
    }

    private void showJokeActivity(String joke) {
        Intent jokeIntent = new Intent(this, com.honu.standup.JokeActivity.class);
        jokeIntent.putExtra(JokeActivity.EXTRA_JOKE_TEXT, joke);
        startActivity(jokeIntent);
    }


    class JokeAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

        private JokeApi myApiService = null;

        // localhost's IP address in Android emulator
        //String rootUrl = "http://10.0.2.2:8080/_ah/api/";

        // localhost's IP address in Genymotion emulator
        String rootUrl = "http://10.0.3.2:8080/_ah/api/";

        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {

            // create the API service (do once)
            if (myApiService == null) {
                JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                      new AndroidJsonFactory(), null)
                      // local devappserver options:
                      .setRootUrl(rootUrl)
                      .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                          @Override
                          public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                              // turn off compression
                              abstractGoogleClientRequest.setDisableGZipContent(true);
                          }
                      });
                      // end: local devappserver options
                myApiService = builder.build();
            }

            try {
                return myApiService.getPun().execute().getJoke();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(LOG_TAG, "Pun: " + result);
            showJokeActivity(result);
        }
    }

}
