package com.ashwin.vote_app;

import com.google.android.material.color.DynamicColors;

public class GayApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
