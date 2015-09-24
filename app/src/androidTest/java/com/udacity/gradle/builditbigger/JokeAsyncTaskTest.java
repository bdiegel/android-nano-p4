package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.test.UiThreadTest;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Connected test verifies that the a non-empty String is returned by JokeFetcher using an AsyncTask
 */
public class JokeAsyncTaskTest extends AndroidTestCase {

    CountDownLatch signal;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        signal = new CountDownLatch(1);
    }

    @UiThreadTest
    public void testFetchJoke() throws InterruptedException {
        TestJokeListener listener = new TestJokeListener();
        JokeFetcher jokeFetcher = new JokeFetcher(JokeFetcher.GENYMOTION_LOCALHOST, listener);
        jokeFetcher.fetchJoke();
        signal.await(15, TimeUnit.SECONDS);
        //Log.d("JOKE", listener.joke);
        //assertTrue("", !TextUtils.isEmpty(listener.joke));
        assertNull("API call succeeded without error", listener.error);
        assertTrue("Joke is non-empty", !TextUtils.isEmpty(listener.joke));
    }

    class TestJokeListener implements JokeFetcher.JokeListener {

        public String joke = null;
        public Throwable error = null;

        @Override
        public void onSuccess(String joke) {
            this.joke = joke;
            signal.countDown();
        }

        @Override
        public void onError(Throwable throwable) {
            error = throwable;
            signal.countDown();
        }
    }
}
