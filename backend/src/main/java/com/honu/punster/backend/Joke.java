package com.honu.punster.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class Joke {

    private String data;

    public Joke() {
    }

    public Joke(String joke) {
        data = joke;
    }

    public String getJoke() {
        return data;
    }

    public void setJoke(String data) {
        this.data = data;
    }
}