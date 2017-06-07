package com.kidoz.sdk.sample.app;

import android.app.Application;

import com.kidoz.sdk.api.KidozSDK;


/**
 * Created by KIDOZ.
 */

public class SampleAdsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        KidozSDK.setLoggingEnabled(true);
    }
}
