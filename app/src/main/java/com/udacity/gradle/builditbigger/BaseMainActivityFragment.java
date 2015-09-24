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
public abstract class BaseMainActivityFragment extends Fragment implements JokeFetcher.JokeListener {

    static final String LOG_TAG = BaseMainActivityFragment.class.getSimpleName();

    Button mJokeButton;

    ProgressBar mProgress;

    String mJoke;

    public BaseMainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mProgress = (ProgressBar) root.findViewById(R.id.fetchJokeProgress);

        mJokeButton = (Button) root.findViewById(R.id.tellJokeButton);
        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mJokeButton.setEnabled(false);
                tellJoke(view);
            }
        });

        return root;
    }

    public void tellJoke(View view) {
        mProgress.setVisibility(ProgressBar.VISIBLE);
        JokeFetcher jokeFetcher = new JokeFetcher(getString(R.string.emulator_localhost), this);
        jokeFetcher.fetchJoke();
    }

    protected void showJokeActivity() {
        Intent jokeIntent = new Intent(getActivity(), com.honu.standup.JokeActivity.class);
        jokeIntent.putExtra(JokeActivity.EXTRA_JOKE_TEXT, mJoke);
        startActivity(jokeIntent);
        mJokeButton.setEnabled(true);
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
