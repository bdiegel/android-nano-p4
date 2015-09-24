package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.honu.standup.JokeActivity;


/**
 * Main fragment for 'free' app shows a banner ad at footer of activtiy and interstitial ad before each joke
 */
public class MainActivityFragment extends BaseMainActivityFragement {

    InterstitialAd mInterstitialAd;

    private static final String TEST_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";

    public MainActivityFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // super will setup button listener and progress indicator
        View root = super.onCreateView(inflater, container, savedInstanceState);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(TEST_AD_UNIT_ID);
        requestInterstitialAd();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                showJokeActivity();
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        return root;
    }

    @Override
    protected void showJokeActivity() {
        // show an interstial ad before each joke
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Intent jokeIntent = new Intent(getActivity(), com.honu.standup.JokeActivity.class);
            jokeIntent.putExtra(JokeActivity.EXTRA_JOKE_TEXT, mJoke);
            startActivity(jokeIntent);
            requestInterstitialAd();
        }
        mJokeButton.setEnabled(true);
    }

    private void requestInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder()
              .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
              .build();
        mInterstitialAd.loadAd(adRequest);
    }
}
