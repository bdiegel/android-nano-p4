package com.honu.standup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.Random;

public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE_TEXT = "com.honu.standup.JOKE_TEXT";

    static final String APOLOGY = "Sorry, I'm not feeling funny today (something must be wrong)";

    int[] mBackgroundColors;
    Random mRandom = new Random();

    TextSwitcher mJokeTextSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        // select a random background color
        mBackgroundColors = getResources().getIntArray(R.array.bgcolors);
        int bgColor = mBackgroundColors[mRandom.nextInt(mBackgroundColors.length)];

        // get joke text
        String joke = null;
        Intent intent = getIntent();
        if (intent != null) {
             joke = intent.getStringExtra(EXTRA_JOKE_TEXT);
        }

        // default text if called without a joke
        if (TextUtils.isEmpty(joke)) {
            joke = APOLOGY;
        }

        TextView jokeTextView = (TextView) findViewById(R.id.joke);
        jokeTextView.setBackgroundColor(bgColor);
        jokeTextView.setText(joke);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_joke, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // handle back arrow in toolbar:
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
