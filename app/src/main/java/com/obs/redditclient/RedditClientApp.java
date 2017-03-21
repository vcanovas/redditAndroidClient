package com.obs.redditclient;

public class RedditClientApp extends com.activeandroid.app.Application {
    private static RedditClientApp singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    public static RedditClientApp getInstance() {
        return singleton;
    }

}
