package com.pwr.mycookbook;

import android.app.Activity;
import android.app.Application;

import com.pwr.mycookbook.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by olaku on 24.12.2017.
 */

public class MyCookbookApp extends Application implements HasActivityInjector {


    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override public void onCreate() {
        super.onCreate();

        initializeComponent();
    }

    private void initializeComponent() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
