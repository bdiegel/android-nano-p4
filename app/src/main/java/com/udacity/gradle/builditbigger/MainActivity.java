package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.honu.standup.JokeActivity;


public class MainActivity extends ActionBarActivity { //implements JokeFetcher.JokeListener {

    static final String LOG_TAG = MainActivity.class.getSimpleName();

    //ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mProgress = (ProgressBar) findViewById(R.id.fetchJokeProgress);
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
        // automatically handle clicks on the Home/Up mJokeButton, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    // Button onClick
//    public void tellJoke(View view) {
//        mProgress.setVisibility(ProgressBar.VISIBLE);
//        JokeFetcher jokeFetcher = new JokeFetcher(JokeFetcher.GENYMOTION_LOCALHOST, this);
//        jokeFetcher.fetchJoke();
//    }

//    private void showJokeActivity(String joke) {
//        Intent jokeIntent = new Intent(this, com.honu.standup.JokeActivity.class);
//        jokeIntent.putExtra(JokeActivity.EXTRA_JOKE_TEXT, joke);
//        startActivity(jokeIntent);
//    }


//    @Override
//    public void onSuccess(String joke) {
//        mProgress.setVisibility(ProgressBar.GONE);
//        showJokeActivity(joke);
//    }
//
//    @Override
//    public void onError(Throwable throwable) {
//        mProgress.setVisibility(ProgressBar.GONE);
//        Toast.makeText(this, R.string.fetch_joke_failed, Toast.LENGTH_LONG).show();
//        Log.e(LOG_TAG, getString(R.string.fetch_joke_failed), throwable);
//    }
}
