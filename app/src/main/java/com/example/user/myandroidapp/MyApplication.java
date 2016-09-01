package com.example.user.myandroidapp;

import android.app.Application;
import android.view.Display;

import com.example.user.myandroidapp.model.OwnedPokemonInfo;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by user on 2016/8/25.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(OwnedPokemonInfo.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .enableLocalDataStore()
                .applicationId("aBriKu0h4EZgnb8Sft9Uv4HyDZHOj01WZQp3jPs1")
                .clientKey("YJy27NUjuLfJaicKAFReic3gpCFxdemFsPrsQj05")
                .server("https://parseapi.back4app.com/")
                .build()
        );

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .diskCacheSize(50 * 1024 * 1024) //50MB
                .diskCacheFileCount(100)
                .build();

        ImageLoader.getInstance().init(config);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
