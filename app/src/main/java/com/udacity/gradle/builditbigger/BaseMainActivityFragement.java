package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.honu.standup.JokeActivity;

/**
 * Base fragment functionality shared by all product flavors
 */
public abstract class BaseMainActivityFragement extends Fragment implements JokeFetcher.JokeListener {

    static final String LOG_TAG = BaseMainActivityFragement.class.getSimpleName();

    ProgressBar mProgress;

    String mJoke;

    public BaseMainActivityFragement() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mProgress = (ProgressBar) root.findViewById(R.id.fetchJokeProgress);

        Button button = (Button) root.findViewById(R.id.tellJokeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke(view);
            }
        });

        return root;
    }

    public void tellJoke(View view) {
        mProgress.setVisibility(ProgressBar.VISIBLE);
        JokeFetcher jokeFetcher = new JokeFetcher(JokeFetcher.GENYMOTION_LOCALHOST, this);
        jokeFetcher.fetchJoke();
    }

    protected void showJokeActivity() {
        Intent jokeIntent = new Intent(getActivity(), com.honu.standup.JokeActivity.class);
        jokeIntent.putExtra(JokeActivity.EXTRA_JOKE_TEXT, mJoke);
        startActivity(jokeIntent);
    }

    @Override
    public void onSuccess(String joke) {
        mProgress.setVisibility(ProgressBar.GONE);
        mJoke = joke;
        showJokeActivity();
    }

    @Override
    public void onError(Throwable throwable) {
        mProgress.setVisibility(ProgressBar.GONE);
        Toast.makeText(getActivity(), R.string.fetch_joke_failed, Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, getString(R.string.fetch_joke_failed), throwable);
    }
}
